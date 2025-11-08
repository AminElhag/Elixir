package com.elixirgym.elixir.di

import com.elixirgym.elixir.data.database.DatabaseDriverFactory
import com.elixirgym.elixir.data.repository.SessionManager
import com.elixirgym.elixir.database.ElixirDatabase
import org.koin.dsl.module

actual val databaseModule = module {
    single { DatabaseDriverFactory() }
    single { ElixirDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single<ISessionManager> { SessionManager(get()) }
}
