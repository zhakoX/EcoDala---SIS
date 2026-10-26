package com.ecodala

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.ecodala.core.data.local.EcoLocalCache
import com.ecodala.core.data.local.LocalProfileStore
import com.ecodala.core.data.repository.LocalEcoRepository
import com.ecodala.core.localization.EcoLocalization
import com.ecodala.core.logging.AppLogger
import com.ecodala.core.navigation.EcoDalaApp
import com.ecodala.core.settings.AppSettingsStore
import com.ecodala.core.ui.theme.EcoDalaTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    companion object {
        // DataStore observers belong to the process,
        // so configuration changes cannot cancel them.
        private val applicationScope = CoroutineScope(
            SupervisorJob() + Dispatchers.Main.immediate
        )
    }

    private var dataReady by mutableStateOf(false)

    private var startupError by mutableStateOf<String?>(null)

    private fun restoreDemoData() {
        startupError = null

        lifecycleScope.launch {
            try {
                LocalEcoRepository.shared.restore()
                LocalProfileStore.awaitReady()

                dataReady = true

            } catch (error: CancellationException) {
                throw error

            } catch (error: Exception) {
                startupError =
                    "Local demo data could not be loaded: ${error.message}"

                Log.e(
                    "SIS4.MainActivity",
                    "Local data load failed",
                    error
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(
            "SIS4.MainActivity",
            "onCreate instance=${hashCode()} " +
                    "restored=${savedInstanceState != null}"
        )

        AppLogger.installCrashLogging()

        // Modern Android edge-to-edge support.
        enableEdgeToEdge()

        AppSettingsStore.initialize(
            context = applicationContext,
            scope = applicationScope
        )

        LocalProfileStore.initialize(
            context = applicationContext,
            scope = applicationScope
        )

        EcoLocalCache.initialize(applicationContext)

        restoreDemoData()

        setContent {
            val settings by AppSettingsStore.settings.collectAsState()

            LaunchedEffect(settings.appLanguageTag) {
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(
                        settings.appLanguageTag
                    )
                )
            }

            EcoLocalization(
                languageTag = settings.appLanguageTag
            ) {
                EcoDalaTheme(
                    darkTheme = settings.darkModeEnabled
                ) {

                    // Configure system bar icon appearance.
                    // Deprecated statusBarColor and navigationBarColor
                    // setters are no longer used.
                    SideEffect {
                        WindowCompat.getInsetsController(
                            window,
                            window.decorView
                        ).apply {
                            isAppearanceLightStatusBars =
                                !settings.darkModeEnabled

                            isAppearanceLightNavigationBars =
                                !settings.darkModeEnabled
                        }
                    }

                    if (dataReady) {
                        EcoDalaApp()
                    } else {
                        Surface {
                            Column {
                                Text(
                                    text = startupError
                                        ?: "Loading local demo…"
                                )

                                if (startupError != null) {
                                    Button(
                                        onClick = ::restoreDemoData
                                    ) {
                                        Text("Retry")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(
            "SIS4.MainActivity",
            "onStart instance=${hashCode()}"
        )
    }

    override fun onResume() {
        super.onResume()

        Log.d(
            "SIS4.MainActivity",
            "onResume instance=${hashCode()}"
        )
    }

    override fun onPause() {
        Log.d(
            "SIS4.MainActivity",
            "onPause instance=${hashCode()}"
        )

        super.onPause()
    }

    override fun onStop() {
        Log.d(
            "SIS4.MainActivity",
            "onStop instance=${hashCode()}"
        )

        super.onStop()
    }

    override fun onDestroy() {
        Log.d(
            "SIS4.MainActivity",
            "onDestroy instance=${hashCode()} " +
                    "changingConfigurations=$isChangingConfigurations"
        )

        super.onDestroy()
    }
}