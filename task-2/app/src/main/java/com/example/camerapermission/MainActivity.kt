package com.example.camerapermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
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
import androidx.compose.material3.MaterialTheme
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.app.Activity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(this.window, false)
        this.setContent {
            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PermissionTest( modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PermissionTest(modifier: Modifier = Modifier){
    val context = LocalContext.current

    var showSettingsButton by remember { mutableStateOf(false) }

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
                showSettingsButton = false
            } else {
                val activity = context as? Activity
                val shouldShowRationale = activity?.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) == true

                if(!shouldShowRationale) {
                    showSettingsButton = true
                    Toast.makeText(context, "Permiso de cámara DENEGADO PERMANENTEMENTE. Ve a configuración para habilitarlo.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Permiso de cámara DENEGADO", Toast.LENGTH_SHORT).show()
                }
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
                if (!hasCameraPermission) {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                } else {
                    Toast.makeText(context, "El permiso de cámara ya fue concedido", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text("Acceder a la cámara")
        }
        if (showSettingsButton && !hasCameraPermission) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("Ir a Configuración")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
//        Greeting("Android")
        PermissionTest()
    }
}