package com.example.kotlin_random_number_and_city_manager

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.kotlin_random_number_and_city_manager.ui.theme.KotlinrandomnumberandcitymanagerTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

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
fun CityModuleButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(300.dp)
            .height(60.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun Menu() {
    val selectedModule = remember { mutableStateOf("Seleccione un Módulo") }
    val buttonsVisible = remember { mutableStateOf(true) }
    val score = remember { mutableStateOf(0) }
    val attempts = remember { mutableStateOf(0) }
    val randomNumber = remember { mutableStateOf(Random.nextInt(1, 6)) }
    val userGuess = remember { mutableStateOf(TextFieldValue("")) }
    val showGameOverDialog = remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("GamePreferences", Context.MODE_PRIVATE)
    val highestScore = remember { mutableStateOf(sharedPreferences.getInt("highestScore", 0)) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HeaderText(selectedModule.value)

        if (buttonsVisible.value) {
            ModuleButton("Adivinar el número", onClick = {
                selectedModule.value = "Adivinar el número entre 1 y 5"
                buttonsVisible.value = false
            })
            Spacer(modifier = Modifier.height(16.dp))
            ModuleButton("Ciudades del mundo", onClick = {
                selectedModule.value = "Ciudades del mundo"
                buttonsVisible.value = false
            })
        } else {
            if (selectedModule.value == "Adivinar el número entre 1 y 5") {
                GameModule(
                    score = score,
                    attempts = attempts,
                    randomNumber = randomNumber,
                    userGuess = userGuess,
                    onGameOver = {
                        showGameOverDialog.value = true
                    },
                    onCorrectGuess = {
                        scope.launch {
                            snackbarHostState.showSnackbar("¡Correcto!", duration = SnackbarDuration.Short)
                        }
                    },
                    onWrongGuess = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Incorrecto, intenta de nuevo", duration = SnackbarDuration.Short)
                        }
                    },
                    highestScore = highestScore,
                    saveHighestScore = { newScore ->
                        with(sharedPreferences.edit()) {
                            putInt("highestScore", newScore)
                            apply()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CityModuleButton("Cargar una ciudad capital", onClick = {
                selectedModule.value = "Cargar una ciudad capital"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })

            Spacer(modifier = Modifier.height(16.dp))

            CityModuleButton("Consultar ciudad por nombre" , onClick = {
                selectedModule.value = "Consultar ciudad por nombre"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })

            Spacer(modifier = Modifier.height(16.dp))

            CityModuleButton("Borrar ciudad por su nombre", onClick = {
                selectedModule.value = "Borrar ciudad por su nombre"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })

            Spacer(modifier = Modifier.height(16.dp))

            CityModuleButton("Borrar todas las ciudades de un pais", onClick = {
                selectedModule.value = "Borrar todas las ciudades de un pais"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })

            Spacer(modifier = Modifier.height(16.dp))
            CityModuleButton("Modificar la población de una ciudad", onClick = {
                selectedModule.value = "Modificar la población de una ciudad"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })

            Spacer(modifier = Modifier.height(16.dp))
            CityModuleButton("Volver al menú", onClick = {
                selectedModule.value = "Seleccione un Módulo"
                buttonsVisible.value = true
                resetGame(score, attempts, randomNumber)
            })
        }

        if (showGameOverDialog.value) {
            AlertDialog(
                onDismissRequest = { showGameOverDialog.value = false },
                title = { Text("Fin del juego") },
                text = { Text("Has fallado 5 veces seguidas. Tu puntaje ha sido reiniciado.") },
                confirmButton = {
                    Button(
                        onClick = {
                            showGameOverDialog.value = false
                            selectedModule.value = "Seleccione un Módulo"
                            buttonsVisible.value = true
                            resetGame(score, attempts, randomNumber)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = White
                        )
                    ) {
                        Text("OK")
                    }
                }
            )
        }
    }

    SnackbarHost(hostState = snackbarHostState)
}

@Composable
fun HeaderText(text: String) {
    Text(
        text = text,
        fontSize = 38.sp,
        lineHeight = 50.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 50.dp)
    )
}

@Composable
fun ModuleButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(180.dp)
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = White
        )
    ) {
        Text(
            text = text,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun GameModule(
    score: MutableState<Int>,
    attempts: MutableState<Int>,
    randomNumber: MutableState<Int>,
    userGuess: MutableState<TextFieldValue>,
    onGameOver: () -> Unit,
    onCorrectGuess: () -> Unit,
    onWrongGuess: () -> Unit,
    highestScore: MutableState<Int>,
    saveHighestScore: (Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Puntaje: ${score.value}",
            fontSize = 30.sp,
            lineHeight = 50.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        Text(
            text = "Puntaje más alto: ${highestScore.value}",
            fontSize = 20.sp,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        OutlinedTextField(
            value = userGuess.value,
            onValueChange = { userGuess.value = it },
            label = { Text("Ingrese un número") },
            modifier = Modifier
                .width(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ModuleButton("Adivinar", onClick = {
            val guess = userGuess.value.text.toIntOrNull()
            if (guess != null && guess in 1..5) {
                if (guess == randomNumber.value) {
                    score.value += 10
                    attempts.value = 0
                    randomNumber.value = Random.nextInt(1, 6)
                    onCorrectGuess()

                    if (score.value > highestScore.value) {
                        highestScore.value = score.value
                        saveHighestScore(score.value)
                    }
                } else {
                    attempts.value += 1
                    onWrongGuess()
                    if (attempts.value == 5) {
                        score.value = 0
                        attempts.value = 0
                        randomNumber.value = Random.nextInt(1, 6)
                        onGameOver()
                    }
                }
            }
        })
    }
}

fun resetGame(
    score: MutableState<Int>,
    attempts: MutableState<Int>,
    randomNumber: MutableState<Int>
) {
    score.value = 0
    attempts.value = 0
    randomNumber.value = Random.nextInt(1, 6)
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
    KotlinrandomnumberandcitymanagerTheme {
        Menu()
    }
}
