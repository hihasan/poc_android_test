package xyz.hihasan.ledgerlite.core.testing.network

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * Canned responses so instrumented tests can talk to a real Retrofit stack without a backend.
 * Installed on the [okhttp3.mockwebserver.MockWebServer] that [FakeNetworkModule] provides; a test
 * that needs a specific scenario can inject that server and swap its `dispatcher`.
 */
class MockApiDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        val path = request.path.orEmpty().substringBefore('?')
        return when {
            path.endsWith("/auth/login") || path.endsWith("/auth/register") ->
                json(AUTH_RESPONSE)

            path.endsWith("/accounts") -> json(ACCOUNTS_RESPONSE)

            path.endsWith("/transactions") && request.method == "POST" ->
                json(CREATED_TRANSACTION, code = 201)

            path.endsWith("/transactions") -> json(EMPTY_PAGE)

            path.contains("/transactions/") -> json(CREATED_TRANSACTION)

            else -> MockResponse().setResponseCode(404)
        }
    }

    private fun json(body: String, code: Int = 200): MockResponse =
        MockResponse()
            .setResponseCode(code)
            .setHeader("Content-Type", "application/json")
            .setBody(body)

    private companion object {
        const val AUTH_RESPONSE = """
            {"user_id":"user-1","email":"demo@ledgerlite.app","display_name":"Demo User",
             "access_token":"mock-access-token","refresh_token":"mock-refresh-token"}
        """

        const val ACCOUNTS_RESPONSE = """
            [{"id":"acc-checking","name":"Checking","type":"CHECKING","currency":"USD","balance_minor_units":532144},
             {"id":"acc-savings","name":"Savings","type":"SAVINGS","currency":"USD","balance_minor_units":1200000}]
        """

        const val EMPTY_PAGE = """
            {"items":[],"page":1,"page_size":20,"total_items":0,"total_pages":0}
        """

        const val CREATED_TRANSACTION = """
            {"id":"srv-generated","type":"EXPENSE","category":"OTHER","amount_minor_units":0,
             "currency":"USD","description":"","timestamp_epoch_millis":0,"account_id":"acc-checking"}
        """
    }
}
