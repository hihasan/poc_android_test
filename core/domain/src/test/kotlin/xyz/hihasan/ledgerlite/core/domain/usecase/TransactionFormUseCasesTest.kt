package xyz.hihasan.ledgerlite.core.domain.usecase

import org.junit.jupiter.api.Test

/**
 * JUnit 5 unit tests for the Add Expense / Transfer form use cases. Run with `:core:domain:test`.
 * Bodies are TODO on purpose — write the assertions yourself.
 */
class ValidateTransactionFormUseCaseTest {

    @Test
    fun `accepts a fully populated expense form`() { TODO() }

    @Test
    fun `rejects a blank amount`() { TODO() }

    @Test
    fun `rejects a non-numeric amount`() { TODO() }

    @Test
    fun `rejects a zero or negative amount`() { TODO() }

    @Test
    fun `rejects a blank description`() { TODO() }

    @Test
    fun `requires a category and an account`() { TODO() }

    @Test
    fun `transfer requires a distinct counterparty account`() { TODO() }

    @Test
    fun `normalises currency to upper case`() { TODO() }
}

class AddTransactionUseCaseTest {

    @Test
    fun `persists a valid transaction and fires the notification`() { TODO() }

    @Test
    fun `does not persist when validation fails`() { TODO() }

    @Test
    fun `propagates a repository failure`() { TODO() }
}

class DeleteTransactionUseCaseTest {

    @Test
    fun `delegates to the repository`() { TODO() }
}
