package com.example.myapplication.ui.features.auth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit,
    onNavigateToCadastro: () -> Unit,
    onNavigateToEsqueciSenha: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showTermsDialog by remember { mutableStateOf(false) }
    var acceptedTerms by remember { mutableStateOf(false) }

    val BgDark = Color(0xFF0F1115)
    val InputDark = Color(0xFF1C2029)
    val GoldYellow = Color(0xFFFFD152)
    val TextGray = Color(0xFF9E9FA4)

    if (showTermsDialog) {
        AlertDialog(
            onDismissRequest = { showTermsDialog = false },
            icon = { Icon(Icons.Default.Security, contentDescription = null, tint = GoldYellow) },
            title = {
                Text(
                    text = "Termos de Uso e Privacidade (LGPD)",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 280.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Documentação de Segurança - Quadro de Missões v1.2:\n\n" +
                               "1. Tratamento e Minimização de Dados (LGPD):\n" +
                               "Coletamos exclusivamente e-mail, nome/apelido e dados de preferências para viabilizar o agendamento de partidas presenciais. Não armazenamos endereços residenciais completos.\n\n" +
                               "2. Isenção de Responsabilidade em Encontros Presenciais:\n" +
                               "A plataforma atua apenas na aproximação de jogadores. Não realizamos checagem de antecedentes criminais. A conduta nas partidas é de total responsabilidade dos participantes.\n\n" +
                               "3. Diretrizes de Segurança e Convivência:\n" +
                               "Priorize locais públicos e ludotecas credenciadas. Não compartilhe dados financeiros ou sensíveis no chat. Casos de assédio, ameaça ou descumprimento estão sujeitos a bloqueio imediato.",
                        color = TextGray,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        acceptedTerms = true
                        showTermsDialog = false
                        onLoginClick()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color.Black)
                ) {
                    Text("Aceito e Concordo", fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showTermsDialog = false }) {
                    Text("Recusar", color = TextGray)
                }
            },
            containerColor = Color(0xFF161920),
            shape = RoundedCornerShape(16.dp)
        )
    }

    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Quadro de Missões", color = GoldYellow, fontSize = 32.sp, fontWeight = FontWeight.Bold)
            Text("Conectando grupos presenciais de RPG", color = TextGray, fontSize = 14.sp)

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("E-mail", color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = TextGray) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputDark, unfocusedContainerColor = InputDark,
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldYellow, unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Senha", color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = TextGray) },
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputDark, unfocusedContainerColor = InputDark,
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldYellow, unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onNavigateToEsqueciSenha) {
                    Text("Esqueci minha senha", color = GoldYellow, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (!acceptedTerms) {
                        showTermsDialog = true
                    } else {
                        onLoginClick()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Entrar", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = { showTermsDialog = true }) {
                Text("Ler Termos de Uso e LGPD", color = TextGray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Ainda não tem conta? ", color = TextGray)
                TextButton(onClick = onNavigateToCadastro) {
                    Text("Criar agora", color = GoldYellow, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
