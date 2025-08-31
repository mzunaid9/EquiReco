package com.example.equireco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

class MainActivity : ComponentActivity() {
    private lateinit var repository: ParcoursRepository

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ ici on passe bien le context
        repository = ParcoursRepository(applicationContext)

        setContent {
            EquiRecoTheme {
                val navController = rememberNavController()
                Scaffold { padding ->
                    AppNavHost(navController, repository)
                }
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, repository: ParcoursRepository) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, repository)
        }
        composable("addEditParcours/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: -1
            val parcours = if (index >= 0) repository.getParcours(index) else null
            AddEditParcoursScreen(navController, repository, index, parcours)
        }
        composable("addEditObstacles/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: -1
            val parcours = repository.getParcours(index)
            if (parcours != null) {
                AddEditObstaclesScreen(navController, repository, index, parcours)
            } else {
                Text("Parcours introuvable")
            }
        }
    }
}
