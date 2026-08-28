package xyz.hihasan.ledgerlite.core.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PageDto<T>(
    val items: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int,
)

@Serializable
data class TransactionDto(
    val id: String,
    val type: String,
    val category: String,
    @SerialName("amount_minor_units") val amountMinorUnits: Long,
    val currency: String,
    val description: String,
    val note: String? = null,
    @SerialName("timestamp_epoch_millis") val timestampEpochMillis: Long,
    @SerialName("account_id") val accountId: String,
    @SerialName("counterparty_account_id") val counterpartyAccountId: String? = null,
    val pending: Boolean = false,
)

@Serializable
data class AccountDto(
    val id: String,
    val name: String,
    val type: String,
    val currency: String,
    @SerialName("balance_minor_units") val balanceMinorUnits: Long,
)

@Serializable
data class CreateTransactionRequest(
    val type: String,
    val category: String,
    @SerialName("amount_minor_units") val amountMinorUnits: Long,
    val currency: String,
    val description: String,
    val note: String? = null,
    @SerialName("timestamp_epoch_millis") val timestampEpochMillis: Long,
    @SerialName("account_id") val accountId: String,
    @SerialName("counterparty_account_id") val counterpartyAccountId: String? = null,
)

@Serializable
data class AuthRequest(
    val email: String,
    val password: String,
    @SerialName("display_name") val displayName: String? = null,
)

@Serializable
data class AuthResponse(
    @SerialName("user_id") val userId: String,
    val email: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
)
