package com.ecodala

import android.os.Bundle
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ecodala.core.data.repository.LocalEcoRepository
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.toArgb
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import com.ecodala.core.data.local.EcoLocalCache
import com.ecodala.core.logging.AppLogger
import com.ecodala.core.localization.EcoLocalization
import com.ecodala.core.navigation.EcoDalaApp
import com.ecodala.core.session.SessionManager
import com.ecodala.core.settings.AppSettingsStore
import com.ecodala.core.ui.theme.EcoDalaTheme

class MainActivity : ComponentActivity() {
    companion object {
        // DataStore observers belong to the process, so rotation cannot cancel them.
        private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
    }
    private var dataReady by mutableStateOf(false)
    private var startupError by mutableStateOf<String?>(null)

    private fun restoreDemoData() {
        startupError = null
        lifecycleScope.launch {
            try {
                LocalEcoRepository.shared.restore()
                SessionManager.awaitReady()
                dataReady = true
            } catch (error: Exception) {
                if (error is kotlinx.coroutines.CancellationException) throw error
                startupError = "Local demo data could not be loaded: ${error.message}"
                Log.e("SIS4.MainActivity", "Local data load failed", error)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("SIS4.MainActivity", "onCreate instance=${hashCode()} restored=${savedInstanceState != null}")
        AppLogger.installCrashLogging()
        enableEdgeToEdge()
        AppSettingsStore.initialize(
            context = applicationContext,
            scope = applicationScope
        )
        SessionManager.initialize(
            context = applicationContext,
            scope = applicationScope
        )
        EcoLocalCache.initialize(applicationContext)
        restoreDemoData()

        setContent {
            val settings by AppSettingsStore.settings.collectAsState()

            LaunchedEffect(settings.appLanguageTag) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(settings.appLanguageTag)
                )
            }

            EcoLocalization(languageTag = settings.appLanguageTag) {
                EcoDalaTheme(darkTheme = settings.darkModeEnabled) {
                    val systemBarColor = MaterialTheme.colorScheme.background.toArgb()
                    val navigationBarColor = MaterialTheme.colorScheme.surface.toArgb()

                    SideEffect {
                        window.statusBarColor = systemBarColor
                        window.navigationBarColor = navigationBarColor
                        WindowCompat.getInsetsController(window, window.decorView).apply {
                            isAppearanceLightStatusBars = !settings.darkModeEnabled
                            isAppearanceLightNavigationBars = !settings.darkModeEnabled
                        }
                    }

                    if (dataReady) EcoDalaApp() else androidx.compose.material3.Surface {
                        androidx.compose.foundation.layout.Column {
                            androidx.compose.material3.Text(startupError ?: "Loading local demo…")
                            if (startupError != null) androidx.compose.material3.Button(onClick = ::restoreDemoData) {
                                androidx.compose.material3.Text("Retry")
                            }
                        }
                    }
                }
            }
        }
    }
    override fun onStart() { super.onStart(); Log.d("SIS4.MainActivity", "onStart instance=${hashCode()}") }
    override fun onResume() { super.onResume(); Log.d("SIS4.MainActivity", "onResume instance=${hashCode()}") }
    override fun onPause() { Log.d("SIS4.MainActivity", "onPause instance=${hashCode()}"); super.onPause() }
    override fun onStop() { Log.d("SIS4.MainActivity", "onStop instance=${hashCode()}"); super.onStop() }
    override fun onDestroy() {
        Log.d("SIS4.MainActivity", "onDestroy instance=${hashCode()} changingConfigurations=$isChangingConfigurations")
        super.onDestroy()
    }

}
