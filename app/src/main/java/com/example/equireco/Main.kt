package com.example.equireco

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Main(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("EquiReco", modifier = Modifier.padding(bottom = 32.dp))

        Button(
            onClick = { navController.navigate("listeParcours") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text("Voir tous les parcours")
        }

        Button(
            onClick = { navController.navigate("addParcours") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Créer un parcours")
        }
    }
}
