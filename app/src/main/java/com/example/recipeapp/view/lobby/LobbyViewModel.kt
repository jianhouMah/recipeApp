package com.example.recipeapp.view.lobby

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.data.FoodType
import com.example.recipeapp.model.GetRecipeApiList
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.usecase.GetRecipeApiListUseCase
import com.example.recipeapp.usecase.GetRecipeListUseCase
import com.example.recipeapp.utils.Resource
import com.example.recipeapp.view.base.BaseViewModel
import kotlinx.coroutines.launch


class LobbyViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val getRecipeApiListUseCase: GetRecipeApiListUseCase,
) : BaseViewModel() {
    var selectedRecipeId: Int = FoodType.MAIN_COURSE.ids

    private val _getRecipeList = MutableLiveData<List<RecipeList>>()
    val getRecipeList
        get() = _getRecipeList

    private val _getApiRecipeList = MutableLiveData<List<RecipeList>>()
    val getApiRecipeList
        get() = _getApiRecipeList

    fun getRecipeList() = viewModelScope.launch {
        _getRecipeList.value = getRecipeListUseCase.invoke()
    }

    fun getApiRecipeList() = viewModelScope.launch {
        _getApiRecipeList.value = getRecipeApiListUseCase.invoke()
    }


    fun setSelectedFoodType(ids: Int) {
        selectedRecipeId = ids
    }

}
