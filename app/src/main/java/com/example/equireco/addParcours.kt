package com.example.equireco

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.equireco.viewmodel.ParcoursViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun addParcours(navController: NavController, viewModel: ParcoursViewModel) {
    var nom by remember { mutableStateOf(viewModel.editingParcours?.nom ?: "") }
    var lieu by remember { mutableStateOf(viewModel.editingParcours?.lieu ?: "") }
    var date by remember { mutableStateOf(viewModel.editingParcours?.date ?: "") }

    // Liste des points dessinés
    var points by remember { mutableStateOf(listOf<Offset>()) }

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

            Text("Desssine Parcours :", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Dessin Parcou rs
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.LightGray)
                    .pointerInput(Unit) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            points = points + change.position
                        }
                    }
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    points.forEach { point ->
                        drawCircle(
                            color = Color.Black,
                            radius = 5f,
                            center = point
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (viewModel.editingParcours == null) {
                        viewModel.addParcours(nom, lieu, date)
                    } else {
                        viewModel.updateParcours(nom, lieu, date)
                    }
                    viewModel.clearEditingParcours()
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enregistrer")
            }
        }
    }
}
