package com.github.thetrotfreak.calculator2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.thetrotfreak.calculator2.ui.theme.Calculator2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator()
                }
            }
        }
    }
}

@Composable
fun Calculator() {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Display Screen
        DisplayScreen(expression, result)

        // Keypad
        Keypad(onKeyPressed = { key ->
            if (key == "=") {
                result = try {
                    evaluateExpression(expression)
                } catch (e: ArithmeticException) {
//                    "Error"
//                    DivideByZeroException
//                    In case of which, any chain of operations
//                    will not be evaluated
//                    setting the result to this exception
//                    until expression is cleared
                    "Can't Divide By ZERO"
                } catch (e: NumberFormatException) {
//                    "Error"
//                    thrown when string input is empty
                    ""
                }
            } else {
                expression += key
                android.util.Log.d("[INFO]", "Expression: $expression")
                result = try {
                    evaluateExpression(expression)
                } catch (e: ArithmeticException) {
//                    "Error"
                    "Can't divide by ZERO"
                } catch (e: NumberFormatException) {
//                    "Error"
                    ""
                }
                android.util.Log.d("[INFO]", "Result: $result")
                if (result.equals("Can't divide by ZERO")) {
                    expression = ""
                }
                val newExpression: Float? = result.toFloatOrNull()
                if (newExpression != null) {
                    expression = result
                }
            }
        }, onClearPressed = {
            expression = ""
            result = ""
        })
    }
}

@Composable
fun DisplayScreen(expression: String, result: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = expression.trim(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        Text(
            text = result.trim(),
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Composable
fun Keypad(
    onKeyPressed: (String) -> Unit,
    onClearPressed: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        for (row in keypadLayout) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (key in row) {
                    KeyButton(key) {
                        if (key == "AC") {
                            onClearPressed()
                        } else {
                            onKeyPressed(key)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyButton(key: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(8.dp),
    ) {
        Text(text = key, style = MaterialTheme.typography.headlineLarge)
    }
}


//@Composable
fun evaluateExpression(expression: String): String {
    // Remove spaces
    val cleanExpression = expression.replace(" ", "")

    // Evaluate expression
    return cleanExpression.split("+", "-", "*", "/")
        .map { it.toFloatOrNull() ?: throw NumberFormatException("NaN") }.let { values ->
            when {
                "+" in expression -> (values[0] + values[1]).toString()
                "-" in expression -> (values[0] - values[1]).toString()
                "*" in expression -> (values[0] * values[1]).toString()
                "/" in expression -> {
                    if (values[1] == 0f) {
                        throw ArithmeticException("Divide by zero")
                    }
                    (values[0] / values[1]).toString()
                }

                else -> expression
            }
        }
}

@Composable
@Preview(showBackground = true)
fun CalculatorPreview() {
    Calculator2Theme {
        Calculator()
    }
}

val keypadLayout = listOf(
    listOf("7", "8", "9", "/"),
    listOf("4", "5", "6", "*"),
    listOf("1", "2", "3", "-"),
    listOf("AC", "0", "=", "+")
)