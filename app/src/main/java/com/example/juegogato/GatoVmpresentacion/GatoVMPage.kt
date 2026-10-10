package com.example.juegogato.GatoVmpresentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

// Paleta de colores para el Tema Oscuro
val DarkBackground = Color(0xFF121212)
val DarkSurface = Color(0xFF1E1E1E)
val PrimaryCard = Color(0xFF2D2D2D)
val BorderColor = Color(0xFF555555)
val AccentRed = Color(0xFFD32F2F)
val AccentBlue = Color(0xFF3F51B5)
val TextWhite = Color(0xFFEEEEEE)

// 1. COMPOSABLE PRINCIPAL (Pantalla contenedora)
@Composable
fun GatoVMPage(viewModel: GatoViewModel = viewModel()) {
    val tablero by viewModel.tablero.collectAsState()
    val mensajeEstado by viewModel.mensajeEstado.collectAsState()
    val puntosX by viewModel.puntosX.collectAsState()
    val puntosO by viewModel.puntosO.collectAsState()
    val nombreCreador by viewModel.nombreCreador.collectAsState()
    val matricula by viewModel.matricula.collectAsState()
    val mostrarPerfil by viewModel.mostrarPerfil.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(16.dp))


            if (mostrarPerfil) {
                Profile(
                    nombre = nombreCreador,
                    matricula = matricula,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            CuadroEstado(mensajeEstado = mensajeEstado)
            CuadroInformacion(puntosX = puntosX, puntosO = puntosO)
        }

        TableroGato(
            tablero = tablero,
            onCasillaSeleccionada = { index -> viewModel.seleccionarCasilla(index) }
        )

        BotonesControl(
            onReiniciarMarcador = { viewModel.reiniciarMarcador() },
            onReiniciarJuego = { viewModel.reiniciarJuego() },
            onMostrarPerfil = { viewModel.profile() }
        )
    }
}

// 2. COMPOSABLE: Estado / Turno
@Composable
fun CuadroEstado(mensajeEstado: String) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkSurface),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = mensajeEstado,
            color = TextWhite,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )
    }
}

// 3. COMPOSABLE: Cuadro de Información (Marcador)
@Composable
fun CuadroInformacion(puntosX: Int, puntosO: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BorderColor, RoundedCornerShape(8.dp))
            .background(DarkSurface, RoundedCornerShape(8.dp))
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Información",
                color = Color.Gray,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Jugador 1 (X): $puntosX  |  Jugador 2 (O): $puntosO",
                color = TextWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// 4. COMPOSABLE: Tablero 3x3
@Composable
fun TableroGato(
    tablero: List<String>,
    onCasillaSeleccionada: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .aspectRatio(1f)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (row in 0..2) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (col in 0..2) {
                    val index = row * 3 + col
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(PrimaryCard, RoundedCornerShape(8.dp))
                            .clickable { onCasillaSeleccionada(index) }
                    ) {
                        Text(
                            text = tablero[index],
                            color = if (tablero[index] == "X") Color(0xFFFF5252) else Color(0xFF448AFF),
                            fontSize = 36.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// 5. COMPOSABLE: Botones de Acción
@Composable
fun BotonesControl(
    onReiniciarMarcador: () -> Unit,
    onReiniciarJuego: () -> Unit,
    onMostrarPerfil: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onReiniciarMarcador,
            colors = ButtonDefaults.buttonColors(containerColor = AccentRed),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Reiniciar Marcador", color = TextWhite, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onReiniciarJuego,
            colors = ButtonDefaults.buttonColors(containerColor = AccentBlue),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Reiniciar Juego", color = TextWhite, fontWeight = FontWeight.Bold)
        }

        Button(
            onClick = onMostrarPerfil,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(text = "Mostrar Perfil", color = TextWhite, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun Profile(
    nombre: String,
    matricula: String,
    modifier: Modifier = Modifier
) {
    if (nombre.isNotEmpty() && matricula.isNotEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .border(1.dp, BorderColor, RoundedCornerShape(12.dp))
                .background(DarkSurface, RoundedCornerShape(12.dp))
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Perfil de Usuario",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Nombre: $nombre",
                    color = TextWhite,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Matrícula: $matricula",
                    color = TextWhite,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}