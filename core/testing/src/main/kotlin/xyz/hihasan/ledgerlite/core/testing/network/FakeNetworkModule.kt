package xyz.hihasan.ledgerlite.core.testing.network

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.mockwebserver.MockWebServer
import xyz.hihasan.ledgerlite.core.network.di.ApiBaseUrl
import xyz.hihasan.ledgerlite.core.network.di.NetworkUrlModule
import java.net.InetAddress
import javax.inject.Singleton

/**
 * Replaces [NetworkUrlModule] in every `@HiltAndroidTest` run: stands up an in-process
 * [MockWebServer] backed by [MockApiDispatcher]'s canned responses and points Retrofit at it.
 *
 * This module lives in `:core:testing` `main` (a source set only ever consumed as
 * `androidTestImplementation` / `testImplementation`), so `MockWebServer` never ships in the app.
 *
 * A test that needs to script a specific scenario can `@Inject` the [MockWebServer] and swap its
 * `dispatcher`.
 */
@Module
@TestInstallIn(components = [SingletonComponent::class], replaces = [NetworkUrlModule::class])
object FakeNetworkModule {

    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer().apply {
        dispatcher = MockApiDispatcher()
        // getLoopbackAddress() resolves no hostname, so start() does no DNS lookup and is safe on
        // any thread — including the main thread, where Hilt may first instantiate this.
        start(InetAddress.getLoopbackAddress(), 0)
    }

    @Provides
    @ApiBaseUrl
    fun provideBaseUrl(server: MockWebServer): String = server.url("/").toString()
}
