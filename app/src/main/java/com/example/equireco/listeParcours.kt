package com.example.equireco

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.equireco.data.Parcours
import com.example.equireco.viewmodel.ParcoursViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun listeParcours(navController: NavController, viewModel: ParcoursViewModel) {
    val parcoursList by viewModel.allParcours.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Liste des Parcours") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addParcours") }) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(modifier = Modifier.padding(padding)) {
            items(parcoursList) { parcours ->
                ParcoursItem(
                    parcours,
                    onDelete = { viewModel.deleteParcours(parcours) },
                    onEdit = {
                        viewModel.setEditingParcours(parcours)
                        navController.navigate("addParcours")
                    }
                )
            }
        }
    }
}

@Composable
fun ParcoursItem(parcours: Parcours, onDelete: () -> Unit, onEdit: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(parcours.nom, style = MaterialTheme.typography.titleMedium)
                Text(parcours.lieu)
                Text(parcours.date)
            }
            Row {
                IconButton(onClick = onEdit) { Icon(Icons.Default.Edit, contentDescription = "Editer") }
                IconButton(onClick = onDelete) { Icon(Icons.Default.Delete, contentDescription = "Supprimer") }
            }
        }
    }
}
