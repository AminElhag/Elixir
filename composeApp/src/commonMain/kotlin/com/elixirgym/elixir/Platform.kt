package com.elixirgym.elixir

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform