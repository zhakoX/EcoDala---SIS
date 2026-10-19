package com.ecodala.core.settings

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException

data class AppSettings(
    val darkModeEnabled: Boolean = false,
    val preferredUnit: String = "kg",
    val appLanguageTag: String = "en"
)

private val Context.appSettingsDataStore by preferencesDataStore(name = "app_settings")

object AppSettingsStore {
    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings

    private var appContext: Context? = null
    private var appScope: CoroutineScope? = null

    private val darkModeKey = booleanPreferencesKey("dark_mode_enabled")
    private val preferredUnitKey = stringPreferencesKey("preferred_unit")
    private val appLanguageTagKey = stringPreferencesKey("app_language_tag")

    fun initialize(context: Context, scope: CoroutineScope) {
        if (appContext != null) return

        appContext = context.applicationContext
        appScope = scope

        scope.launch {
            context.applicationContext.appSettingsDataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        emit(androidx.datastore.preferences.core.emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    AppSettings(
                        darkModeEnabled = preferences[darkModeKey] ?: false,
                        preferredUnit = preferences[preferredUnitKey] ?: "kg",
                        appLanguageTag = preferences[appLanguageTagKey] ?: "en"
                    )
                }
                .collect { savedSettings ->
                    _settings.value = savedSettings
                }
        }
    }

    fun update(transform: (AppSettings) -> AppSettings) {
        _settings.update(transform)

        val context = appContext ?: return
        val scope = appScope ?: return
        val newSettings = _settings.value

        scope.launch {
            context.appSettingsDataStore.edit { preferences ->
                preferences[darkModeKey] = newSettings.darkModeEnabled
                preferences[preferredUnitKey] = newSettings.preferredUnit
                preferences[appLanguageTagKey] = newSettings.appLanguageTag
            }
        }
    }
}
