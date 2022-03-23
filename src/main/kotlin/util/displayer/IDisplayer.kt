package util.displayer

import util.helper.TileMatrix

interface IDisplayer {
    fun showMap(matrix: TileMatrix)
}