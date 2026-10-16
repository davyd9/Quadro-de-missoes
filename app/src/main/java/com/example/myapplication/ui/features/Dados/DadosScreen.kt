package com.example.myapplication.ui.features.Dados

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// --- CORES DO DESIGN ---
val BgDark = Color(0xFF0F1115)
val CardDark = Color(0xFF161920)
val InputDark = Color(0xFF1C2029)
val GoldYellow = Color(0xFFFFD152)
val GoldYellowDisabled = Color(0x66FFD152)
val TextGray = Color(0xFF9E9FA4)
val SubtitleYellow = Color(0xFFE5C058)
val BorderDark = Color(0xFF282C37)
val ErrorRed = Color(0xFFE54D4D)

// --- MODELOS DE DADOS ---
data class TableItem(
    val id: String,
    val title: String,
    val system: String,
    val isPresencial: Boolean = true,
    val currentPlayers: Int,
    val maxPlayers: Int,
    val dmName: String,
    val dmNick: String = "",
    val schedule: String,
    val location: String,
    val description: String
)

sealed class DadosSubScreen {
    object Board : DadosSubScreen()
    data class Details(val table: TableItem) : DadosSubScreen()
    object Create : DadosSubScreen()
}

@Composable
fun DadosScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = BgDark
    ) {
        AppRPGNavigation()
    }
}

@Composable
fun AppRPGNavigation() {
    var currentScreen by remember { mutableStateOf<DadosSubScreen>(DadosSubScreen.Board) }
    val tablesList = remember {
        mutableStateListOf(
            TableItem(
                id = "D&D#5e01",
                title = "A Mina Perdida de Phandelver",
                system = "D&D 5e",
                currentPlayers = 3,
                maxPlayers = 5,
                dmName = "Arthur",
                dmNick = "Mestre Arthur",
                schedule = "Sábados às 15:00",
                location = "Ludoteca Central - Mesa 02",
                description = "Campanha oficial introdutória para novos jogadores e veteranos! Vamos explorar as misteriosas minas próximas a Neverwinter. Níveis de personagem recomendados: do 1 ao 5. Todo o material básico (fichas, dados e miniaturas) será disponibilizado pelo mestre na mesa de jogo."
            ),
            TableItem(
                id = "CoC#7e03",
                title = "O Segredo nas Sombras",
                system = "Call of Cthulhu",
                currentPlayers = 4,
                maxPlayers = 4,
                dmName = "Bia",
                dmNick = "Mestre Bia",
                schedule = "Sextas 19:30",
                location = "Ludoteca Central",
                description = "Uma investigação sombria sobre acontecimentos paranormais nos arredores de Arkham. Tragam mentes preparadas e muita cautela."
            )
        )
    }

    when (val screen = currentScreen) {
        is DadosSubScreen.Board -> {
            TableBoardScreen(
                tables = tablesList,
                onTableClick = { currentScreen = DadosSubScreen.Details(it) },
                onAddClick = { currentScreen = DadosSubScreen.Create }
            )
        }
        is DadosSubScreen.Details -> {
            TableDetailsScreen(
                table = screen.table,
                onBack = { currentScreen = DadosSubScreen.Board }
            )
        }
        is DadosSubScreen.Create -> {
            CreateTableScreen(
                onBack = { currentScreen = DadosSubScreen.Board },
                onCreate = { newTable ->
                    tablesList.add(0, newTable)
                    currentScreen = DadosSubScreen.Board
                }
            )
        }
    }
}

// --- TELA 1: QUADRO DE MESAS ---
@Composable
fun TableBoardScreen(
    tables: List<TableItem>,
    onTableClick: (TableItem) -> Unit,
    onAddClick: () -> Unit
) {
    var searchTitle by remember { mutableStateOf("") }
    var searchCode by remember { mutableStateOf("") }
    var selectedTag by remember { mutableStateOf("Todos") }
    val tags = listOf("Todos", "D&D 5e", "Tormenta 20", "Call of Cthulhu", "Outros", "Apenas com Vagas")

    val filteredTables = tables.filter { item ->
        val matchesTitle = item.title.contains(searchTitle, ignoreCase = true)
        val matchesCode = item.id.contains(searchCode, ignoreCase = true)
        val matchesTag = when (selectedTag) {
            "Todos" -> true
            "Apenas com Vagas" -> item.currentPlayers < item.maxPlayers
            else -> item.system.equals(selectedTag, ignoreCase = true)
        }
        matchesTitle && matchesCode && matchesTag
    }

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quadro de Mesas",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .border(1.dp, SubtitleYellow.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("PRESENCIAL", color = SubtitleYellow, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(CardDark)
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Search, contentDescription = null, tint = SubtitleYellow, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("BUSCAR MESAS", color = TextGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    DarkTextField(value = searchTitle, onValueChange = { searchTitle = it }, placeholder = "Pesquisar por Título...")
                    DarkTextField(value = searchCode, onValueChange = { searchCode = it }, placeholder = "Pesquisar por Código (ex: D&D#5e01)")
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    tags.forEach { tag ->
                        val isSelected = tag == selectedTag
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isSelected) GoldYellow else InputDark)
                                .clickable { selectedTag = tag }
                                .padding(horizontal = 14.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = tag,
                                color = if (isSelected) Color.Black else TextGray,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            items(filteredTables) { table ->
                TableCard(table = table, onClick = { onTableClick(table) })
            }
        }

        FloatingActionButton(
            onClick = onAddClick,
            containerColor = GoldYellow,
            contentColor = Color.Black,
            shape = CircleShape,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Nova Mesa", modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
fun TableCard(table: TableItem, onClick: () -> Unit) {
    val isFull = table.currentPlayers >= table.maxPlayers
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(CardDark)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = table.title,
                color = Color.White,
                fontSize = 17.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(InputDark)
                    .border(0.5.dp, if (isFull) TextGray else SubtitleYellow, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "${table.currentPlayers}/${table.maxPlayers} Vagas",
                    color = if (isFull) TextGray else SubtitleYellow,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Text(
            text = "${table.system} • ${if (table.isPresencial) "Presencial" else "Online"} Código: ${table.id}",
            color = SubtitleYellow,
            fontSize = 12.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            InfoRowIcon(icon = Icons.Default.Person, text = if (table.dmNick.isNotBlank()) table.dmNick else "Mestre ${table.dmName}")
            InfoRowIcon(icon = Icons.Default.DateRange, text = table.schedule)
            InfoRowIcon(icon = Icons.Default.Place, text = table.location)
        }

        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth().height(46.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isFull) InputDark else GoldYellow,
                contentColor = if (isFull) TextGray else Color.Black
            )
        ) {
            Text(
                text = if (isFull) "Mesa Lotada" else "Solicitar Entrada",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

// --- TELA 2: DETALHES DA MESA ---
@Composable
fun TableDetailsScreen(
    table: TableItem,
    onBack: () -> Unit
) {
    val isFull = table.currentPlayers >= table.maxPlayers

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            }
            Text(
                text = "Detalhes da Mesa",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(CardDark)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = table.title,
                color = Color.White,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${table.system} • Presencial   Código: ${table.id}",
                color = SubtitleYellow,
                fontSize = 13.sp
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Jogadores Inscritos", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                    Text("${table.currentPlayers}/${table.maxPlayers} Jogadores", color = SubtitleYellow, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                }

                LinearProgressIndicator(
                    progress = { table.currentPlayers.toFloat() / table.maxPlayers.toFloat() },
                    modifier = Modifier.fillMaxWidth().height(6.dp).clip(RoundedCornerShape(3.dp)),
                    color = GoldYellow,
                    trackColor = InputDark
                )
            }

            HorizontalDivider(color = BorderDark, thickness = 1.dp)

            DetailItem(icon = Icons.Default.Person, label = "ORGANIZADOR / MESTRE", value = if (table.dmNick.isNotBlank()) table.dmNick else "Mestre ${table.dmName}")
            DetailItem(icon = Icons.Default.DateRange, label = "HORÁRIO DA SESSÃO", value = table.schedule)
            DetailItem(icon = Icons.Default.Place, label = "LOCAL", value = table.location)

            HorizontalDivider(color = BorderDark, thickness = 1.dp)

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text("SOBRE A MESA", color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = table.description,
                    color = Color.White,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                enabled = !isFull,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldYellow,
                    contentColor = Color.Black,
                    disabledContainerColor = InputDark,
                    disabledContentColor = TextGray
                )
            ) {
                Text(
                    text = if (isFull) "Mesa Lotada" else "Solicitar Entrada",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// --- TELA 3: CRIAR NOVA MESA ---
@Composable
fun CreateTableScreen(
    onBack: () -> Unit,
    onCreate: (TableItem) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var system by remember { mutableStateOf("") }
    var dmName by remember { mutableStateOf("") }
    var vacancies by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
            }
            Text(
                text = "Criar Nova Mesa",
                color = Color.White,
                fontSize = 20.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(14.dp))
                .background(CardDark)
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormLabel(text = "TÍTULO DA CAMPANHA *")
                DarkTextField(
                    value = title,
                    onValueChange = { title = it },
                    placeholder = "Ex: A Mina Perdida de Phandelver"
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormLabel(text = "CÓDIGO ÚNICO (OPCIONAL)")
                DarkTextField(
                    value = code,
                    onValueChange = { code = it },
                    placeholder = "Ex: D&D#5e01"
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormLabel(text = "SISTEMA DE RPG *")
                DarkTextField(
                    value = system,
                    onValueChange = { system = it },
                    placeholder = "Ex: D&D 5e, Tormenta 20, Call of Cthulhu"
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    FormLabel(text = "MESTRE / ORGANIZADOR *")
                    DarkTextField(
                        value = dmName,
                        onValueChange = { dmName = it },
                        placeholder = "Seu Nome/Nick"
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    FormLabel(text = "TOTAL DE VAGAS *")
                    DarkTextField(
                        value = vacancies,
                        onValueChange = { vacancies = it },
                        placeholder = "Ex: 5"
                    )
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormLabel(text = "LOCALIZAÇÃO DA MESA")
                DarkTextField(
                    value = location,
                    onValueChange = { location = it },
                    placeholder = "Ex: Ludoteca Central - Mesa 02"
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                FormLabel(text = "DESCRIÇÃO DA MESA")
                DarkTextField(
                    value = description,
                    onValueChange = { description = it },
                    placeholder = "Escreva detalhes sobre a campanha, nível de jogadores, regras adicionais ou o que esperar...",
                    singleLine = false,
                    modifier = Modifier.height(110.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                onCreate(
                    TableItem(
                        id = code.ifBlank { "MESA#${(1000..9999).random()}" },
                        title = title.ifBlank { "Nova Campanha" },
                        system = system.ifBlank { "RPG" },
                        dmName = dmName.ifBlank { "Mestre" },
                        schedule = "Sábados às 15:00",
                        location = location.ifBlank { "Ludoteca" },
                        description = description.ifBlank { "Campanha criada pela comunidade." },
                        currentPlayers = 1,
                        maxPlayers = vacancies.toIntOrNull() ?: 5
                    )
                )
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GoldYellow, contentColor = Color.Black)
        ) {
            Text("Publicar Mesa", fontWeight = FontWeight.Bold, fontSize = 15.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar", color = TextGray, fontSize = 14.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

// --- COMPONENTES AUXILIARES REUTILIZÁVEIS ---
@Composable
fun DarkTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = TextGray.copy(alpha = 0.6f), fontSize = 13.sp) },
        singleLine = singleLine,
        isError = isError,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = InputDark,
            unfocusedContainerColor = InputDark,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorContainerColor = InputDark,
            errorIndicatorColor = ErrorRed
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun InfoRowIcon(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = TextGray, modifier = Modifier.size(15.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = TextGray, fontSize = 13.sp)
    }
}

@Composable
fun DetailItem(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(InputDark),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = SubtitleYellow, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(label, color = TextGray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun FormLabel(text: String) {
    Text(text, color = TextGray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
}
