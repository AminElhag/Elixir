package com.elixirgym.elixir.di

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.elixirgym.elixir.presentation.viewmodels.AccountCreationViewModel
import com.elixirgym.elixir.presentation.viewmodels.LoginViewModel
import org.koin.dsl.module

val appModule = module {
    // ViewModels / ScreenModels
    factory<AccountCreationViewModel> { AccountCreationViewModel() }
    factory<LoginViewModel> { LoginViewModel() }
}
