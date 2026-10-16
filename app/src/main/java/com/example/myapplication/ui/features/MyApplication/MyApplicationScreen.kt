package com.example.myapplication.ui.features.MyApplication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ==========================================
// 1. MODELOS DE DADOS
// ==========================================
enum class PlayerStatus { PENDING, EVALUATED }
enum class EvaluationScreen { PLAYER_LIST, EVALUATE, XP_SUMMARY, SESSION_SUMMARY, HISTORY }

data class Player(
    val id: String,
    val name: String,
    val role: String,
    val status: PlayerStatus = PlayerStatus.PENDING,
    var xpEarned: Int = 0
)

data class SessionHistory(
    val id: String,
    val title: String,
    val date: String,
    val playersCount: Int,
    val totalXp: Int
)

data class AppUiState(
    val currentScreen: EvaluationScreen = EvaluationScreen.PLAYER_LIST,
    val sessionTitle: String = "A Cripta Perdida",
    val players: List<Player> = emptyList(),
    val currentPlayerIndex: Int = -1,
    val lastEvaluatedXp: Int = 0,
    val lastEvaluationBreakdown: Map<String, Int> = emptyMap(),
    val history: List<SessionHistory> = emptyList()
)

// ==========================================
// 2. VIEWMODEL & STATEFLOW
// ==========================================
class RpgViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update {
            it.copy(
                players = listOf(
                    Player("1", "Guilherme Santos", "Guerreiro, Guardião"),
                    Player("2", "Ana Beatriz", "Líder, Barda Elfa"),
                    Player("3", "Carlos Oliveira", "Mago, Curandeiro"),
                    Player("4", "Mariana Souza", "Ladina, Arqueira"),
                    Player("5", "Roberto Silva", "Feiticeiro, Sábio")
                ),
                history = listOf(
                    SessionHistory("h1", "A Cripta Perdida", "20 de Agosto, 2025", 5, 720),
                    SessionHistory("h2", "O Covil dos Gob.", "12 de Agosto, 2025", 5, 680),
                    SessionHistory("h3", "O Mistério de V.", "29 de Julho, 2025", 5, 550),
                    SessionHistory("h4", "Emboscada na...", "15 de Julho, 2025", 5, 610)
                )
            )
        }
    }

    fun selectPlayer(playerId: String) {
        val index = _uiState.value.players.indexOfFirst { it.id == playerId }
        if (index != -1) {
            _uiState.update { it.copy(currentPlayerIndex = index, currentScreen = EvaluationScreen.EVALUATE) }
        }
    }

    fun submitEvaluation(interp: Int, team: Int, creat: Int, part: Int) {
        val breakdown = mapOf(
            "Interpretação (Roleplay)" to interp * 10,
            "Trabalho em Equipe" to team * 10,
            "Criatividade" to creat * 10,
            "Participação" to part * 10
        )
        val totalStars = interp + team + creat + part
        val bonus = if (totalStars >= 16) 20 else 0
        val totalXp = breakdown.values.sum() + bonus

        val newBreakdown = breakdown.toMutableMap()
        if (bonus > 0) newBreakdown["Bônus de Mestre (Desempenho)"] = bonus

        _uiState.update {
            it.copy(
                lastEvaluatedXp = totalXp,
                lastEvaluationBreakdown = newBreakdown,
                currentScreen = EvaluationScreen.XP_SUMMARY
            )
        }
    }

    fun confirmXpAndNext() {
        val state = _uiState.value
        val currentPlayer = state.players[state.currentPlayerIndex]

        val updatedPlayers = state.players.toMutableList()
        updatedPlayers[state.currentPlayerIndex] = currentPlayer.copy(
            xpEarned = state.lastEvaluatedXp,
            status = PlayerStatus.EVALUATED
        )

        val nextPendingIndex = updatedPlayers.indexOfFirst { it.status == PlayerStatus.PENDING }

        if (nextPendingIndex != -1) {
            _uiState.update {
                it.copy(players = updatedPlayers, currentPlayerIndex = nextPendingIndex, currentScreen = EvaluationScreen.EVALUATE)
            }
        } else {
            _uiState.update {
                it.copy(players = updatedPlayers, currentScreen = EvaluationScreen.SESSION_SUMMARY)
            }
        }
    }

    fun finishSession() {
        val state = _uiState.value
        val totalXp = state.players.sumOf { it.xpEarned }
        val newHistory = SessionHistory(
            id = System.currentTimeMillis().toString(),
            title = state.sessionTitle,
            date = "Hoje",
            playersCount = state.players.size,
            totalXp = totalXp
        )

        _uiState.update {
            it.copy(
                history = listOf(newHistory) + it.history,
                currentScreen = EvaluationScreen.HISTORY,
                players = it.players.map { p -> p.copy(status = PlayerStatus.PENDING, xpEarned = 0) }
            )
        }
    }

    fun navigateBack() {
        val current = _uiState.value.currentScreen
        val previous = when (current) {
            EvaluationScreen.PLAYER_LIST -> EvaluationScreen.PLAYER_LIST
            EvaluationScreen.EVALUATE -> EvaluationScreen.PLAYER_LIST
            EvaluationScreen.XP_SUMMARY -> EvaluationScreen.EVALUATE
            EvaluationScreen.SESSION_SUMMARY -> EvaluationScreen.PLAYER_LIST
            EvaluationScreen.HISTORY -> EvaluationScreen.PLAYER_LIST
        }
        _uiState.update { it.copy(currentScreen = previous) }
    }
}

// ==========================================
// 3. TEMA E CORES
// ==========================================
val DarkBackground = Color(0xFF121212)
val SurfaceColor = Color(0xFF1E1E1E)
val AccentYellow = Color(0xFFFDE047)
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFFAAAAAA)

@Composable
fun RpgTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = darkColorScheme(
            background = DarkBackground,
            surface = SurfaceColor,
            primary = AccentYellow,
            onBackground = TextWhite,
            onSurface = TextWhite
        )
    ) {
        content()
    }
}

// ==========================================
// 4. COMPONENTES CUSTOMIZADOS
// ==========================================
@Composable
fun ComposeRatingBar(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        (1..5).forEach { index ->
            Text(
                text = if (index <= rating) "★" else "☆",
                color = if (index <= rating) AccentYellow else TextGray,
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable { onRatingChange(index) }
                    .padding(horizontal = 4.dp)
            )
        }
    }
}

@Composable
fun PrimaryButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = AccentYellow, contentColor = Color.Black),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

// ==========================================
// 5. TELAS (SCREENS)
// ==========================================
@Composable
fun MyApplicationScreen(viewModel: RpgViewModel = viewModel()) {
    RpgTheme {
        val state by viewModel.uiState.collectAsState()

        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            when (state.currentScreen) {
                EvaluationScreen.PLAYER_LIST -> PlayerListScreen(
                    state = state,
                    onPlayerClick = viewModel::selectPlayer,
                    onFinish = viewModel::finishSession
                )
                EvaluationScreen.EVALUATE -> EvaluatePlayerScreen(
                    state = state,
                    onBack = viewModel::navigateBack,
                    onSubmit = viewModel::submitEvaluation
                )
                EvaluationScreen.XP_SUMMARY -> XpSummaryScreen(
                    state = state,
                    onBack = viewModel::navigateBack,
                    onNext = viewModel::confirmXpAndNext
                )
                EvaluationScreen.SESSION_SUMMARY -> SessionSummaryScreen(
                    state = state,
                    onFinish = viewModel::finishSession
                )
                EvaluationScreen.HISTORY -> HistoryScreen(
                    state = state,
                    onBack = viewModel::navigateBack
                )
            }
        }
    }
}

@Composable
fun PlayerListScreen(state: AppUiState, onPlayerClick: (String) -> Unit, onFinish: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Avaliar Jogadores", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Spacer(modifier = Modifier.height(16.dp))
        Text("SESSÃO ATUAL", color = AccentYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text(state.sessionTitle, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(state.players) { player ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { if (player.status == PlayerStatus.PENDING) onPlayerClick(player.id) }
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.DarkGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(player.name.take(2).uppercase(), color = TextWhite, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(player.name, color = TextWhite, fontWeight = FontWeight.Bold)
                            Text(player.role, color = TextGray, fontSize = 12.sp)
                        }
                        val badgeColor = if (player.status == PlayerStatus.EVALUATED) Color(0xFF4CAF50) else AccentYellow
                        val badgeText = if (player.status == PlayerStatus.EVALUATED) "AVALIADO" else "PENDENTE"
                        Box(
                            modifier = Modifier
                                .border(1.dp, badgeColor, RoundedCornerShape(16.dp))
                                .padding(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text(badgeText, color = badgeColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Finalizar Sessão", onClick = onFinish)
    }
}

@Composable
fun EvaluatePlayerScreen(state: AppUiState, onBack: () -> Unit, onSubmit: (Int, Int, Int, Int) -> Unit) {
    val player = state.players.getOrElse(state.currentPlayerIndex) { state.players.first() }

    var interp by rememberSaveable { mutableIntStateOf(0) }
    var team by rememberSaveable { mutableIntStateOf(0) }
    var creat by rememberSaveable { mutableIntStateOf(0) }
    var part by rememberSaveable { mutableIntStateOf(0) }
    var comment by rememberSaveable { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Text("Avaliar Jogador", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.DarkGray), contentAlignment = Alignment.Center) {
                Text(player.name.take(2).uppercase(), color = TextWhite, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(player.name, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(player.role, color = TextGray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("CRITÉRIOS DE ATUAÇÃO", color = AccentYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        RatingCriteriaRow("Interpretação (Roleplay)", interp) { interp = it }
        RatingCriteriaRow("Trabalho em Equipe", team) { team = it }
        RatingCriteriaRow("Criatividade", creat) { creat = it }
        RatingCriteriaRow("Participação", part) { part = it }

        Spacer(modifier = Modifier.height(24.dp))
        Text("COMENTÁRIOS (OPCIONAL)", color = AccentYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            placeholder = { Text("Excelente interpretação...", color = TextGray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentYellow,
                unfocusedBorderColor = SurfaceColor,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            modifier = Modifier.fillMaxWidth().height(100.dp)
        )

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(text = "Confirmar Avaliação") {
            onSubmit(interp, team, creat, part)
        }
    }
}

@Composable
fun RatingCriteriaRow(label: String, rating: Int, onRatingChange: (Int) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = TextWhite, fontSize = 14.sp)
        ComposeRatingBar(rating = rating, onRatingChange = onRatingChange)
    }
}

@Composable
fun XpSummaryScreen(state: AppUiState, onBack: () -> Unit, onNext: () -> Unit) {
    val player = state.players.getOrElse(state.currentPlayerIndex) { state.players.first() }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Text("Resumo de XP", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.DarkGray), contentAlignment = Alignment.Center) {
                Text(player.name.take(2).uppercase(), color = TextWhite, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(player.name, color = TextWhite, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(player.role, color = TextGray, fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = SurfaceColor),
            border = BorderStroke(1.dp, AccentYellow),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(24.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("XP TOTAL ADQUIRIDO", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("+${state.lastEvaluatedXp} XP", color = AccentYellow, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                Text("Faltam 320 XP para subir de nível", color = TextGray, fontSize = 10.sp)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("DETALHAMENTO DE CÁLCULO", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        state.lastEvaluationBreakdown.forEach { (key, value) ->
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(key, color = TextWhite, fontSize = 14.sp)
                Text("+$value XP", color = AccentYellow, fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(text = "Confirmar e Próximo", onClick = onNext)
    }
}

@Composable
fun SessionSummaryScreen(state: AppUiState, onFinish: () -> Unit) {
    val rankedPlayers = state.players.sortedByDescending { it.xpEarned }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Resumo da Sessão", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        Text("Sessão concluída com sucesso", color = TextGray, fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Text("Sessão: ${state.sessionTitle}", color = TextWhite, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text("20 de Ago, 2026 • 5 Jogadores", color = TextGray, fontSize = 12.sp)

        Spacer(modifier = Modifier.height(24.dp))
        Text("RANKING DE XP CONCEDIDO", color = TextGray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(rankedPlayers) { index, player ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${index + 1}", color = TextGray, fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape).background(Color.DarkGray), contentAlignment = Alignment.Center) {
                            Text(player.name.take(2).uppercase(), color = TextWhite, fontSize = 12.sp)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(player.name, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                            Text(player.role, color = TextGray, fontSize = 10.sp)
                        }
                        Text("+${player.xpEarned} XP", color = AccentYellow, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(text = "Concluir", onClick = onFinish)
    }
}

@Composable
fun HistoryScreen(state: AppUiState, onBack: () -> Unit) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val filteredHistory = state.history.filter {
        it.title.contains(searchQuery, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextWhite)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text("Histórico de Sessões", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextWhite)
        }

        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search", tint = TextGray) },
            placeholder = { Text("Buscar sessão ou aventura...", color = TextGray) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AccentYellow,
                unfocusedBorderColor = SurfaceColor,
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text("SESSÕES ANTERIORES", color = AccentYellow, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(filteredHistory) { session ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = SurfaceColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(session.title, color = TextWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            Text("${session.date} • ${session.playersCount} Jogadores", color = TextGray, fontSize = 12.sp)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Total ${session.totalXp} XP", color = AccentYellow, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Ver", tint = TextGray)
                        }
                    }
                }
            }
        }
    }
}
