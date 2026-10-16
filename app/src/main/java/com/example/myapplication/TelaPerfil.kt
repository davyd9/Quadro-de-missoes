package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.ui.theme.ThemeState

@Composable
fun TelaPerfil(
    onBackClick: () -> Unit = {},
    onEditarClick: () -> Unit = {}
) {
    var showConfirmModal by remember { mutableStateOf(false) }

    val isDark = ThemeState.isDarkTheme
    val BgDark = if (isDark) Color(0xFF0C101A) else Color(0xFFF4F6F9)
    val CardDark = if (isDark) Color(0xFF141926) else Color(0xFFFFFFFF)
    val BorderDark = if (isDark) Color(0xFF1F2637) else Color(0xFFE2E6EE)
    val TextMain = if (isDark) Color.White else Color(0xFF1A1F2C)
    val TextMuted = if (isDark) Color(0xFF8E95A5) else Color(0xFF6B7280)
    val TextDim = if (isDark) Color(0xFF5E6577) else Color(0xFF9CA3AF)
    val GoldYellow = Color(0xFFFFC737)

    Surface(modifier = Modifier.fillMaxSize(), color = BgDark) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFF091226),
                                    Color(0xFF1C2244),
                                    Color(0xFF10283E),
                                    BgDark
                                )
                            )
                        )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = onBackClick, modifier = Modifier.size(36.dp)) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = Color.White
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0x66000000),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x44FFFFFF)),
                            modifier = Modifier.clickable { }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.White, modifier = Modifier.size(12.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Trocar Banner", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-48).dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(contentAlignment = Alignment.BottomEnd) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .background(CardDark, CircleShape)
                                .border(3.dp, GoldYellow, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("DM", color = TextMain, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                        }

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(GoldYellow, CircleShape)
                                .border(2.dp, BgDark, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar Foto", tint = Color(0xFF0C101A), modifier = Modifier.size(14.dp))
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-36).dp)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Lucas", color = TextMain, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Place, contentDescription = null, tint = TextMuted, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "São Paulo, SP", color = TextMuted, fontSize = 13.sp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardDark),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("Mestre / DM", color = TextMuted, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("★", color = GoldYellow, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("9.4", color = TextMain, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(" / 10", color = TextMuted, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("18 avaliações", color = TextDim, fontSize = 11.sp)
                            }
                        }

                        Card(
                            colors = CardDefaults.cardColors(containerColor = CardDark),
                            shape = RoundedCornerShape(12.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text("Jogador", color = TextMuted, fontSize = 12.sp)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("★", color = GoldYellow, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("9.8", color = TextMain, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                                    Text(" / 10", color = TextMuted, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("32 avaliações", color = TextDim, fontSize = 11.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TituloSecao("| BIO", GoldYellow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(12.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = "Mestro campanhas aos fins de semana e jogo Commander casual. Busco mesas presenciais em lojas da cidade.",
                                color = TextMain,
                                fontSize = 13.sp,
                                lineHeight = 19.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(text = "62/600", color = TextDim, fontSize = 11.sp, modifier = Modifier.align(Alignment.End))
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TituloSecao("| PAPEL & NÍVEIS", GoldYellow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TagChip(label = "Mestre / DM • Avançado", isSelected = true)
                        TagChip(label = "Jogador • Intermediário", isSelected = true)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TituloSecao("| DISPONIBILIDADE & ESTILO", GoldYellow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TagChip(label = "Presencial", isSelected = true)
                            TagChip(label = "Fins de Semana", isSelected = true)
                            TagChip(label = "Casual", isSelected = true)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TagChip(label = "Online (Discord/VTT)", isSelected = false)
                            TagChip(label = "Dias de Semana", isSelected = false)
                        }
                        TagChip(label = "Competitivo", isSelected = false)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TituloSecao("| JOGOS & SISTEMAS", GoldYellow)
                    Spacer(modifier = Modifier.height(8.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TagChip(label = "D&D 5e • Avançado", isSelected = true)
                        TagChip(label = "Tormenta20 • Intermediário", isSelected = true)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TagChip(label = "Magic: The Gathering • Iniciante", isSelected = true)
                            TagChip(label = "Call of Cthulhu", isSelected = false)
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            TagChip(label = "Pathfinder 2e", isSelected = false)
                            TagChip(label = "Vampiro: A Máscara", isSelected = false)
                            TagChip(label = "Catan", isSelected = false)
                        }
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    Button(
                        onClick = { showConfirmModal = true },
                        colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color(0xFF0C101A)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Text("Editar Perfil", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(40.dp))
                }
            }

            if (showConfirmModal) {
                Dialog(onDismissRequest = { showConfirmModal = false }) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CardDark),
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                        modifier = Modifier.fillMaxWidth().padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier.size(44.dp).background(Color(0x22FFC737), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Warning, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(24.dp))
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            Text("Confirmar Alterações", color = TextMain, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text("Deseja salvar as alterações no seu perfil?", color = TextMuted, fontSize = 13.sp, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.height(22.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { showConfirmModal = false },
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMain),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, BorderDark),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).height(44.dp)
                                ) {
                                    Text("Cancelar", fontSize = 13.sp)
                                }

                                Button(
                                    onClick = {
                                        showConfirmModal = false
                                        onEditarClick()
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color(0xFF0C101A)),
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.weight(1f).height(44.dp)
                                ) {
                                    Text("Sim, Salvar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TituloSecao(titulo: String, corDestaque: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(text = titulo, color = corDestaque, fontWeight = FontWeight.Bold, fontSize = 13.sp, letterSpacing = 0.5.sp)
    }
}

@Composable
private fun TagChip(label: String, isSelected: Boolean) {
    val GoldYellow = Color(0xFFFFC737)
    val isDark = ThemeState.isDarkTheme
    val CardDark = if (isDark) Color(0xFF141926) else Color(0xFFFFFFFF)
    val BorderDark = if (isDark) Color(0xFF1F2637) else Color(0xFFE2E6EE)

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = if (isSelected) Color(0x18FFC737) else CardDark,
        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) GoldYellow else BorderDark)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isSelected) {
                Icon(Icons.Default.Check, contentDescription = null, tint = GoldYellow, modifier = Modifier.size(13.dp))
                Spacer(modifier = Modifier.width(5.dp))
            }
            Text(
                text = label,
                color = if (isSelected) GoldYellow else (if (isDark) Color(0xFF8E95A5) else Color(0xFF6B7280)),
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}
