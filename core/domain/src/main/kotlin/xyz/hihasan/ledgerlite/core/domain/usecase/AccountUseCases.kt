package xyz.hihasan.ledgerlite.core.domain.usecase

import kotlinx.coroutines.flow.Flow
import xyz.hihasan.ledgerlite.core.domain.repository.AccountRepository
import xyz.hihasan.ledgerlite.core.model.Account
import xyz.hihasan.ledgerlite.core.model.Money
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    operator fun invoke(): Flow<List<Account>> = repository.observeAccounts()
}

class GetTotalBalanceUseCase @Inject constructor(
    private val repository: AccountRepository,
) {
    operator fun invoke(): Flow<Money> = repository.observeTotalBalance()
}
