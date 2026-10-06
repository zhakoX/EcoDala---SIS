package kz.ecodala

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kz.ecodala.navigation.AppNavigation
import kz.ecodala.ui.theme.EcoDalaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            EcoDalaTheme {
                AppNavigation()
            }
        }
    }
}