package xyz.hihasan.ledgerlite.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import xyz.hihasan.ledgerlite.core.domain.repository.AccountRepository
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionRepository
import xyz.hihasan.ledgerlite.core.model.Money
import xyz.hihasan.ledgerlite.core.model.SpendingSummary
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

data class DashboardData(
    val totalBalance: Money,
    val summary: SpendingSummary,
)

/** Emits the spending summary for a period (defaults to the current calendar month). */
class GetSpendingSummaryUseCase @Inject constructor(
    private val transactions: TransactionRepository,
) {
    operator fun invoke(
        from: LocalDate = LocalDate.now().withDayOfMonth(1),
        to: LocalDate = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth()),
    ): Flow<SpendingSummary> = transactions.observeSpendingSummary(from, to)
}

/** Combines total balance and spending summary for the Dashboard screen. */
class GetDashboardUseCase @Inject constructor(
    private val accounts: AccountRepository,
    private val getSpendingSummary: GetSpendingSummaryUseCase,
) {
    operator fun invoke(): Flow<DashboardData> =
        combine(accounts.observeTotalBalance(), getSpendingSummary()) { balance, summary ->
            DashboardData(balance, summary)
        }
}
