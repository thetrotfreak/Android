package com.mad.finale

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val dataStore = context.dataStore
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val image = painterResource(id = R.drawable.androidparty)

    val scope = rememberCoroutineScope()
    val datePickerState = rememberDatePickerState()
    val openDialog = remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var birthdate by remember { mutableStateOf("") }
    var checked by remember { mutableStateOf(false) }

//    val onboarded = produceState(initialValue = false) {
//        dataStore.data.map { preferences ->
//            preferences[PreferencesKeys.ONBOARDED] ?: false
//        }.collect {
//            value = it
//        }
//    }.value

    fun writePreference(key: Preferences.Key<Boolean>, value: Boolean) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    fun writePreference(key: Preferences.Key<String>, value: String) {
        scope.launch {
            dataStore.edit { preferences ->
                preferences[key] = value
            }
        }
    }

    Box {
//        Image(
//            painter = image,
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            alpha = 0.25F
//        )
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Let's go for shopping!",
                fontSize = 64.sp,
                lineHeight = 64.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            OutlinedTextField(
                value = name,
                onValueChange = {
                    writePreference(PreferencesKeys.NAME, it)
                    name = it
                },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    writePreference(PreferencesKeys.EMAIL, it)
                    email = it
                },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            PasswordTextField()
            OutlinedTextField(
                value = birthdate,
                onValueChange = {
                    writePreference(PreferencesKeys.BIRTHDATE, it)
                },
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
                modifier = Modifier.fillMaxWidth()
            )
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
                                writePreference(PreferencesKeys.BIRTHDATE, birthdate)
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
                        showModeToggle = true,
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        writePreference(PreferencesKeys.ONBOARDED, it)
                    }
                )
                Text(
                    text = "I have read and agree to all Terms & Conditions.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Button(
                enabled = checked,
                onClick = {
                    val intent = Intent(context, HomeActivity::class.java)
                    ContextCompat.startActivity(context, intent, null)
                },
            ) {
                Text(text = "Start Shopping!")
            }
        }
    }
}

@Composable
fun PasswordTextField(modifier: Modifier = Modifier) {
    var password by rememberSaveable { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = password,
        onValueChange = {
            password = it
        },
        placeholder = { Text(text = "Password") },
        label = { Text(text = "Password") },
        trailingIcon = {
            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }) {
                if (passwordVisibility)
                    Icon(imageVector = Icons.Filled.Visibility, contentDescription = null)
                else
                    Icon(imageVector = Icons.Filled.VisibilityOff, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = if (passwordVisibility) VisualTransformation.None
        else PasswordVisualTransformation()
    )
}
