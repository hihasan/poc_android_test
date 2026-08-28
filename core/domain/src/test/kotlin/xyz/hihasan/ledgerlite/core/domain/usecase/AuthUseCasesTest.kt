package xyz.hihasan.ledgerlite.core.domain.usecase

import org.junit.jupiter.api.Test

class LoginUseCaseTest {

    @Test
    fun `rejects an invalid email before calling the repository`() { TODO() }

    @Test
    fun `rejects a password shorter than the minimum`() { TODO() }

    @Test
    fun `delegates to the repository when input is valid`() { TODO() }
}

class RegisterUseCaseTest {

    @Test
    fun `requires matching passwords`() { TODO() }

    @Test
    fun `requires a display name`() { TODO() }

    @Test
    fun `delegates to the repository when input is valid`() { TODO() }
}

class LogoutUseCaseTest {

    @Test
    fun `delegates to the repository`() { TODO() }
}

class ObserveAuthSessionUseCaseTest {

    @Test
    fun `emits the current session and subsequent updates`() { TODO() }
}
