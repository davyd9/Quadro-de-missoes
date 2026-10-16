package com.example.myapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.features.Dados.DadosScreen
import com.example.myapplication.ui.features.MyApplication.MyApplicationScreen
import com.example.myapplication.ui.features.feedmain.FeedMainScreen
import com.example.myapplication.ui.features.auth.LoginScreen
import com.example.myapplication.ui.features.auth.CadastroScreen
import com.example.myapplication.ui.features.auth.EsqueciSenhaScreen
import com.example.myapplication.ui.features.chat.ChatScreen
import com.example.myapplication.ui.features.mapa.MapScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
sealed class AppDestination(val route: String, val label: String, val icon: ImageVector, val badgeCount: Int = 0) {
    object Login : AppDestination("login", "Login", Icons.Default.Person)
    object Cadastro : AppDestination("cadastro", "Cadastro", Icons.Default.Person)
    object EsqueciSenha : AppDestination("esqueci_senha", "Esqueci", Icons.Default.Person)
    object Feed : AppDestination("feed", "Descobertas", Icons.Default.Explore)
    object Dados : AppDestination("dados", "Mesas", Icons.Default.GridView)
    object Mapa : AppDestination("mapa", "Mapa", Icons.Default.Place)
    object Chat : AppDestination("chat", "Chat", Icons.Default.Email, badgeCount = 2)
    object Avaliacao : AppDestination("avaliacao", "Sessoes & XP", Icons.Default.Star)
    object Perfil : AppDestination("perfil", "Perfil", Icons.Default.Person)
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyApplicationTheme { AppMainNavigation() } }
    }
}
@Composable
fun AppMainNavigation() {
    val navController = rememberNavController()
    val destinations = listOf(AppDestination.Feed, AppDestination.Dados, AppDestination.Mapa, AppDestination.Chat, AppDestination.Avaliacao, AppDestination.Perfil)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in listOf("feed", "dados", "mapa", "chat", "avaliacao", "perfil")
    Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color(0xFF0F172A), bottomBar = { if (showBottomBar) { CentralBottomNavigationBar(navController = navController, destinations = destinations) } }) { innerPadding ->
        NavHost(navController = navController, startDestination = AppDestination.Login.route, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            composable(AppDestination.Login.route) { LoginScreen(onLoginClick = { navController.navigate(AppDestination.Feed.route) { popUpTo(AppDestination.Login.route) { inclusive = true } } }, onNavigateToCadastro = { navController.navigate(AppDestination.Cadastro.route) }, onNavigateToEsqueciSenha = { navController.navigate(AppDestination.EsqueciSenha.route) }) }
            composable(AppDestination.Cadastro.route) { CadastroScreen(onBackClick = { navController.popBackStack() }, onCadastroSuccess = { navController.navigate(AppDestination.Feed.route) { popUpTo(0) } }) }
            composable(AppDestination.EsqueciSenha.route) { EsqueciSenhaScreen(onBackClick = { navController.popBackStack() }) }
            composable(AppDestination.Feed.route) { FeedMainScreen(onNavigateToMesas = { navigateSingleTop(navController, AppDestination.Dados.route) }, onNavigateToAvaliacao = { navigateSingleTop(navController, AppDestination.Avaliacao.route) }, onNavigateToPerfil = { navigateSingleTop(navController, AppDestination.Perfil.route) }) }
            composable(AppDestination.Dados.route) { DadosScreen() }
            composable(AppDestination.Mapa.route) { MapScreen() }
            composable(AppDestination.Chat.route) { ChatScreen() }
            composable(AppDestination.Avaliacao.route) { MyApplicationScreen() }
            composable(AppDestination.Perfil.route) { TelaPerfil(onBackClick = { if (!navController.popBackStack()) navigateSingleTop(navController, AppDestination.Feed.route) }) }
        }
    }
}
private fun navigateSingleTop(navController: NavHostController, route: String) {
    navController.navigate(route) { popUpTo(navController.graph.findStartDestination().id) { saveState = true }; launchSingleTop = true; restoreState = true }
}
@Composable
fun CentralBottomNavigationBar(navController: NavHostController, destinations: List<AppDestination>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(containerColor = Color(0xFF0F172A), tonalElevation = 0.dp) {
        destinations.forEach { destination ->
            val selected = currentRoute == destination.route
            NavigationBarItem(icon = { if (destination.badgeCount > 0) { BadgedBox(badge = { Badge(containerColor = Color(0xFFEF4444)) { Text(destination.badgeCount.toString()) } }) { Icon(destination.icon, contentDescription = destination.label) } } else { Icon(destination.icon, contentDescription = destination.label) } }, label = { Text(destination.label, fontSize = 9.sp) }, selected = selected, onClick = { navigateSingleTop(navController, destination.route) }, colors = NavigationBarItemDefaults.colors(selectedIconColor = Color(0xFFFACC15), selectedTextColor = Color(0xFFFACC15), unselectedIconColor = Color(0xFF94A3B8), unselectedTextColor = Color(0xFF94A3B8), indicatorColor = Color.Transparent))
        }
    }
}