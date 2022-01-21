package main

import map.Stage

class Game {
    val stage = Stage()

    fun start() {
        stage.createStairs(3,3)
        stage.spawnPlayer()
    }

    fun takeTurn() {
        stage.movePlayer()
    }
}