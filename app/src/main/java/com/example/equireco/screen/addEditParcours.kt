package com.example.equireco.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.equireco.data.ParcoursRepository
import com.example.equireco.model.Parcours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditParcoursScreen(
    navController: NavController,
    parcoursRepository: ParcoursRepository,
    parcoursIndex: Int? = null,
    existingParcours: Parcours? = null
) {
    var name by remember { mutableStateOf(existingParcours?.name ?: "") }
    var location by remember { mutableStateOf(existingParcours?.location ?: "") }
    var date by remember { mutableStateOf(existingParcours?.date ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(if (parcoursIndex == null || parcoursIndex == -1) "Nouveau parcours" else "Modifier parcours") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom du parcours") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Lieu") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val parcours = Parcours(
                        name = name,
                        location = location,
                        date = date,
                        obstacles = existingParcours?.obstacles?.toMutableList() ?: mutableListOf()
                    )

                    if (parcoursIndex == null || parcoursIndex == -1) {
                        parcoursRepository.addParcours(parcours)
                        val newIndex = parcoursRepository.getAllParcours().lastIndex
                        navController.navigate("addEditObstacles/$newIndex")
                    } else {
                        parcoursRepository.updateParcours(parcoursIndex, parcours)
                        navController.navigate("addEditObstacles/$parcoursIndex")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Valider et ajouter obstacles")
            }
        }
    }
}
