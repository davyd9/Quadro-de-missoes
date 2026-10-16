package com.example.myapplication.ui.features.feedmain

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
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

data class MesaItem(
    val titulo: String,
    val sistema: String,
    val mestre: String,
    val vagas: String,
    val horario: String,
    val local: String
)

@Composable
fun FeedMainScreen(
    onNavigateToMesas: () -> Unit = {},
    onNavigateToAvaliacao: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {},
    onLogoutClick: () -> Unit = {}
) {
    var searchMesa by remember { mutableStateOf("") }

    val BgDark = Color(0xFF0C101A)
    val CardDark = Color(0xFF141926)
    val BorderDark = Color(0xFF1F2637)
    val TextMain = Color.White
    val TextMuted = Color(0xFF8E95A5)
    val GoldYellow = Color(0xFFFFC737)

    val mesas = listOf(
        MesaItem("A Maldição de Strahd", "D&D 5e", "DM Rafael", "2/5 vagas", "Sáb • 14:00", "Vila Mariana, SP"),
        MesaItem("O Fim de Arton", "Tormenta20", "Lucas", "1/6 vagas", "Dom • 15:00", "Pinheiros, SP"),
        MesaItem("Chamado Silencioso", "Call of Cthulhu", "DM Arthur", "Lotada", "Sex • 19:30", "Moema, SP"),
        MesaItem("Torneio Commander Casual", "Magic: The Gathering", "Taverna Geek", "Livre", "Hoje • 18:00", "Paulista, SP")
    )

    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Cabeçalho: Perfil e Botão Deslogar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable { onNavigateToPerfil() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(Color(0xFF1B3B8B), CircleShape)
                            .border(2.dp, GoldYellow, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "DM",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "Olá, Lucas",
                            color = TextMain,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = GoldYellow,
                                modifier = Modifier.size(13.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                text = "São Paulo, SP • 2km",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                // Botão Único: Deslogar
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(CardDark, RoundedCornerShape(10.dp))
                        .border(1.dp, BorderDark, RoundedCornerShape(10.dp))
                        .clickable { onLogoutClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Deslogar",
                        tint = Color(0xFFE53935),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Campo de Busca
            OutlinedTextField(
                value = searchMesa,
                onValueChange = { searchMesa = it },
                placeholder = { Text("Buscar mesas, sistemas ou mestres...", color = TextMuted, fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldYellow) },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = CardDark,
                    unfocusedContainerColor = CardDark,
                    focusedTextColor = TextMain,
                    unfocusedTextColor = TextMain,
                    focusedBorderColor = GoldYellow,
                    unfocusedBorderColor = BorderDark
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Mesas em Destaque",
                color = TextMain,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Lista de Mesas
            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(mesas) { mesa ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, BorderDark, RoundedCornerShape(14.dp))
                            .clickable { onNavigateToMesas() }
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = mesa.titulo,
                                    color = TextMain,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier.weight(1f, fill = false)
                                )
                                Text(
                                    text = mesa.vagas,
                                    color = GoldYellow,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${mesa.sistema} • Mestre: ${mesa.mestre}",
                                color = TextMuted,
                                fontSize = 12.sp
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = mesa.horario, color = TextMuted, fontSize = 11.sp)
                                Text(text = mesa.local, color = TextMuted, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
