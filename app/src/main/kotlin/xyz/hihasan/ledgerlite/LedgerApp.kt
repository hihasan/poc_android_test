package xyz.hihasan.ledgerlite

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import xyz.hihasan.ledgerlite.core.notifications.TransactionNotifier
import javax.inject.Inject

@HiltAndroidApp
class LedgerApp : Application() {

    @Inject
    lateinit var transactionNotifier: TransactionNotifier

    override fun onCreate() {
        super.onCreate()
        transactionNotifier.ensureChannel()
    }
}
