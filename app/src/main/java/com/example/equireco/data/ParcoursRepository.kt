package com.example.equireco.data

import kotlinx.coroutines.flow.Flow

class ParcoursRepository(private val dao: ParcoursDao) {
    val allParcours: Flow<List<Parcours>> = dao.getAllParcours()

    suspend fun insert(parcours: Parcours) = dao.insert(parcours)
    suspend fun update(parcours: Parcours) = dao.update(parcours)
    suspend fun delete(parcours: Parcours) = dao.delete(parcours)
}
