package com.example.juegogato.com.example.juegogato.GatoVmpresentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.material3.ButtonDefaults
@Composable
fun GatoVMPage(viewModel: GatoViewModel) {

    val tablero by viewModel.tablero.collectAsStateWithLifecycle()
    val mensajeEstado by viewModel.mensajeEstado.collectAsStateWithLifecycle()
    val juegoTerminado by viewModel.juegoTerminado.collectAsStateWithLifecycle()
    val puntosX by viewModel.puntosX.collectAsStateWithLifecycle()
    val puntosO by viewModel.puntosO.collectAsStateWithLifecycle()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (juegoTerminado) Color(0xFFE0F7FA) else Color(0xFFF5F5F5)

            )
        ) {

            Text(
                text = mensajeEstado,
                modifier = Modifier.padding(16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold


            )
        }


        OutlinedTextField(
            value = "Jugador 1: X  |  Jugador 2: O",
            onValueChange = {},
            readOnly = true,
            label = { Text("Información") },
            modifier = Modifier.fillMaxWidth()
        )


        TableroGato(
            tablero = tablero,
            onCasillaClick = { index -> viewModel.seleccionarCasilla(index) }
        )

        Button(
            onClick = { viewModel.reiniciarMarcador() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text("Reiniciar Marcador")
        }

        Button(
            onClick = { viewModel.reiniciarJuego() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Reiniciar Juego")
        }

        Text(
            text = "Marcador -> Jugador X: $puntosX  |  Jugador O: $puntosO",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.DarkGray
        )
    }
}


@Composable
fun TableroGato(
    tablero: List<String>,
    onCasillaClick: (Int) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (i in 0 until 3) {
            // 5. Composable: Row
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (j in 0 until 3) {
                    val index = i * 3 + j

                    CasillaGato(
                        valor = tablero[index],
                        onClick = { onCasillaClick(index) }
                    )
                }
            }
        }
    }
}

@Composable
fun CasillaGato(
    valor: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .background(if (valor.isEmpty()) Color.LightGray else Color(0xFFE0E0E0))
            .clickable(enabled = valor.isEmpty()) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = valor,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = if (valor == "X") Color.Blue else Color.Red
        )
    }
}