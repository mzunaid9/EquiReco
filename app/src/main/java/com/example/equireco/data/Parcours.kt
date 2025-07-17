package com.example.equireco.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "parcours")
data class Parcours(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nom: String,
    val lieu: String,
    val date: String
)
