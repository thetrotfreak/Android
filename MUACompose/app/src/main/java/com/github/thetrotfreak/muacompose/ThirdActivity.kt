package com.github.thetrotfreak.muacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.github.thetrotfreak.muacompose.ui.theme.MUAComposeTheme

class ThirdActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MUAComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    ProfileBio(this.intent.getStringExtra("name") ?: "")
                }
            }
        }
    }
}

@Composable
fun ProfileBio(name: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Image(
            painter = painterResource(id = R.drawable.pfp),
            contentDescription = "User's profile picture",
            modifier = Modifier
                .size(256.dp)
                .clip(CircleShape)
                .border(
                    width = .25.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape,
                )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Hey, I am ${name}. Nice to meet ya!",
            fontStyle = FontStyle.Italic,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}