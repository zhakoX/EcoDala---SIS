package com.ecodala.core.data.local

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.io.IOException

data class LocalProfile(val fullName: String? = null, val email: String? = null)

private val Context.profileDataStore by preferencesDataStore(name = "sis4_local_profile")

/** Optional local display name and contact; no account or authentication state. */
object LocalProfileStore {
    private val _profile = MutableStateFlow(LocalProfile())
    val profile = _profile.asStateFlow()
    private val ready = MutableStateFlow(false)
    private var appContext: Context? = null
    private val nameKey = stringPreferencesKey("name")
    private val emailKey = stringPreferencesKey("email")

    suspend fun awaitReady() { ready.first { it } }

    fun initialize(context: Context, scope: CoroutineScope) {
        if (appContext != null) return
        appContext = context.applicationContext
        scope.launch {
            context.applicationContext.profileDataStore.data
                .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
                .collect {
                    _profile.value = LocalProfile(it[nameKey], it[emailKey])
                    ready.value = true
                }
        }
    }

    suspend fun updateProfile(fullName: String, email: String) {
        appContext?.profileDataStore?.edit {
            it[nameKey] = fullName
            it[emailKey] = email
        }
        _profile.value = LocalProfile(fullName, email)
    }
}
