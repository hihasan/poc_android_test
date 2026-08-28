package xyz.hihasan.ledgerlite.core.designsystem.testing

/**
 * Central registry of Compose `testTag` values. Screens tag their key nodes with these so that
 * Compose UI tests, UI Automator flows, and E2E tests can reference them by a stable name.
 *
 * Convention: `"<screen>_<element>"`, lower_snake_case. List items append `"_<id>"` at runtime
 * via [transactionRow].
 */
object LedgerTestTags {

    // --- Login / Register -------------------------------------------------
    const val LOGIN_SCREEN = "login_screen"
    const val LOGIN_EMAIL_FIELD = "login_email_field"
    const val LOGIN_PASSWORD_FIELD = "login_password_field"
    const val LOGIN_SUBMIT_BUTTON = "login_submit_button"
    const val LOGIN_BIOMETRIC_BUTTON = "login_biometric_button"
    const val LOGIN_ERROR_TEXT = "login_error_text"
    const val REGISTER_SCREEN = "register_screen"
    const val REGISTER_NAME_FIELD = "register_name_field"
    const val REGISTER_EMAIL_FIELD = "register_email_field"
    const val REGISTER_PASSWORD_FIELD = "register_password_field"
    const val REGISTER_CONFIRM_PASSWORD_FIELD = "register_confirm_password_field"
    const val REGISTER_SUBMIT_BUTTON = "register_submit_button"

    // --- Dashboard ------------------------------------------------------------
    const val DASHBOARD_SCREEN = "dashboard_screen"
    const val DASHBOARD_BALANCE_TEXT = "dashboard_balance_text"
    const val DASHBOARD_SPENDING_CHART = "dashboard_spending_chart"
    const val DASHBOARD_ADD_FAB = "dashboard_add_fab"
    const val DASHBOARD_CATEGORY_LEGEND = "dashboard_category_legend"

    // --- Transaction list / detail -----------------------------------------
    const val TRANSACTION_LIST_SCREEN = "transaction_list_screen"
    const val TRANSACTION_LIST = "transaction_list"
    const val TRANSACTION_LIST_LOADING = "transaction_list_loading"
    const val TRANSACTION_LIST_EMPTY = "transaction_list_empty"
    const val TRANSACTION_ROW_PREFIX = "transaction_row_"
    const val TRANSACTION_DETAIL_SCREEN = "transaction_detail_screen"
    const val TRANSACTION_DETAIL_AMOUNT = "transaction_detail_amount"
    const val TRANSACTION_DETAIL_DELETE_BUTTON = "transaction_detail_delete_button"

    fun transactionRow(id: String): String = TRANSACTION_ROW_PREFIX + id

    // --- Add expense / transfer -------------------------------------------
    const val ADD_TX_SCREEN = "add_tx_screen"
    const val ADD_TX_TYPE_TOGGLE = "add_tx_type_toggle"
    const val ADD_TX_AMOUNT_FIELD = "add_tx_amount_field"
    const val ADD_TX_DESCRIPTION_FIELD = "add_tx_description_field"
    const val ADD_TX_CATEGORY_DROPDOWN = "add_tx_category_dropdown"
    const val ADD_TX_ACCOUNT_DROPDOWN = "add_tx_account_dropdown"
    const val ADD_TX_COUNTERPARTY_DROPDOWN = "add_tx_counterparty_dropdown"
    const val ADD_TX_NOTE_FIELD = "add_tx_note_field"
    const val ADD_TX_SAVE_BUTTON = "add_tx_save_button"
    const val ADD_TX_AMOUNT_ERROR = "add_tx_amount_error"
    const val ADD_TX_DESCRIPTION_ERROR = "add_tx_description_error"

    // --- Search / filter ----------------------------------------------------
    const val SEARCH_SCREEN = "search_screen"
    const val SEARCH_QUERY_FIELD = "search_query_field"
    const val SEARCH_FILTER_BUTTON = "search_filter_button"
    const val SEARCH_RESULTS_LIST = "search_results_list"
    const val SEARCH_TYPE_CHIP_PREFIX = "search_type_chip_"
    const val SEARCH_CATEGORY_CHIP_PREFIX = "search_category_chip_"

    // --- Settings ---------------------------------------------------------
    const val SETTINGS_SCREEN = "settings_screen"
    const val SETTINGS_DARK_THEME_SWITCH = "settings_dark_theme_switch"
    const val SETTINGS_BIOMETRIC_SWITCH = "settings_biometric_switch"
    const val SETTINGS_CURRENCY_DROPDOWN = "settings_currency_dropdown"
    const val SETTINGS_SEED_DATA_BUTTON = "settings_seed_data_button"
    const val SETTINGS_LOGOUT_BUTTON = "settings_logout_button"

    // --- Scaffolding common to all screens -------------------------------
    const val TOP_APP_BAR = "top_app_bar"
    const val BOTTOM_NAV = "bottom_nav"
    const val BACK_BUTTON = "back_button"
}
