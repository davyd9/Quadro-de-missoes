package com.example.myapplication.ui.features.mapa

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MesaProxima(
    val titulo: String,
    val sistema: String,
    val local: String,
    val distancia: String,
    val vagas: String,
    val horario: String
)

@Composable
fun MapScreen() {
    var searchLocation by remember { mutableStateOf("") }
    val sistemas = listOf("Todos", "D&D 5e", "Tormenta20", "Ordem Paranormal", "Call of Cthulhu", "Vampiro")
    var selectedSistema by remember { mutableStateOf("Todos") }

    val mesas = listOf(
        MesaProxima("A Cripta Perdida", "Dungeons & Dragons 5e", "Ludoteca Taverna Geek", "1.2 km", "3/5 vagas", "Sáb, 15:00"),
        MesaProxima("O Segredo na Floresta", "Tormenta20", "Shopping Geek Hall", "2.8 km", "4/6 vagas", "Dom, 14:00"),
        MesaProxima("O Chamado das Sombras", "Ordem Paranormal", "Ponto de Encontro Central", "3.5 km", "1/4 vagas", "Sex, 19:30")
    )

    val BgDark = Color(0xFF0F1115)
    val CardDark = Color(0xFF161920)
    val InputDark = Color(0xFF1C2029)
    val GoldYellow = Color(0xFFFFD152)
    val TextGray = Color(0xFF9E9FA4)

    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Encontre Mesas Perto de Você",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Explore ludotecas e grupos presenciais na sua região",
                color = TextGray,
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchLocation,
                onValueChange = { searchLocation = it },
                placeholder = { Text("Buscar por ludoteca, bairro ou mestre...", color = TextGray, fontSize = 14.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = GoldYellow) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = InputDark, unfocusedContainerColor = InputDark,
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White,
                    focusedBorderColor = GoldYellow, unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(sistemas) { sistema ->
                    val isSelected = sistema == selectedSistema
                    FilterChip(
                        selected = isSelected,
                        onClick = { selectedSistema = sistema },
                        label = { Text(sistema, fontSize = 12.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = GoldYellow,
                            selectedLabelColor = Color.Black,
                            containerColor = CardDark,
                            labelColor = TextGray
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) GoldYellow else Color(0xFF282D37)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .background(CardDark, RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFF232834), RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Navigation, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(32.dp))
                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Radar de Proximidade Ativo", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("3 locais parceiros encontrados em um raio de 5 km", color = TextGray, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Mesas com Vagas Abertas", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                items(mesas) { mesa ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFF222631), RoundedCornerShape(14.dp))
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(mesa.titulo, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                Surface(
                                    color = Color(0xFF252A36),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        mesa.distancia,
                                        color = GoldYellow,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }
                            Text(mesa.sistema, color = GoldYellow, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                            Spacer(modifier = Modifier.height(6.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Place, contentDescription = null, tint = TextGray, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(mesa.local, color = TextGray, fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(12.dp))
                                Text("• " + mesa.horario, color = TextGray, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
