package com.example.recipeapp.view.splash

import androidx.lifecycle.viewModelScope
import com.example.recipeapp.usecase.GetRecipeListUseCase
import com.example.recipeapp.view.base.BaseViewModel
import kotlinx.coroutines.launch


class SplashViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    ) : BaseViewModel() {


    fun initFile() = viewModelScope.launch {
        getRecipeListUseCase.initRecipeFile()
    }

}
