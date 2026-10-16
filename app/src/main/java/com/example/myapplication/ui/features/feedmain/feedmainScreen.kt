package com.example.myapplication.ui.features.feedmain

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DarkBackground = Color(0xFF0F172A)
val CardBackground = Color(0xFF1E293B)
val AccentYellow = Color(0xFFFACC15)
val TextGray = Color(0xFF94A3B8)
val TagBackground = Color(0xFF334155)

@Composable
fun FeedMainScreen(
    onNavigateToMesas: () -> Unit = {},
    onNavigateToAvaliacao: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = DarkBackground
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            TopHeader(
                onProfileClick = onNavigateToPerfil,
                onFilterClick = onNavigateToMesas
            )
            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier.weight(1f)) {
                GameCard(onPlayClick = onNavigateToMesas)
            }

            Spacer(modifier = Modifier.height(16.dp))
            ActionButtons(
                onFavoriteClick = onNavigateToAvaliacao,
                onConfirmClick = onNavigateToMesas
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun MainScreen(
    onNavigateToMesas: () -> Unit = {},
    onNavigateToAvaliacao: () -> Unit = {},
    onNavigateToPerfil: () -> Unit = {}
) {
    FeedMainScreen(
        onNavigateToMesas = onNavigateToMesas,
        onNavigateToAvaliacao = onNavigateToAvaliacao,
        onNavigateToPerfil = onNavigateToPerfil
    )
}

@Composable
fun TopHeader(
    onProfileClick: () -> Unit = {},
    onFilterClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { onProfileClick() }
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E3A8A))
                    .border(1.5.dp, AccentYellow, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("DM", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Olá, Lucas",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = AccentYellow,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "São Paulo, SP - 2km", color = TextGray, fontSize = 12.sp)
                }
            }
        }
        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.border(1.dp, TextGray.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
        ) {
            Icon(Icons.Default.Tune, contentDescription = "Filtro", tint = Color.White)
        }
    }
}

@Composable
fun GameCard(onPlayClick: () -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.cardColors(containerColor = CardBackground)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.7f),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.9f)
                            )
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = { },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.5f)),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("PULAR", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onPlayClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AccentYellow.copy(alpha = 0.8f),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(50)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("JOGAR", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Mesa do Rafael", color = Color.White, fontSize = 14.sp)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = AccentYellow,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("4.8", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Dungeons & Dragons\n5e",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 32.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        TagChip("RPG", isHighlighted = true)
                        TagChip("Presencial")
                        TagChip("Iniciantes Bem-vindos")
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        DetailItem(Icons.Default.Group, "3/5 jogadores")
                        DetailItem(Icons.Default.LocationOn, "1.2 km")
                        DetailItem(Icons.Default.AccessTime, "Sáb, 30 Ago - 19h")
                    }
                }
            }
        }
    }
}

@Composable
fun TagChip(text: String, isHighlighted: Boolean = false) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isHighlighted) AccentYellow else TagBackground)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = text,
            color = if (isHighlighted) Color.Black else Color.White,
            fontSize = 12.sp,
            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun DetailItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = AccentYellow, modifier = Modifier.size(16.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text, color = Color.White, fontSize = 12.sp)
    }
}

@Composable
fun ActionButtons(
    onFavoriteClick: () -> Unit = {},
    onConfirmClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { },
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, TextGray.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = TextGray)
        }

        IconButton(
            onClick = { },
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, TextGray.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Fechar", tint = TextGray)
        }

        IconButton(
            onClick = onFavoriteClick,
            modifier = Modifier
                .size(48.dp)
                .border(1.dp, TextGray.copy(alpha = 0.3f), CircleShape)
        ) {
            Icon(Icons.Default.StarBorder, contentDescription = "Avaliar", tint = TextGray)
        }

        IconButton(
            onClick = onConfirmClick,
            modifier = Modifier
                .size(64.dp)
                .background(AccentYellow, CircleShape)
        ) {
            Icon(
                Icons.Default.Check,
                contentDescription = "Confirmar",
                tint = Color.Black,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F172A)
@Composable
fun MainScreenPreview() {
    MaterialTheme(colorScheme = darkColorScheme(background = DarkBackground)) {
        FeedMainScreen()
    }
}
