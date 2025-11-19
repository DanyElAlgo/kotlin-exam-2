package com.example.fibonacci

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fibonacci.ui.theme.FibonacciTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FibonacciTheme {
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
fun Greeting(name: String, n: Int, modifier: Modifier = Modifier) {
    Column{
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        Text(
            text = "Fibonacci of $n is ${Fibo(10)}",
            modifier = modifier
        )
        Row{
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
    FibonacciTheme {
        Greeting("Android", 10)
    }
}