package com.example.recipeapp.di


import com.example.recipeapp.view.detailrecipe.DetailRecipeViewModel
import com.example.recipeapp.view.home.HomeViewModel
import com.example.recipeapp.view.lobby.LobbyViewModel
import com.example.recipeapp.view.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        SplashViewModel(get())
    }

    viewModel {
        HomeViewModel()
    }

    viewModel {
        LobbyViewModel(get())
    }

    viewModel {
        DetailRecipeViewModel(get(), get(), get(), get())
    }

}
