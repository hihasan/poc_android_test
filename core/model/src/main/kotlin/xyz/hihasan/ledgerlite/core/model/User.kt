package xyz.hihasan.ledgerlite.core.model

data class User(
    val id: String,
    val email: String,
    val displayName: String,
)

/** Result of a successful authentication. */
data class AuthSession(
    val user: User,
    val accessToken: String,
    val refreshToken: String,
)
