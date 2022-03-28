package ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import util.helper.StageHelper
import util.player_action.PlayerActDown
import util.player_action.PlayerActLeft
import util.player_action.PlayerActRight
import util.player_action.PlayerActUp

/**
 * The main window of the app. The view and the base stage are defined here.
 *
 * TODO: Update to make it more Rogue-like (i.e. : Don't put the stage but the "game")
 */
@Composable
@Preview
fun App() {
    val stage by remember {
        mutableStateOf(
            StageHelper.stageBuilder.setMatrixWidth(40)
                .setMatrixLength(20)
                .setRoomNumber(7)
                .build().apply { spawnSomeFoes() }
        )
    }

    StageHelper.currentStage = stage

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(5.dp)
    ) {
        GridView(stage.map)
//        Log()
    }
}

/**
 * Resolves the key events in the game.
 * @return false if we don't use this event, otherwise true.
 */
@OptIn(ExperimentalComposeUiApi::class)
fun keyEvent(event: KeyEvent): Boolean {
    if (event.type != KeyEventType.KeyDown)
        return false

    val action = when (event.key) {
        Key.DirectionUp, Key.W -> PlayerActUp
        Key.DirectionDown, Key.S -> PlayerActDown
        Key.DirectionLeft, Key.A -> PlayerActLeft
        Key.DirectionRight, Key.D -> PlayerActRight
        else -> return false
    }

    StageHelper.currentStage.playTurn(action)

    return true
}
