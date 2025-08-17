package com.example.equireco

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.equireco.viewmodel.ParcoursViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addParcours(navController: NavController, viewModel: ParcoursViewModel) {
    var nom by remember { mutableStateOf(viewModel.editingParcours?.nom ?: "") }
    var lieu by remember { mutableStateOf(viewModel.editingParcours?.lieu ?: "") }
    var date by remember { mutableStateOf(viewModel.editingParcours?.date ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (viewModel.editingParcours == null) "Ajouter Parcours" else "Modifier Parcours")
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            OutlinedTextField(
                value = nom,
                onValueChange = { nom = it },
                label = { Text("Nom") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = lieu,
                onValueChange = { lieu = it },
                label = { Text("Lieu") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.temporairNom = nom
                    viewModel.temporairLieu = lieu
                    viewModel.temporairDate = date

                    navController.navigate("placerObstacles")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Placer les obstacles")
            }
        }
    }
}
