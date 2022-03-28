package ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import util.graph.tiles.Tile
import util.graph.tiles.TileMatrix
import util.helper.emojiFont

/**
 * Shows the TileMatrix as a Compose-like view
 */
@Composable
fun GridView(matrix: TileMatrix) = Column {
        for (row in matrix) RowOfGrid(row)
    }


@Composable
private fun RowOfGrid(row: Array<Tile>) = Row {
        for (item in row)
            GridItem(item)
    }

@Composable
private fun GridItem(tile: Tile) {
    val remTile = remember { mutableStateOf(tile) }
//    Text(remTile.value.charDisplay.toString(), fontFamily = FontFamily.Monospace)
    Text(remTile.value.emojiDisplay, fontFamily = emojiFont)
}
