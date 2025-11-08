package com.elixirgym.elixir

import androidx.compose.ui.window.ComposeUIViewController
import com.elixirgym.elixir.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}