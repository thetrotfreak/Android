package com.github.thetrotfreak.muacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled._123
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.thetrotfreak.muacompose.ui.theme.MUAComposeTheme

class SecondActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MUAComposeTheme {
                Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                    ProfileDetails(this.intent.getBundleExtra("profile"))
                }
            }
        }
    }
}

@Composable
fun ProfileDetails(profileBundle: Bundle?) {
    val profile = remember {
        mutableStateOf(
            Profile(
                email = profileBundle?.getString("email", ""),
                name = profileBundle?.getString("name", ""),
                reg = profileBundle?.getString("register_number", "")
            )
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        OutlinedTextField(
            value = profile.value.name ?: "",
            onValueChange = {
                profile.value = profile.value.copy(name = it)
            },
            label = { Text("Name") },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = profile.value.reg ?: "",
            onValueChange = { profile.value = profile.value.copy(reg = it) },
            label = { Text("Register") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled._123, contentDescription = null
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = profile.value.email ?: "",
            onValueChange = { profile.value = profile.value.copy(email = it) },
            label = { Text("Email") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

