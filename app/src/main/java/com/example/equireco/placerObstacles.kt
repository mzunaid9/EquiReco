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
fun placerObstacles(navController: NavController, viewModel: ParcoursViewModel) {
    var obstacles by remember { mutableStateOf(listOf<Pair<String, Offset>>()) }
    var selectedObstacleType by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Placer les Obstacles") }
            )
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(onClick = { selectedObstacleType = "BARRE_SIMPLE" }) {
                        Text("|")
                    }
                    Button(onClick = { selectedObstacleType = "BARRE_DOUBLE" }) {
                        Text("||")
                    }
                    Button(
                        onClick = {
                            viewModel.addParcours(
                                viewModel.temporairNom,
                                viewModel.temporairLieu,
                                viewModel.temporairDate
                            )
                            navController.popBackStack(Routes.listeParcours, false)
                        }
                    ) {
                        Text("Valider")
                    }
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color.LightGray)
                .pointerInput(selectedObstacleType) {
                    detectDragGestures { change, dragAmount ->
                        if (selectedObstacleType != null) {
                            obstacles = obstacles + Pair(
                                selectedObstacleType!!,
                                change.position
                            )
                            selectedObstacleType = null
                        }
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                obstacles.forEach { (type, position) ->
                    if (type == "BARRE_SIMPLE") {
                        drawRect(
                            color = Color.Red,
                            topLeft = position,
                            size = androidx.compose.ui.geometry.Size(100f, 20f)
                        )
                    } else if (type == "BARRE_DOUBLE") {
                        drawRect(
                            color = Color.Blue,
                            topLeft = position,
                            size = androidx.compose.ui.geometry.Size(100f, 40f)
                        )
                    }
                }
            }
        }
    }
}
