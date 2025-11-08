package com.elixirgym.elixir.di

import com.elixirgym.elixir.data.database.DatabaseDriverFactory
import com.elixirgym.elixir.data.repository.ISessionManager
import com.elixirgym.elixir.data.repository.SessionManager
import com.elixirgym.elixir.database.ElixirDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

actual val databaseModule = module {
    single { DatabaseDriverFactory(androidContext()) }
    single { ElixirDatabase(get<DatabaseDriverFactory>().createDriver()) }
    single<ISessionManager> { SessionManager(get()) }
}
