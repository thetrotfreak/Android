package com.github.thetrotfreak.theactivitylifecycle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import com.github.thetrotfreak.theactivitylifecycle.ui.theme.TheActivityLifecycleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        Log.i("[INFO]", Lifecycle.State.CREATED.name)
        setContent {
            TheActivityLifecycleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Bivas Kumar")
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i("[INFO]", Lifecycle.State.STARTED.name)
    }

    override fun onResume() {
        super.onResume()
        Log.i("[INFO]", Lifecycle.State.RESUMED.name)
    }

    override fun onPause() {
        super.onPause()
        Log.i("[INFO]", Lifecycle.Event.ON_PAUSE.name)
    }

    override fun onStop() {
        super.onStop()
        Log.i("[INFO]", Lifecycle.Event.ON_STOP.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("[INFO]", Lifecycle.State.DESTROYED.name)
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(32.dp),
    ) {
        Text(
            text = name,
            modifier = modifier,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "2347111",
            modifier = modifier,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}