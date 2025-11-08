package com.elixirgym.elixir.data.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.elixirgym.elixir.database.ElixirDatabase

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ElixirDatabase.Schema, "elixir.db")
    }
}
