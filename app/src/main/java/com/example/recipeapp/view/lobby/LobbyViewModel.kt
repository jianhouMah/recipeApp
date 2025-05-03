package com.example.recipeapp.view.lobby

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.usecase.GetRecipeListUseCase
import com.example.recipeapp.view.base.BaseViewModel
import kotlinx.coroutines.launch


class LobbyViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase,
) : BaseViewModel() {

    private val _getRecipeList = MutableLiveData<List<RecipeList>>()
    val getRecipeList
        get() = _getRecipeList

    fun getRecipeList() = viewModelScope.launch {
        _getRecipeList.value = getRecipeListUseCase.invoke()
    }

}
