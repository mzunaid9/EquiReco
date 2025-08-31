package com.example.equireco.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.equireco.data.ParcoursRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, parcoursRepository: ParcoursRepository) {
    // On utilise mutableStateListOf pour que la liste soit observable
    val parcoursList = remember { mutableStateListOf(*parcoursRepository.getAllParcours().toTypedArray()) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Liste des parcours") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addEditParcours/-1") }) {
                Text("+")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            itemsIndexed(parcoursList) { index, parcours ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("addEditObstacles/$index") },
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nom : ${parcours.name}", style = MaterialTheme.typography.titleMedium)
                        Text(text = "Lieu : ${parcours.location}")
                        Text(text = "Date : ${parcours.date}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { navController.navigate("addEditParcours/$index") }) {
                                Text("Modifier")
                            }
                            Button(onClick = {
                                parcoursRepository.deleteParcours(index)
                                parcoursList.removeAt(index) // suppression immédiate de la liste
                            }) {
                                Text("Supprimer")
                            }
                        }
                    }
                }
            }
        }
    }
}
