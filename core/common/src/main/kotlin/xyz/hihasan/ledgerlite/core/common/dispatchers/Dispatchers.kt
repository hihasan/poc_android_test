package xyz.hihasan.ledgerlite.core.common.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val ledgerDispatcher: LedgerDispatcher)

enum class LedgerDispatcher { Default, IO }

/**
 * Injected wrapper around the coroutine dispatchers so tests can substitute a
 * `TestDispatcher` without touching `Dispatchers.setMain`.
 */
interface DispatcherProvider {
    val default: kotlinx.coroutines.CoroutineDispatcher
    val io: kotlinx.coroutines.CoroutineDispatcher
    val main: kotlinx.coroutines.CoroutineDispatcher
}
