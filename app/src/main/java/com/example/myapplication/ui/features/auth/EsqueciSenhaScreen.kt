package com.example.myapplication.ui.features.auth
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun EsqueciSenhaScreen(onBackClick: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Text("TELA DE ESQUECI SENHA EM CONSTRUCAO")
        Button(onClick = onBackClick) { Text("Voltar para o Login") }
    }
}
