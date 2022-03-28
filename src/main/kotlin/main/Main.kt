package main

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import ui.App
import ui.Log
import ui.keyEvent

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Rogue",
        state = rememberWindowState(),
        onKeyEvent = ::keyEvent
    ) {
        App()
    }
}
