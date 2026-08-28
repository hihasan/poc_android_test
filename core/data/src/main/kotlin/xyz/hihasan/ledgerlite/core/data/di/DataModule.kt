package xyz.hihasan.ledgerlite.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import xyz.hihasan.ledgerlite.core.data.repository.AccountRepositoryImpl
import xyz.hihasan.ledgerlite.core.data.repository.AuthRepositoryImpl
import xyz.hihasan.ledgerlite.core.data.repository.SettingsRepositoryImpl
import xyz.hihasan.ledgerlite.core.data.repository.TransactionRepositoryImpl
import xyz.hihasan.ledgerlite.core.domain.repository.AccountRepository
import xyz.hihasan.ledgerlite.core.domain.repository.AuthRepository
import xyz.hihasan.ledgerlite.core.domain.repository.SettingsRepository
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(impl: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindAccountRepository(impl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(impl: SettingsRepositoryImpl): SettingsRepository

    companion object {
        @Provides
        @Singleton
        fun providePreferencesDataStore(
            @ApplicationContext context: Context,
        ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
            scope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
        ) { context.preferencesDataStoreFile("ledgerlite_prefs") }
    }
}
