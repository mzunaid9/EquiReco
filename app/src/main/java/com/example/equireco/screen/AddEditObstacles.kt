@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.equireco.screen

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Paint
import android.net.Uri
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.equireco.data.ParcoursRepository
import com.example.equireco.model.Obstacle
import com.example.equireco.model.Parcours
import java.io.File
import java.io.FileOutputStream

@Composable
fun AddEditObstaclesScreen(
    navController: NavController,
    parcoursRepository: ParcoursRepository,
    parcoursIndex: Int,
    parcours: Parcours
) {
    val context = LocalContext.current
    val obstacles = remember { mutableStateListOf<Obstacle>().apply { addAll(parcours.obstacles) } }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    var obstacleNumber by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.Red) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Éditer obstacles") }) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Bouton Enregistrer
                Button(
                    onClick = {
                        parcours.obstacles = obstacles.toMutableList()
                        parcoursRepository.updateParcours(parcoursIndex, parcours)
                        navController.navigate("home") { popUpTo("home") { inclusive = true } }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Enregistrer") }

                // Bouton Partager
                Button(
                    onClick = {
                        // Création bitmap
                        val bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
                        val canvas = android.graphics.Canvas(bitmap)
                        val paint = Paint().apply { textSize = 36f }

                        val w = 140f
                        val h = 70f
                        obstacles.forEach { o ->
                            paint.color = Color(android.graphics.Color.parseColor(o.colorHex)).toArgb()
                            canvas.save()
                            canvas.rotate(o.rotation, o.posX + w / 2f, o.posY + h / 2f)
                            canvas.drawRect(o.posX, o.posY, o.posX + w, o.posY + h, paint)
                            canvas.restore()
                            paint.color = android.graphics.Color.BLACK
                            canvas.drawText(o.number, o.posX + w / 2f, o.posY + h / 2f + 12f, paint)
                        }

                        // Sauvegarde temporaire
                        val file = File(context.cacheDir, "parcours_${parcours.name}.png")
                        FileOutputStream(file).use { bitmap.compress(Bitmap.CompressFormat.PNG, 100, it) }

                        // Partage via FileProvider
                        val uri: Uri = FileProvider.getUriForFile(
                            context,
                            "${context.packageName}.provider",
                            file
                        )
                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "image/png"
                            putExtra(Intent.EXTRA_STREAM, uri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }
                        context.startActivity(Intent.createChooser(shareIntent, "Partager le parcours"))
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Partager") }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Ligne d’ajout
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = obstacleNumber,
                    onValueChange = { obstacleNumber = it },
                    label = { Text("Numéro") },
                    modifier = Modifier.weight(1f)
                )
                listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow).forEach { c ->
                    Button(
                        onClick = { selectedColor = c },
                        colors = ButtonDefaults.buttonColors(containerColor = c),
                        modifier = Modifier.size(40.dp),
                        content = {}
                    )
                }
                Button(onClick = {
                    if (obstacleNumber.isNotBlank()) {
                        val hex = "#%08X".format(selectedColor.toArgb())
                        obstacles.add(
                            Obstacle(
                                number = obstacleNumber,
                                colorHex = hex,
                                posX = 100f,
                                posY = 100f,
                                rotation = 0f
                            )
                        )
                        obstacleNumber = ""
                        selectedIndex = obstacles.lastIndex
                    }
                }) { Text("Ajouter") }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFEFEFEF))
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(obstacles.size) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    selectedIndex = obstacles.indexOfLast { o ->
                                        val w = 140f
                                        val h = 70f
                                        offset.x in o.posX..(o.posX + w) &&
                                                offset.y in o.posY..(o.posY + h)
                                    }.takeIf { it >= 0 }
                                },
                                onDrag = { _, dragAmount ->
                                    selectedIndex?.let { i ->
                                        if (i in obstacles.indices) {
                                            val o = obstacles[i]
                                            obstacles[i] = o.copy(
                                                posX = o.posX + dragAmount.x,
                                                posY = o.posY + dragAmount.y
                                            )
                                        }
                                    }
                                }
                            )
                        }
                ) {
                    val w = 140f
                    val h = 70f
                    obstacles.forEach { o ->
                        val rectColor = Color(android.graphics.Color.parseColor(o.colorHex))
                        rotate(o.rotation, pivot = Offset(o.posX + w / 2f, o.posY + h / 2f)) {
                            drawRoundRect(
                                color = rectColor,
                                topLeft = Offset(o.posX, o.posY),
                                size = androidx.compose.ui.geometry.Size(w, h),
                                cornerRadius = androidx.compose.ui.geometry.CornerRadius(12f, 12f)
                            )
                            drawIntoCanvas { canvas ->
                                val paint = android.graphics.Paint().apply {
                                    color = android.graphics.Color.BLACK
                                    textSize = 36f
                                    isAntiAlias = true
                                    textAlign = android.graphics.Paint.Align.CENTER
                                }
                                val cx = o.posX + w / 2f
                                val cy = o.posY + h / 2f + 12f
                                canvas.nativeCanvas.drawText(o.number, cx, cy, paint)
                            }
                        }
                    }
                }

                // Contrôles rotation + suppression
                selectedIndex?.let { i ->
                    if (i in obstacles.indices) {
                        val o = obstacles[i]
                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("Obstacle sélectionné : ${o.number}")
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = { obstacles[i] = o.copy(rotation = (o.rotation + 15f) % 360f) }) { Text("⟳") }
                                Button(onClick = { obstacles[i] = o.copy(rotation = (o.rotation - 15f + 360f) % 360f) }) { Text("⟲") }
                                Button(onClick = { obstacles.removeAt(i); selectedIndex = null }) { Text("Supprimer") }
                            }
                        }
                    }
                }
            }
        }
    }
}
