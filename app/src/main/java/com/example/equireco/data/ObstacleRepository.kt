package com.example.equireco.data

import kotlinx.coroutines.flow.Flow

class ObstacleRepository(private val dao: ObstacleDao) {
    fun getObstacles(parcoursId: Int): Flow<List<Obstacle>> = dao.getObstaclesForParcours(parcoursId)
    suspend fun addObstacle(obstacle: Obstacle) = dao.insert(obstacle)
    suspend fun updateObstacle(obstacle: Obstacle) = dao.update(obstacle)
    suspend fun deleteObstacle(obstacle: Obstacle) = dao.delete(obstacle)
}
