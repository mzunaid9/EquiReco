package com.example.equireco.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.ColumnInfo

@Entity(
    tableName = "obstacles",
    foreignKeys = [ForeignKey(
        entity = Parcours::class,
        parentColumns = ["id"],
        childColumns = ["parcoursId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Obstacle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val parcoursId: Int,
    val numero: String,
    val couleur: Long, // stocker couleur en ARGB
    val posX: Float,
    val posY: Float,
    val rotation: Float,
    val type: String // BARRE_SIMPLE, BARRE_DOUBLE, etc.
)
