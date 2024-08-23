package com.mad.finale

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.preferencesDataStore
import com.mad.finale.ui.theme.FinaleTheme
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "onboarding")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FinaleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val onboarded = produceState(initialValue = false) {
                        dataStore.data.map { preferences ->
                            preferences[PreferencesKeys.ONBOARDED] ?: false
                        }.collect {
                            value = it
                        }
                    }.value
                    if (onboarded) {
                        val intent = Intent(this, HomeActivity::class.java)
                        ContextCompat.startActivity(this, intent, null)
                    } else {
                        Login()
                    }
                }
            }
        }
    }
}
