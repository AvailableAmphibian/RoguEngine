package main

import map.Stage
import util.displayer.BaseDisplayer

fun main() {
//    val game = Game()

    val stage = Stage()
    stage.initMap()
//    stage.createPlayers()
    val displayer = BaseDisplayer()
    displayer.showMap(stage.map)

//    for (i in 0 .. 5) {
//        Thread.sleep(2000)
//        stage.playTurn()
//        displayer.showMap(stage.map)
//    }
}
