package com.example.equireco.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ObstacleDao {
    @Insert suspend fun insert(obstacle: Obstacle)
    @Update suspend fun update(obstacle: Obstacle)
    @Delete suspend fun delete(obstacle: Obstacle)

    @Query("SELECT * FROM obstacles WHERE parcoursId = :parcoursId ORDER BY id ASC")
    fun getObstaclesForParcours(parcoursId: Int): Flow<List<Obstacle>>
}
