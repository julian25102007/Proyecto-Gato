package com.example.juegogato.com.example.juegogato.GatoVmpresentacion

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GatoViewModel : ViewModel() {

    private val _puntosX = MutableStateFlow(0)
    val puntosX: StateFlow<Int> = _puntosX.asStateFlow()

    private val _puntosO = MutableStateFlow(0)
    val puntosO: StateFlow<Int> = _puntosO.asStateFlow()

    private val _tablero = MutableStateFlow(List(9) { "" })
    val tablero: StateFlow<List<String>> = _tablero.asStateFlow()

    private val _jugadorActual = MutableStateFlow("X")
    val jugadorActual: StateFlow<String> = _jugadorActual.asStateFlow()

    private val _mensajeEstado = MutableStateFlow("Turno de: X")
    val mensajeEstado: StateFlow<String> = _mensajeEstado.asStateFlow()

    private val _juegoTerminado = MutableStateFlow(false)
    val juegoTerminado: StateFlow<Boolean> = _juegoTerminado.asStateFlow()

    fun seleccionarCasilla(index: Int) {
        if (_tablero.value[index].isNotEmpty() || _juegoTerminado.value) return

        val nuevoTablero = _tablero.value.toMutableList()
        nuevoTablero[index] = _jugadorActual.value
        _tablero.value = nuevoTablero

        val hayGanador = verificarGanador(nuevoTablero, _jugadorActual.value)

        if (hayGanador) {
            _mensajeEstado.value = "¡Ganó el jugador ${_jugadorActual.value}!"
            if (_jugadorActual.value == "X") {
                _puntosX.value++
            } else {
                _puntosO.value++
            }
            _juegoTerminado.value = true
        } else if (!nuevoTablero.contains("")) {
            _mensajeEstado.value = "¡Es un Empate!"
            _juegoTerminado.value = true
        } else {
            val siguiente = if (_jugadorActual.value == "X") "O" else "X"
            _jugadorActual.value = siguiente
            _mensajeEstado.value = "Turno de: $siguiente"
        }
    }

    fun reiniciarJuego() {
        _tablero.value = List(9) { "" }
        _jugadorActual.value = "X"
        _juegoTerminado.value = false
        _mensajeEstado.value = "Turno de: X"
    }

    fun reiniciarMarcador() {
        _puntosX.value = 0
        _puntosO.value = 0
    }

    private fun verificarGanador(tablero: List<String>, jugador: String): Boolean {
        val combinacionesGanadoras = listOf(
            listOf(0, 1, 2), listOf(3, 4, 5), listOf(6, 7, 8),
            listOf(0, 3, 6), listOf(1, 4, 7), listOf(2, 5, 8),
            listOf(0, 4, 8), listOf(2, 4, 6)
        )
        return combinacionesGanadoras.any { combo ->
            combo.all { index -> tablero[index] == jugador }
        }
    }
}