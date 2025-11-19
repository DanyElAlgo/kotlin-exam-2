package com.example.camerapermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.camerapermission.ui.theme.CameraPermissionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CameraPermissionTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    permissionTest( modifier = Modifier.padding(innerPadding))
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
fun permissionTest(modifier: Modifier = Modifier){
    val context = LocalContext.current

    // Estados para saber si los permisos han sido concedidos
    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCameraPermission = isGranted
            if (isGranted) {
                Toast.makeText(context, "Permiso de cámara CONCEDIDO", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Permiso de cámara DENEGADO", Toast.LENGTH_SHORT).show()
            }
        }
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = if (hasCameraPermission) "Permiso de cámara: SÍ" else "Permiso de cámara: NO")
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                // Si el permiso ya está concedido, no hacemos nada.
                // Si no, lanzamos la solicitud. [10]
                if (!hasCameraPermission) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    Toast.makeText(context, "El permiso de cámara ya fue concedido", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Acceder a la cámara")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CameraPermissionTheme {
//        Greeting("Android")
        permissionTest()
    }
}