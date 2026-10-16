package com.example.myapplication
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@Composable
fun TelaPerfil(onBackClick: () -> Unit) {
    var isEditing by remember { mutableStateOf(false) }
    var nome by remember { mutableStateOf("Davyd Molina") }
    var bio by remember { mutableStateOf("Mestre e jogador de D&D 5e e Tormenta20 em Curitiba. Foco em campanhas narrativas e encontros presenciais.") }
    val BgDark = Color(0xFF0F1115); val CardDark = Color(0xFF1C2029); val GoldYellow = Color(0xFFFFD152); val TextGray = Color(0xFF9E9FA4)
    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBackClick) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White) }
                Text("Meu Perfil", color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                IconButton(onClick = { isEditing = !isEditing }) { Icon(Icons.Default.Edit, contentDescription = "Editar", tint = GoldYellow) }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Box(modifier = Modifier.size(90.dp).background(CardDark, CircleShape), contentAlignment = Alignment.Center) { Icon(Icons.Default.Person, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(50.dp)) }
                Spacer(modifier = Modifier.height(12.dp))
                if (isEditing) {
                    OutlinedTextField(value = nome, onValueChange = { nome = it }, label = { Text("Nome", color = GoldYellow) }, colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedContainerColor = CardDark, unfocusedContainerColor = CardDark), modifier = Modifier.fillMaxWidth())
                } else {
                    Text(nome, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Text("Nivel 5 · Mestre Veterano", color = GoldYellow, fontSize = 14.sp)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Surface(color = CardDark, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BIOGRAFIA", color = GoldYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(6.dp))
                    if (isEditing) {
                        OutlinedTextField(value = bio, onValueChange = { bio = it }, modifier = Modifier.fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(focusedTextColor = Color.White, unfocusedTextColor = Color.White, focusedContainerColor = BgDark, unfocusedContainerColor = BgDark))
                    } else {
                        Text(bio, color = TextGray, fontSize = 14.sp, lineHeight = 20.sp)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Surface(color = CardDark, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("REPUTACAO E METRICAS", color = GoldYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("18", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Partidas", color = TextGray, fontSize = 12.sp) }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(Icons.Default.Star, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(16.dp)); Text("4.9", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp) }; Text("Avaliacao", color = TextGray, fontSize = 12.sp) }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) { Text("100%", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp); Text("Presenca", color = TextGray, fontSize = 12.sp) }
                    }
                }
            }
            if (isEditing) {
                Spacer(modifier = Modifier.height(20.dp))
                Button(onClick = { isEditing = false }, colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color.Black), modifier = Modifier.fillMaxWidth().height(48.dp), shape = RoundedCornerShape(12.dp)) { Text("Salvar Alteracoes", fontWeight = FontWeight.Bold) }
            }
        }
    }
}