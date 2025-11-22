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

fun Fibo(n: Int): Int {
    return if (n <= 1) n else Fibo(n - 1) + Fibo(n - 2)
}

@Composable
fun Greeting(name: String, n: Int, modifier: Modifier = Modifier.padding(16.dp)) {
    var textInput by remember { mutableStateOf("10") }
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
                fiboNumber = textInput.toIntOrNull() ?: 0
            },
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Calculate")
        }
        Text(
            text = "Fibonacci of $fiboNumber is ${Fibo(fiboNumber)}",
            modifier = modifier
        )
        Row(modifier = Modifier.padding(top = 8.dp)) {
            var x = 0
            // Limit loop to prevent freezing if user types a huge number accidentally
            val safeLimit = if (fiboNumber > 20) 20 else fiboNumber

            var sequenceString = ""
            while (x <= safeLimit) {
                sequenceString += "${Fibo(x)}; "
                x += 1
            }
            Text(text = sequenceString)
        }
        if (fiboNumber > 20) {
            Text("(Sequence display capped at 20 for UI safety)")
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
