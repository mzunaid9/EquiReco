package com.example.equireco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.equireco.data.ParcoursRepository
import com.example.equireco.model.Parcours
import com.example.equireco.screen.AddEditObstaclesScreen
import com.example.equireco.screen.AddEditParcoursScreen
import com.example.equireco.screen.HomeScreen
import com.example.equireco.ui.theme.EquiRecoTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private lateinit var parcoursRepository: ParcoursRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parcoursRepository = ParcoursRepository(this)

        setContent {
            EquiRecoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    var showSplash by remember { mutableStateOf(true) }

                    // Affiche le splash pendant 2 secondes
                    if (showSplash) {
                        LaunchedEffect(Unit) {
                            delay(2000)
                            showSplash = false
                        }

                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "Logo",
                                modifier = Modifier.size(200.dp)
                            )
                        }

                    } else {
                        // App normale
                        val navController: NavHostController = rememberNavController()
                        NavHost(navController = navController, startDestination = "home") {
                            composable("home") {
                                HomeScreen(navController, parcoursRepository)
                            }
                            composable("addEditParcours/{index}") { backStack ->
                                val index = backStack.arguments?.getString("index")?.toIntOrNull()
                                val parcours: Parcours? =
                                    index?.takeIf { it >= 0 }?.let { parcoursRepository.getParcours(it) }
                                AddEditParcoursScreen(
                                    navController = navController,
                                    parcoursRepository = parcoursRepository,
                                    parcoursIndex = index,
                                    existingParcours = parcours
                                )
                            }
                            composable("addEditObstacles/{index}") { backStack ->
                                val index = backStack.arguments?.getString("index")?.toIntOrNull()
                                val parcours = index?.let { parcoursRepository.getParcours(it) }
                                if (index != null && parcours != null) {
                                    AddEditObstaclesScreen(
                                        navController = navController,
                                        parcoursRepository = parcoursRepository,
                                        parcoursIndex = index,
                                        parcours = parcours
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
