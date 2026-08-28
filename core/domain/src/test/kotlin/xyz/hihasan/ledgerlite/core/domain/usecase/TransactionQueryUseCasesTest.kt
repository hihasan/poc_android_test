package xyz.hihasan.ledgerlite.core.domain.usecase

import org.junit.jupiter.api.Test

class GetTransactionsUseCaseTest {

    @Test
    fun `returns the unfiltered paged stream by default`() { TODO() }
}

class SearchTransactionsUseCaseTest {

    @Test
    fun `forwards the filter to the repository`() { TODO() }
}

class GetTransactionDetailUseCaseTest {

    @Test
    fun `emits the transaction for the given id`() { TODO() }

    @Test
    fun `emits null when the transaction is missing`() { TODO() }
}

class SeedTransactionsUseCaseTest {

    @Test
    fun `asks the repository to seed the requested count`() { TODO() }
}
