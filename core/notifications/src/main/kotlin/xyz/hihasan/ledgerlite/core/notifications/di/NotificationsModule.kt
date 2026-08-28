package xyz.hihasan.ledgerlite.core.notifications.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionEventNotifier
import xyz.hihasan.ledgerlite.core.notifications.TransactionNotifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationsModule {

    @Binds
    @Singleton
    abstract fun bindTransactionEventNotifier(impl: TransactionNotifier): TransactionEventNotifier
}
