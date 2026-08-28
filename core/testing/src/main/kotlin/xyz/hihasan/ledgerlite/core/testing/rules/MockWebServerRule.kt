package xyz.hihasan.ledgerlite.core.testing.rules

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource
import java.net.InetAddress

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
        // Bind to the fixed loopback address: start() then does no DNS lookup and won't trip
        // NetworkOnMainThreadException regardless of which thread the rule runs on.
        server = MockWebServer().also { it.start(InetAddress.getLoopbackAddress(), 0) }
    }

    override fun after() {
        server.shutdown()
    }
}
