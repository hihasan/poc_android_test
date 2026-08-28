package xyz.hihasan.ledgerlite.core.network.di

import retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import xyz.hihasan.ledgerlite.core.network.api.LedgerApi
import xyz.hihasan.ledgerlite.core.network.mock.MockApiDispatcher
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiBaseUrl

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    /**
     * In-process fake backend. "For now" the app points Retrofit at this; swap the dispatcher
     * (or replace this module with @TestInstallIn) in tests.
     */
    @Provides
    @Singleton
    fun provideMockWebServer(): MockWebServer = MockWebServer().apply {
        dispatcher = MockApiDispatcher()
        start()
    }

    @Provides
    @ApiBaseUrl
    fun provideBaseUrl(server: MockWebServer): String = server.url("/").toString()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        json: Json,
        @ApiBaseUrl baseUrl: String,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    @Provides
    @Singleton
    fun provideLedgerApi(retrofit: Retrofit): LedgerApi = retrofit.create(LedgerApi::class.java)
}
