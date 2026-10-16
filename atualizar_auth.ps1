$base = "$PWD\app\src\main\java\com\example\myapplication\ui\features\auth"

$loginContent = @"
package com.example.myapplication.ui.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    onNavigateToCadastro: () -> Unit = {},
    onNavigateToEsqueciSenha: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tela de Login", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onLoginClick) {
                Text("Entrar")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onNavigateToCadastro) {
                Text("Cadastrar")
            }
            TextButton(onClick = onNavigateToEsqueciSenha) {
                Text("Esqueci a Senha")
            }
        }
    }
}
"@

$cadastroContent = @"
package com.example.myapplication.ui.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CadastroScreen(
    onBackClick: () -> Unit = {},
    onCadastroSuccess: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tela de Cadastro", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onCadastroSuccess) {
                Text("Concluir Cadastro")
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextButton(onClick = onBackClick) {
                Text("Voltar")
            }
        }
    }
}
"@

$esqueciContent = @"
package com.example.myapplication.ui.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EsqueciSenhaScreen(
    onBackClick: () -> Unit = {}
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Recuperar Senha", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBackClick) {
                Text("Voltar ao Login")
            }
        }
    }
}
"@

[IO.File]::WriteAllText("$base\LoginScreen.kt", $loginContent, [System.Text.Encoding]::UTF8)
[IO.File]::WriteAllText("$base\CadastroScreen.kt", $cadastroContent, [System.Text.Encoding]::UTF8)
[IO.File]::WriteAllText("$base\EsqueciSenhaScreen.kt", $esqueciContent, [System.Text.Encoding]::UTF8)

Write-Host "Telas de autenticacao atualizadas com sucesso!" -ForegroundColor Green