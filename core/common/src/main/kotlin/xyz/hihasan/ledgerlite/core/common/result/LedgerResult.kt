package xyz.hihasan.ledgerlite.core.common.result

/** Minimal domain-level result type used by repositories and use cases. */
sealed interface LedgerResult<out T> {
    data class Success<T>(val data: T) : LedgerResult<T>
    data class Failure(val error: LedgerError) : LedgerResult<Nothing>
}

sealed class LedgerError(open val message: String?) {
    data class Network(override val message: String? = null) : LedgerError(message)
    data class NotFound(override val message: String? = null) : LedgerError(message)
    data class Unauthorized(override val message: String? = null) : LedgerError(message)
    data class Validation(val fieldErrors: Map<String, String>) : LedgerError("Validation failed")
    data class Unknown(val cause: Throwable? = null) : LedgerError(cause?.message)
}

inline fun <T, R> LedgerResult<T>.map(transform: (T) -> R): LedgerResult<R> = when (this) {
    is LedgerResult.Success -> LedgerResult.Success(transform(data))
    is LedgerResult.Failure -> this
}

inline fun <T> LedgerResult<T>.onSuccess(action: (T) -> Unit): LedgerResult<T> {
    if (this is LedgerResult.Success) action(data)
    return this
}

inline fun <T> LedgerResult<T>.onFailure(action: (LedgerError) -> Unit): LedgerResult<T> {
    if (this is LedgerResult.Failure) action(error)
    return this
}
