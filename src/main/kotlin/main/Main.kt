package main

fun main() {
    val game = Game()
    game.start()
    println(game.stage.map.toString())

    for (i in 0..5){
        Thread.sleep(1000)
        game.takeTurn()
        println(game.stage.map.toString())
    }
}