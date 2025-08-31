package com.example.equireco.model

data class Parcours(
    var name: String,
    var location: String,
    var date: String,
    var obstacles: MutableList<Obstacle>
)
