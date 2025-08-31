package com.example.equireco.data

import android.content.Context
import com.example.equireco.model.Parcours
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ParcoursRepository(private val context: Context) {
    private val gson = Gson()
    private val fileName = "parcours.json"
    private val file: File get() = File(context.filesDir, fileName)

    private val parcoursList = mutableListOf<Parcours>()

    init {
        loadFromFile()
    }

    fun getAllParcours(): List<Parcours> = parcoursList

    fun getParcours(index: Int): Parcours? = parcoursList.getOrNull(index)

    fun addParcours(parcours: Parcours) {
        parcoursList.add(parcours)
        saveToFile()
    }

    fun updateParcours(index: Int, parcours: Parcours) {
        if (index in parcoursList.indices) {
            parcoursList[index] = parcours
            saveToFile()
        }
    }

    fun deleteParcours(index: Int) {
        if (index in parcoursList.indices) {
            parcoursList.removeAt(index)
            saveToFile()
        }
    }

    private fun saveToFile() {
        val json = gson.toJson(parcoursList)
        file.writeText(json)
    }

    private fun loadFromFile() {
        if (file.exists()) {
            val json = file.readText()
            val type = object : TypeToken<List<Parcours>>() {}.type
            val data: List<Parcours> = gson.fromJson(json, type) ?: emptyList()
            parcoursList.clear()
            parcoursList.addAll(data)
        }
    }
}
