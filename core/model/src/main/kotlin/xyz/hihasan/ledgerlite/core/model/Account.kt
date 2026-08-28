package xyz.hihasan.ledgerlite.core.model

enum class AccountType { CHECKING, SAVINGS, CREDIT_CARD, CASH, INVESTMENT }

data class Account(
    val id: String,
    val name: String,
    val type: AccountType,
    val currency: String,
    val balance: Money,
)
