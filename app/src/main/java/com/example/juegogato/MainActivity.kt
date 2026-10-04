package com.example.juegogato

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.juegogato.com.example.juegogato.GatoVmpresentacion.GatoViewModel
import com.example.juegogato.com.example.juegogato.GatoVmpresentacion.GatoVMPage
import com.example.juegogato.ui.theme.JuegoGatoTheme

class MainActivity : ComponentActivity() {
    private val viewModel: GatoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JuegoGatoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        GatoVMPage(viewModel = viewModel)
                    }
                }
            }
        }
    }
}