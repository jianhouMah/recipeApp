package com.example.recipeapp.api

import com.example.recipeapp.model.GetRecipeApiList

interface ApiHelper {
    suspend fun getRecipeApiList(baseUrl: String): GetRecipeApiList
}