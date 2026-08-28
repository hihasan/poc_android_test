package xyz.hihasan.ledgerlite.core.data.mapper

import xyz.hihasan.ledgerlite.core.database.entity.AccountEntity
import xyz.hihasan.ledgerlite.core.database.entity.TransactionEntity
import xyz.hihasan.ledgerlite.core.domain.model.NewTransaction
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.AccountType
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.Transaction
import xyz.hihasan.ledgerlite.core.network.dto.AccountDto
import xyz.hihasan.ledgerlite.core.network.dto.CreateTransactionRequest
import xyz.hihasan.ledgerlite.core.network.dto.TransactionDto
import java.time.Instant
import java.util.UUID

fun TransactionEntity.toDomain(): Transaction = Transaction(
    id = id,
    type = enumValueOf(type),
    category = enumValueOf(category),
    amount = Money(amountMinorUnits),
    currency = currency,
    description = description,
    note = note,
    timestamp = Instant.ofEpochMilli(timestamp),
    accountId = accountId,
    counterpartyAccountId = counterpartyAccountId,
    pending = pending,
)

fun Transaction.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type.name,
    category = category.name,
    amountMinorUnits = amount.minorUnits,
    currency = currency,
    description = description,
    note = note,
    timestamp = timestamp.toEpochMilli(),
    accountId = accountId,
    counterpartyAccountId = counterpartyAccountId,
    pending = pending,
)

fun NewTransaction.toDomain(id: String = UUID.randomUUID().toString()): Transaction = Transaction(
    id = id,
    type = type,
    category = category,
    amount = amount,
    currency = currency,
    description = description,
    note = note,
    timestamp = timestamp,
    accountId = accountId,
    counterpartyAccountId = counterpartyAccountId,
    pending = false,
)

fun NewTransaction.toRequest(): CreateTransactionRequest = CreateTransactionRequest(
    type = type.name,
    category = category.name,
    amountMinorUnits = amount.minorUnits,
    currency = currency,
    description = description,
    note = note,
    timestampEpochMillis = timestamp.toEpochMilli(),
    accountId = accountId,
    counterpartyAccountId = counterpartyAccountId,
)

fun TransactionDto.toEntity(): TransactionEntity = TransactionEntity(
    id = id,
    type = type,
    category = category,
    amountMinorUnits = amountMinorUnits,
    currency = currency,
    description = description,
    note = note,
    timestamp = timestampEpochMillis,
    accountId = accountId,
    counterpartyAccountId = counterpartyAccountId,
    pending = pending,
)

fun AccountEntity.toDomain(): Account = Account(
    id = id,
    name = name,
    type = enumValueOf<AccountType>(type),
    currency = currency,
    balance = Money(balanceMinorUnits),
)

fun AccountDto.toEntity(): AccountEntity = AccountEntity(
    id = id,
    name = name,
    type = type,
    currency = currency,
    balanceMinorUnits = balanceMinorUnits,
)

private inline fun <reified T : Enum<T>> enumValueOf(name: String): T =
    enumValues<T>().firstOrNull { it.name == name } ?: enumValues<T>().first()
