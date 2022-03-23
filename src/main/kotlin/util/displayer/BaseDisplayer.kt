package util.displayer

import util.helper.TileMatrix

class BaseDisplayer:IDisplayer {
    override fun showMap(matrix: TileMatrix) {
        val sb = StringBuilder()

        for(row in matrix) {
            for (item in row) {
                sb.append(item.charDisplay)
            }
            sb.append('\n')
        }

        println(sb.toString())
    }
}