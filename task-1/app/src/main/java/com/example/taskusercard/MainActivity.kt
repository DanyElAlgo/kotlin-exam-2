package com.example.taskusercard

import ads_mobile_sdk.h6
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType.Companion.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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

fun Context(){ // patron state

}
var status = "Online"
fun onFollowClick() {
    if(status != "Followed"){
    status = "Followed"}
    else{
        status = "Online"
    }
}
@Composable
fun UserCard(nombre: String, fotoUrl: String, onFollowClick: () -> Unit) {
    var status = "Online" // Estado del usuario
//    foto =
    Column(modifier = Modifier.padding(16.dp)) {
        Row(){
            Image(
                painter = painterResource(id = R.drawable.pvz_logo), // Arreglar cuando recuerde cómo se importaban fotos de urls
                contentDescription = null)
            Text(text = nombre)
            Text(text = status) // Estado
        }
        Button( onClick = onFollowClick) {
            Text(text = if (status == "Online") "Follow" else "Unfollow")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskUserCardTheme {
//        Greeting("Android")
        UserCard("Daniel", "https://upload.wikimedia.org/wikipedia/commons/4/47/Plasma_Workspaces.png", ::onFollowClick)
    }
}