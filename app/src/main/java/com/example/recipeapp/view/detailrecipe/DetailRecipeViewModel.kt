package com.example.recipeapp.view.detailrecipe

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.usecase.CreateRecipeListUseCase
import com.example.recipeapp.usecase.DeleteRecipeListUseCase
import com.example.recipeapp.usecase.GetRecipeListUseCase
import com.example.recipeapp.usecase.UpdateRecipeListUseCase
import com.example.recipeapp.view.base.BaseViewModel
import kotlinx.coroutines.launch


class DetailRecipeViewModel(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val updateRecipeListUseCase: UpdateRecipeListUseCase,
    private val deleteRecipeListUseCase: DeleteRecipeListUseCase,
    private val createRecipeListUseCase: CreateRecipeListUseCase,
) : BaseViewModel() {

    private val _getRecipeList = MutableLiveData<List<RecipeList>>()
    val getRecipeList
        get() = _getRecipeList

    private val _isSuccessUpdateRecipe = MutableLiveData<Boolean>()
    val isSuccessUpdateRecipe
        get() = _isSuccessUpdateRecipe

    private val _isSuccessDeleteRecipe = MutableLiveData<Boolean>()
    val isSuccessDeleteRecipe
        get() = _isSuccessDeleteRecipe

    private val _isSuccessCreateRecipe = MutableLiveData<Boolean>()
    val isSuccessCreateRecipe
        get() = _isSuccessCreateRecipe

    fun updateRecipe(recipeList: RecipeList) = viewModelScope.launch {
        _isSuccessUpdateRecipe.value = updateRecipeListUseCase.invoke(recipeList)
    }

    fun deleteRecipe(recipeList: RecipeList) = viewModelScope.launch {
        _isSuccessDeleteRecipe.value = deleteRecipeListUseCase.invoke(recipeList)
    }

    fun createRecipe(recipeList: RecipeList) = viewModelScope.launch {
        _isSuccessCreateRecipe.value = createRecipeListUseCase.invoke(recipeList)
    }


    fun getRecipeList() = viewModelScope.launch {
        _getRecipeList.value = getRecipeListUseCase.invoke()
    }
}
