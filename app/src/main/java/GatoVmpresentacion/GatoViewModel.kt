package com.example.juegogato.GatoVmpresentacion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GatoViewModel : ViewModel() {

    // 1. Estado del tablero (lista de 9 casillas)
    private val _tablero = MutableStateFlow(List(9) { "" })
    val tablero: StateFlow<List<String>> = _tablero.asStateFlow()

    // 2. Estado del jugador actual ("X" u "O")
    private val _jugadorActual = MutableStateFlow("X")
    val jugadorActual: StateFlow<String> = _jugadorActual.asStateFlow()

    // 3. Estado del mensaje informativo
    private val _mensajeEstado = MutableStateFlow("Turno de: X")
    val mensajeEstado: StateFlow<String> = _mensajeEstado.asStateFlow()

    // 4. Estado para saber si el juego ya terminó
    private val _juegoTerminado = MutableStateFlow(false)
    val juegoTerminado: StateFlow<Boolean> = _juegoTerminado.asStateFlow()

    // Función que se ejecuta cuando el usuario presiona una casilla (0 a 8)
    fun seleccionarCasilla(index: Int) {
        // Si la casilla ya está ocupada o el juego terminó, no hace nada
        if (_tablero.value[index].isNotEmpty() || _juegoTerminado.value) return

        // Crear copia del tablero y asignar la marca del jugador
        val nuevoTablero = _tablero.value.toMutableList()
        nuevoTablero[index] = _jugadorActual.value
        _tablero.value = nuevoTablero

        // Comprobar si el jugador actual ganó
        if (verificarGanador(nuevoTablero, _jugadorActual.value)) {
            _mensajeEstado.value = "¡Ganó el jugador ${_jugadorActual.value}!"
            _juegoTerminado.value = true
        }
        // Comprobar si hay empate
        else if (!nuevoTablero.contains("")) {
            _mensajeEstado.value = "¡Es un Empate!"
            _juegoTerminado.value = true
        }
        // Cambiar turno si el juego sigue
        else {
            val siguiente = if (_jugadorActual.value == "X") "O" else "X"
            _jugadorActual.value = siguiente
            _mensajeEstado.value = "Turno de: $siguiente"
        }
    }

    // Reinicia todas las variables a su valor inicial
    fun reiniciarJuego() {
        _tablero.value = List(9) { "" }
        _jugadorActual.value = "X"
        _mensajeEstado.value = "Turno de: X"
        _juegoTerminado.value = false
    }

    // Lógica para verificar las combinaciones ganadoras
    private fun verificarGanador(tablero: List<String>, jugador: String): Boolean {
        val combinacionesGanadoras = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8), // Filas
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8), // Columnas
            listOf(0, 4, 8), listOf(2, 4, 6)                  // Diagonales
        )
        return combinacionesGanadoras.any { combo ->
            combo.all { index -> tablero[index] == jugador }
        }
    }
}