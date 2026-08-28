package xyz.hihasan.ledgerlite.core.domain.usecase

import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.model.NewTransaction
import xyz.hihasan.ledgerlite.core.domain.model.TransactionFormInput
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionEventNotifier
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionRepository
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.TransactionType
import java.math.BigDecimal
import java.time.Instant
import javax.inject.Inject

/**
 * Pure validation of the Add Expense / Transfer form. No I/O — the natural target for
 * focused unit tests of the validation rules.
 */
class ValidateTransactionFormUseCase @Inject constructor() {

    operator fun invoke(input: TransactionFormInput): LedgerResult<NewTransaction> {
        val errors = mutableMapOf<String, String>()

        val amount = input.amountText.trim().toBigDecimalOrNull()
        if (input.amountText.isBlank()) {
            errors["amount"] = "Amount is required"
        } else if (amount == null) {
            errors["amount"] = "Amount is not a valid number"
        } else if (amount <= BigDecimal.ZERO) {
            errors["amount"] = "Amount must be greater than zero"
        }

        if (input.description.isBlank()) errors["description"] = "Description is required"
        if (input.description.length > MAX_DESCRIPTION) errors["description"] = "Description is too long"
        if (input.category == null) errors["category"] = "Pick a category"
        if (input.accountId.isNullOrBlank()) errors["account"] = "Pick an account"

        if (input.type == TransactionType.TRANSFER) {
            if (input.counterpartyAccountId.isNullOrBlank()) {
                errors["counterparty"] = "Transfers need a destination account"
            } else if (input.counterpartyAccountId == input.accountId) {
                errors["counterparty"] = "Source and destination must differ"
            }
        }

        if (input.currency.length != 3) errors["currency"] = "Use a 3-letter currency code"

        if (errors.isNotEmpty()) return LedgerResult.Failure(LedgerError.Validation(errors))

        return LedgerResult.Success(
            NewTransaction(
                type = input.type,
                category = input.category!!,
                amount = Money.ofMajor(amount!!),
                currency = input.currency.uppercase(),
                description = input.description.trim(),
                note = input.note.trim().ifBlank { null },
                timestamp = input.dateEpochMillis?.let(Instant::ofEpochMilli) ?: Instant.now(),
                accountId = input.accountId!!,
                counterpartyAccountId = input.counterpartyAccountId?.takeIf {
                    input.type == TransactionType.TRANSFER
                },
            ),
        )
    }

    private companion object {
        const val MAX_DESCRIPTION = 140
    }
}

/** Validates the form, persists the transaction, and fires the "transaction added" notification. */
class AddTransactionUseCase @Inject constructor(
    private val validate: ValidateTransactionFormUseCase,
    private val repository: TransactionRepository,
    private val notifier: TransactionEventNotifier,
) {
    suspend operator fun invoke(input: TransactionFormInput): LedgerResult<Unit> {
        return when (val validated = validate(input)) {
            is LedgerResult.Failure -> validated
            is LedgerResult.Success -> when (val saved = repository.addTransaction(validated.data)) {
                is LedgerResult.Failure -> saved
                is LedgerResult.Success -> {
                    notifier.onTransactionAdded(saved.data)
                    LedgerResult.Success(Unit)
                }
            }
        }
    }
}

class DeleteTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository,
) {
    suspend operator fun invoke(id: String): LedgerResult<Unit> = repository.deleteTransaction(id)
}
