package com.example.equireco

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.equireco.data.ParcoursDatabase
import com.example.equireco.data.ParcoursRepository
import com.example.equireco.viewmodel.ParcoursViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dao = ParcoursDatabase.getDatabase(applicationContext).parcoursDao()
        val repository = ParcoursRepository(dao)

        setContent {
            val navController = rememberNavController()
            val viewModel: ParcoursViewModel = viewModel(factory = ParcoursViewModel.Factory(repository))

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController, startDestination = "main") {
                    composable("main") { Main(navController) }
                    composable("listeParcours") { listeParcours(navController, viewModel) }
                    composable("addParcours") { addParcours(navController, viewModel) }
                }
            }
        }
    }
}
