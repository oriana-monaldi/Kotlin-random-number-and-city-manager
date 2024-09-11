package com.example.kotlin_random_number_and_city_manager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import com.example.kotlin_random_number_and_city_manager.ui.theme.KotlinrandomnumberandcitymanagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinrandomnumberandcitymanagerTheme {
                Menu()
            }
        }
    }
}

@Composable
fun Menu() {
    val selectedModule = remember { mutableStateOf("Ninguno") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Módulo seleccionado: ${selectedModule.value}",
            modifier = Modifier.padding(bottom = 80.dp)
        )

        Button(
            onClick = { selectedModule.value = "Adivinar el número" },
            modifier = Modifier
                .width(180.dp)
                .height(50.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = White
            )
        ) {
            Text(text = "Adivinar el número")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { selectedModule.value = "Ciudades del mundo" },
            modifier = Modifier
                .width(180.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = White
            )
        ) {
            Text(text = "Ciudades del mundo")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    KotlinrandomnumberandcitymanagerTheme {
        Menu()
    }
}