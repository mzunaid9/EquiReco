package com.example.equireco.model

data class Obstacle(
    var number: String,
    var colorHex: String,
    var posX: Float,
    var posY: Float,
    var rotation: Float = 0f
)
