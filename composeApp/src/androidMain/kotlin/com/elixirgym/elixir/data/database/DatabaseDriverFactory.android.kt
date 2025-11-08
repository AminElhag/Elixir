package com.elixirgym.elixir.data.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.elixirgym.elixir.database.ElixirDatabase

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(ElixirDatabase.Schema, context, "elixir.db")
    }
}
