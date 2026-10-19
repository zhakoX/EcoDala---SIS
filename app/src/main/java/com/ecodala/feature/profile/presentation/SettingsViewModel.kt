package com.ecodala.feature.profile.presentation

import androidx.lifecycle.ViewModel
import com.ecodala.core.settings.AppSettings
import com.ecodala.core.settings.AppSettingsStore
import kotlinx.coroutines.flow.StateFlow

class SettingsViewModel : ViewModel() {
    val uiState: StateFlow<AppSettings> = AppSettingsStore.settings

    fun setDarkModeEnabled(value: Boolean) {
        AppSettingsStore.update { it.copy(darkModeEnabled = value) }
    }

    fun setPreferredUnit(value: String) {
        AppSettingsStore.update { it.copy(preferredUnit = value) }
    }

    fun setAppLanguage(value: String) {
        AppSettingsStore.update { it.copy(appLanguageTag = value) }
    }
}
