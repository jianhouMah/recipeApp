package com.example.recipeapp.repo

import com.example.recipeapp.api.ApiHelper
import com.example.recipeapp.model.GetRecipeApiList
import com.example.recipeapp.utils.Resource
import com.example.recipeapp.view.base.BaseRepository

class RecipeApiRepository(private val apiHelper: ApiHelper) : BaseRepository() {

    suspend fun getRecipeApiList(baseUrl: String): Resource<GetRecipeApiList> {
        return safeApiCall {
            apiHelper.getRecipeApiList(baseUrl)
        }
    }

}
