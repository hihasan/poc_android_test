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
import retrofit2.Retrofit
import xyz.hihasan.ledgerlite.core.network.BuildConfig
import xyz.hihasan.ledgerlite.core.network.api.LedgerApi
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

/**
 * The API endpoint seam, split into its own module so instrumented tests can replace *only* the
 * base URL — via `@TestInstallIn` (see `:core:testing` `FakeNetworkModule`, which stands up an
 * in-process `MockWebServer`) — without also re-providing the whole Retrofit stack.
 *
 * The value comes from `BuildConfig.API_BASE_URL` (set per build type in `build.gradle.kts`).
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkUrlModule {

    @Provides
    @ApiBaseUrl
    fun provideBaseUrl(): String = BuildConfig.API_BASE_URL
}
