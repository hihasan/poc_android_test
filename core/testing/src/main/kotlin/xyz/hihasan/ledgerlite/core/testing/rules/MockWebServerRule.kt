package xyz.hihasan.ledgerlite.core.testing.rules

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

/**
 * JUnit 4 rule that starts a [MockWebServer] before each test and shuts it down after. Expose
 * [server] / [baseUrl] to point Retrofit at it, or call [enqueueDispatcher] to script responses.
 */
class MockWebServerRule : ExternalResource() {

    lateinit var server: MockWebServer
        private set

    val baseUrl: String get() = server.url("/").toString()

    fun enqueueDispatcher(dispatcher: Dispatcher) {
        server.dispatcher = dispatcher
    }

    override fun before() {
        server = MockWebServer().also { it.start() }
    }

    override fun after() {
        server.shutdown()
    }
}
