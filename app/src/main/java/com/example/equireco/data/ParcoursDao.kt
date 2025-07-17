package com.example.equireco.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ParcoursDao {
    @Insert
    suspend fun insert(parcours: Parcours)

    @Update
    suspend fun update(parcours: Parcours)

    @Delete
    suspend fun delete(parcours: Parcours)

    @Query("SELECT * FROM parcours ORDER BY date DESC")
    fun getAllParcours(): Flow<List<Parcours>>
}
