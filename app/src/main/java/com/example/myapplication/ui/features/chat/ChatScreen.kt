package com.example.myapplication.ui.features.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.ThemeState

data class ItemChat(
    val nomeGrupo: String,
    val autorUltimaMensagem: String,
    val ultimaMensagem: String,
    val horario: String,
    val mensagensNaoLidas: Int
)

@Composable
fun ChatScreen() {
    var searchChat by remember { mutableStateOf("") }

    val isDark = ThemeState.isDarkTheme
    val BgDark = if (isDark) Color(0xFF0F1115) else Color(0xFFF4F6F9)
    val CardDark = if (isDark) Color(0xFF161920) else Color(0xFFFFFFFF)
    val InputDark = if (isDark) Color(0xFF1C2029) else Color(0xFFFFFFFF)
    val BorderDark = if (isDark) Color(0xFF222631) else Color(0xFFE2E6EE)
    val TextMain = if (isDark) Color.White else Color(0xFF1A1F2C)
    val TextGray = if (isDark) Color(0xFF9E9FA4) else Color(0xFF6B7280)
    val GoldYellow = Color(0xFFFFC737)

    val conversas = listOf(
        ItemChat("A Cripta Perdida (D&D 5e)", "DM Rafael", "Fichas prontas para sábado!", "10:42", 2),
        ItemChat("Mesa Tormenta20 - Arton", "Lucas", "Vou levar os dados extras.", "Ontem", 0),
        ItemChat("Ludoteca Taverna Geek", "Suporte", "Reserva presencial confirmada.", "Terça", 0),
        ItemChat("Mestre Arthur", "DM Arthur", "Ficha aprovada!", "24/mai", 0)
    )

    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Mensagens & Grupos",
                color = TextMain,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchChat,
                onValueChange = { searchChat = it },
                placeholder = { Text("Buscar...", color = TextGray) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldYellow) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputDark,
                    unfocusedContainerColor = InputDark,
                    focusedTextColor = TextMain,
                    unfocusedTextColor = TextMain,
                    focusedBorderColor = GoldYellow,
                    unfocusedBorderColor = BorderDark
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(conversas) { chat ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, BorderDark, RoundedCornerShape(14.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .background(if (isDark) Color(0xFF242A38) else Color(0xFFE8EDF5), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Group, contentDescription = null, tint = GoldYellow)
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = chat.nomeGrupo,
                                        color = TextMain,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    Text(text = chat.horario, color = TextGray, fontSize = 11.sp)
                                }

                                Spacer(modifier = Modifier.height(3.dp))

                                Text(
                                    text = "${chat.autorUltimaMensagem}: ${chat.ultimaMensagem}",
                                    color = TextGray,
                                    fontSize = 12.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
