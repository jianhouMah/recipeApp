package com.example.recipeapp.api

import com.example.recipeapp.model.GetRecipeApiList


class ApiHelperImpl(private val apiService: ApiService) : ApiHelper {

    override suspend fun getRecipeApiList(baseUrl: String): GetRecipeApiList  = apiService.getRecipeApiList(baseUrl)
}