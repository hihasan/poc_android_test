package xyz.hihasan.ledgerlite.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.hihasan.ledgerlite.core.common.result.LedgerError
import xyz.hihasan.ledgerlite.core.common.result.LedgerResult
import xyz.hihasan.ledgerlite.core.domain.repository.AuthRepository
import xyz.hihasan.ledgerlite.core.model.AuthSession
import xyz.hihasan.ledgerlite.core.model.User
import xyz.hihasan.ledgerlite.core.network.api.LedgerApi
import xyz.hihasan.ledgerlite.core.network.dto.AuthRequest
import xyz.hihasan.ledgerlite.core.network.dto.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: LedgerApi,
    private val dataStore: DataStore<Preferences>,
) : AuthRepository {

    override val session: Flow<AuthSession?> = dataStore.data.map { prefs ->
        val token = prefs[KEY_ACCESS] ?: return@map null
        AuthSession(
            user = User(
                id = prefs[KEY_USER_ID].orEmpty(),
                email = prefs[KEY_EMAIL].orEmpty(),
                displayName = prefs[KEY_NAME].orEmpty(),
            ),
            accessToken = token,
            refreshToken = prefs[KEY_REFRESH].orEmpty(),
        )
    }

    override suspend fun login(email: String, password: String): LedgerResult<AuthSession> =
        authenticate { api.login(AuthRequest(email, password)) }

    override suspend fun register(
        email: String,
        password: String,
        displayName: String,
    ): LedgerResult<AuthSession> =
        authenticate { api.register(AuthRequest(email, password, displayName)) }

    override suspend fun logout() {
        dataStore.edit { it.clear() }
    }

    private suspend fun authenticate(call: suspend () -> AuthResponse): LedgerResult<AuthSession> = try {
        val response = call()
        dataStore.edit { prefs ->
            prefs[KEY_USER_ID] = response.userId
            prefs[KEY_EMAIL] = response.email
            prefs[KEY_NAME] = response.displayName
            prefs[KEY_ACCESS] = response.accessToken
            prefs[KEY_REFRESH] = response.refreshToken
        }
        LedgerResult.Success(
            AuthSession(
                user = User(response.userId, response.email, response.displayName),
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
            ),
        )
    } catch (t: Throwable) {
        LedgerResult.Failure(LedgerError.Network(t.message))
    }

    private companion object {
        val KEY_USER_ID = stringPreferencesKey("auth_user_id")
        val KEY_EMAIL = stringPreferencesKey("auth_email")
        val KEY_NAME = stringPreferencesKey("auth_name")
        val KEY_ACCESS = stringPreferencesKey("auth_access_token")
        val KEY_REFRESH = stringPreferencesKey("auth_refresh_token")
    }
}
