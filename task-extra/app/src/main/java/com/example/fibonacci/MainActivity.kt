package com.example.fibonacci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        "Android",
                        10,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }

        }
    }
}

fun Fibo(n: Int): Int { // Retornar la secuencia de Fibonacci desde 0 hasta la posición n
    return if (n <= 1) n else Fibo(n - 1) + Fibo(n - 2)
}

@Composable
fun Greeting(name: String, n: Int, modifier: Modifier = Modifier.padding(16.dp)) {
    var textInput by remember { mutableStateOf("10") }
    // State for the number used in calculation (Int)
    var fiboNumber by remember { mutableIntStateOf(10) }
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        OutlinedTextField(
            value = textInput,
            onValueChange = { textInput = it },
            label = { Text("Enter a number (n)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                // Convert string input to Int, default to 0 if invalid
                fiboNumber = textInput.toIntOrNull() ?: 0
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Calculate")
        }
        Text(
            text = "Fibonacci of $n is ${Fibo(10)}",
            modifier = modifier
        )
        Row {
            var x = 0
            while (x <= n) {
                Text(
                    text = "${Fibo(x)}; ",
                    modifier = modifier
                )
                x += 1
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
        Greeting("Android", 10)
    }
}
