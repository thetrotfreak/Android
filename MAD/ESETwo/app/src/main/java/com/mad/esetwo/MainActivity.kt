package com.mad.esetwo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.edit
import com.mad.esetwo.ui.theme.ESETwoTheme
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ESETwoTheme {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Form()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Form(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dataStore = context.dataStore
    val scope = rememberCoroutineScope()

    // Read the preference value
    val agreedToSkip = produceState<Boolean>(initialValue = false) {
        dataStore.data.map { preferences ->
            preferences[PreferencesKeys.AGREED_TO_SKIP] ?: false
        }.collect {
            value = it
        }
    }.value

    // Write the preference value
    fun writePreference(agreed: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[PreferencesKeys.AGREED_TO_SKIP] = agreed
            }
        }
    }
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    val datePickerState = rememberDatePickerState()
    val openDialog = remember {
        mutableStateOf(false)
    }
    var name by remember {
        mutableStateOf("")
    }
    var birthdate by remember {
        mutableStateOf("")
    }
    val (checkedState, onStateChange) = remember {
        mutableStateOf(false)
    }


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            value = birthdate,
            onValueChange = { },
            readOnly = true,
            label = { Text("Birthdate") },
            trailingIcon = {
                IconButton(onClick = { openDialog.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(16.dp))
        if (openDialog.value) {
            val confirmEnabled = remember {
                derivedStateOf { datePickerState.selectedDateMillis != null }
            }
            DatePickerDialog(
                onDismissRequest = { openDialog.value = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                            birthdate = datePickerState.selectedDateMillis?.let { Date(it) }
                                ?.let { dateFormat.format(it) }!!
                        },
                        enabled = confirmEnabled.value
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
//                checked = checkedState and name.isNotBlank() and birthdate.isNotBlank(),
                checked = !agreedToSkip,
                onCheckedChange = {
                    onStateChange(!checkedState)
                    writePreference(!agreedToSkip)
                }
            )
            Text(
                text = "I agree to the Terms & Conditions of IPL20.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        Spacer(modifier = Modifier.size(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.size(16.dp))
        FilledTonalButton(
            enabled = !agreedToSkip,
            onClick = {
                val intent = Intent(context, HomeActivity::class.java)
                ContextCompat.startActivity(context, intent, null)
            },
        ) {
            Text(text = "Let's Go!")
        }
    }
}