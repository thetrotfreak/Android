package com.github.thetrotfreak.muacompose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.github.thetrotfreak.muacompose.ui.theme.MUAComposeTheme

data class Profile(
    var email: String?,
    var name: String?,
    var reg: String?,
    val pfp: ImageVector = Icons.Default.Person
)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MUAComposeTheme {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    val user = remember {
                        mutableStateOf(
                            Profile(
                                email = "bivas.kumar@mca.christuniversity.in",
                                name = "Bivas Kumar",
                                reg = "2347111",
                                pfp = Icons.Default.AccountCircle
                            )
                        )
                    }
                    ProfileCard(user)
                }
            }
        }
    }
}


@Composable
fun ProfileCard(user: MutableState<Profile>) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .paint(
                painterResource(id = R.drawable.official_logo_of_christ_deemed_to_be_university___bangalore),
                alpha = .25f,
                contentScale = ContentScale.Fit
            ), contentAlignment = Alignment.Center

    ) {
        Card(
            modifier = Modifier.padding(16.dp), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ), elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = user.value.email ?: "", style = MaterialTheme.typography.bodySmall
                )

                Spacer(modifier = Modifier.height(8.dp))
                IconButton(onClick = {
                    val intent = Intent(context, ThirdActivity::class.java)
                    intent.putExtra("name", user.value.name)
                    startActivity(context, intent, null)
                }) {
                    Icon(imageVector = user.value.pfp, contentDescription = "Profile icon")
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Hi, ${user.value.name?.uppercase()}!",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedButton(onClick = {
                    val intent = Intent(context, SecondActivity::class.java)
                    val profileBundle = Bundle().apply {
                        putString("email", user.value.email)
                        putString("name", user.value.name)
                        putString("register_number", user.value.reg)
                    }
                    intent.putExtra("profile", profileBundle)
                    startActivity(context, intent, null)
                }) {
                    Text(
                        text = "Manage your Account", style = MaterialTheme.typography.labelMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO)
                        intent.data = "mailto:${user.value.email}".toUri()
                        // https://developer.android.com/guide/components/intents-common.html#Email
                        intent.resolveActivity(context.packageManager) ?: startActivity(
                            context, intent, null
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email to ${user.value.email}"
                        )
                    }
                    IconButton(onClick = {
                        val intent = Intent(
                            Intent.ACTION_VIEW, Uri.parse("https://www.github.com/thetrotfreak")
                        )
                        intent.resolveActivity(context.packageManager) ?: startActivity(
                            context, intent, null
                        )
                    }) {
                        Icon(
                            imageVector = Icons.Default.Public,
                            contentDescription = "GitHub profile"
                        )
                    }
                    IconButton(onClick = {
                        val intent = Intent(context, FeedBackActivity::class.java)
                        startActivity(context, intent, null)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Feedback, contentDescription = null
                        )
                    }
                }
            }
        }
    }
}