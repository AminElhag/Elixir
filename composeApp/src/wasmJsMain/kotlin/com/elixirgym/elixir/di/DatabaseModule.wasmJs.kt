package com.elixirgym.elixir.di

import com.elixirgym.elixir.data.repository.ISessionManager
import com.elixirgym.elixir.data.repository.InMemorySessionManager
import org.koin.dsl.module

actual val databaseModule = module {
    single<ISessionManager> { InMemorySessionManager() }
}
