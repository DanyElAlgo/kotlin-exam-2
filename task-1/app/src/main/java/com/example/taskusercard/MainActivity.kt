package com.example.taskusercard

import ads_mobile_sdk.h6
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.taskusercard.ui.theme.TaskUserCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskUserCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun UserCard(){
    Column(modifier = Modifier.padding(16.dp)) {
        Row(){
            Image() // Imagen de perfil, accedido mediante vínculo
            Text(text = "John Doe", style = MaterialTheme.typography.h6) // Nombre
            Text() // Estado
        }
        Button(){
            Text(text = "Follow") // Botón de seguir
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskUserCardTheme {
//        Greeting("Android")
        UserCard()
    }
}