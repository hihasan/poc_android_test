package xyz.hihasan.ledgerlite.core.notifications

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import xyz.hihasan.ledgerlite.core.domain.repository.TransactionEventNotifier
import xyz.hihasan.ledgerlite.core.model.Transaction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Posts a local notification when a transaction is added. Tapping it deep-links to
 * `ledgerlite://transaction/{id}`, which the app's NavHost resolves to Transaction Detail.
 * Used for deep-link / notification-tap testing (UI Automator).
 */
@Singleton
class TransactionNotifier @Inject constructor(
    @ApplicationContext private val context: Context,
) : TransactionEventNotifier {

    fun ensureChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Transactions",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply { description = "Alerts when a transaction is recorded" }
            context.getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    override fun onTransactionAdded(transaction: Transaction) {
        ensureChannel()
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val deepLink = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("$DEEP_LINK_SCHEME://transaction/${transaction.id}"),
        ).setPackage(context.packageName)

        val pending = PendingIntent.getActivity(
            context,
            transaction.id.hashCode(),
            deepLink,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.stat_sys_upload_done)
            .setContentTitle("Transaction recorded")
            .setContentText("${transaction.description} · ${transaction.currency} ${transaction.amount.majorUnits}")
            .setAutoCancel(true)
            .setContentIntent(pending)
            .build()

        NotificationManagerCompat.from(context).notify(transaction.id.hashCode(), notification)
    }

    companion object {
        const val CHANNEL_ID = "transactions"
        const val DEEP_LINK_SCHEME = "ledgerlite"
    }
}
