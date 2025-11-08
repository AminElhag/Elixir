package com.elixirgym.elixir

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.elixirgym.elixir.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "elixir",
    ) {
        App()
    }
}