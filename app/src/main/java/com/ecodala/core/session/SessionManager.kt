package com.ecodala.core.session

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.ecodala.core.domain.model.EcoUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

data class UserSession(
    val isLoggedIn: Boolean = false,
    val userId: String? = null,
    val fullName: String? = null,
    val email: String? = null
)

private val Context.sessionDataStore by preferencesDataStore(name = "sis4_demo_session")

/** Local demonstration identity, without passwords or tokens. */
object SessionManager {
    private val _session = MutableStateFlow(UserSession())
    val session = _session.asStateFlow()
    private val ready = MutableStateFlow(false)
    suspend fun awaitReady() { ready.first { it } }
    private var appContext: Context? = null
    private val loggedInKey = booleanPreferencesKey("logged_in")
    private val userIdKey = stringPreferencesKey("user_id")
    private val nameKey = stringPreferencesKey("name")
    private val emailKey = stringPreferencesKey("email")

    fun initialize(context: Context, scope: CoroutineScope) {
        if (appContext != null) return
        appContext = context.applicationContext
        scope.launch {
            context.applicationContext.sessionDataStore.data
                .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
                .collect { preferences ->
                    _session.value = UserSession(preferences[loggedInKey] ?: false,
                        preferences[userIdKey], preferences[nameKey], preferences[emailKey])
                    ready.value = true
                }
        }
    }

    suspend fun saveSession(user: EcoUser) {
        appContext?.sessionDataStore?.edit {
            it[loggedInKey] = true
            it[userIdKey] = user.id
            it[nameKey] = user.fullName
            it[emailKey] = user.email
        }
        _session.value = UserSession(true, user.id, user.fullName, user.email)
    }

    suspend fun updateProfile(fullName: String, email: String) {
        appContext?.sessionDataStore?.edit {
            it[nameKey] = fullName
            it[emailKey] = email
        }
        _session.update { it.copy(fullName = fullName, email = email) }
    }

    suspend fun clearSession() {
        appContext?.sessionDataStore?.edit { it.clear() }
        _session.value = UserSession()
    }
}
