package kz.ecodala

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import kz.ecodala.navigation.AppNavigation
import kz.ecodala.ui.theme.EcoDalaTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity.onCreate")

        enableEdgeToEdge()

        setContent {
            EcoDalaTheme {
                AppNavigation()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "MainActivity.onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity.onResume")
    }

    override fun onPause() {
        Log.d(TAG, "MainActivity.onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(TAG, "MainActivity.onStop")
        super.onStop()
    }

    override fun onDestroy() {
        Log.d(TAG, "MainActivity.onDestroy")
        super.onDestroy()
    }

    private companion object {
        const val TAG = "SIS4_LIFECYCLE"
    }
}
