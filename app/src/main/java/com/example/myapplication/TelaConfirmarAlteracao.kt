package com.example.myapplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun TelaConfirmarAlteracao(
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    Dialog(onDismissRequest = onCancelar) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131A29)),
            border = BorderStroke(1.dp, Color(0xFF233047)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠",
                    color = Color(0xFFF3C644),
                    fontSize = 36.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Confirmar Alterações",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Deseja salvar as alterações no seu perfil?",
                    color = Color(0xFF8F9BB3),
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancelar,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF2A374F)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = Color(0xFF1A2336),
                            contentColor = Color(0xFF8F9BB3)
                        )
                    ) {
                        Text("Cancelar", fontSize = 13.sp)
                    }

                    Button(
                        onClick = onConfirmar,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF3C644),
                            contentColor = Color(0xFF0C101B)
                        )
                    ) {
                        Text("Sim, Salvar", fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
