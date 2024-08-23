package com.android.developer.codelabs.composequadrant


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.developer.codelabs.composequadrant.ui.theme.ComposeQuadrantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeQuadrantTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Cartesian()
                }
            }
        }
    }
}

@Composable
fun Cartesian(modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.weight(1F)) {
            Quadrant(
                stringResource(id = R.string.text_composable_function),
                stringResource(id = R.string.text_composable_description),
                Color(0xFFEADDFF),
                Modifier.weight(1F)
            )
            Quadrant(
                stringResource(id = R.string.image_composable_function),
                stringResource(id = R.string.image_composable_description),
                Color(0xFFD0BCFF),
                Modifier.weight(1F)
            )
        }
        Row(Modifier.weight(1F)) {
            Quadrant(
                stringResource(id = R.string.row_composable_function),
                stringResource(id = R.string.row_composable_description),
                Color(0xFFB69DF8),
                Modifier.weight(1F)
            )
            Quadrant(
                stringResource(id = R.string.column_composable_function),
                stringResource(id = R.string.column_composable_description),
                Color(0xFFF6EDFF),
                Modifier.weight(1F)
            )
        }
    }
}

@Composable
fun Quadrant(
    title: String, description: String, backgroundColor: Color, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = backgroundColor)
            .padding(all = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description, color = Color.Black, textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        Cartesian()
    }
}