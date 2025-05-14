package com.example.recipeapp.api

import com.example.recipeapp.model.GetRecipeApiList
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun getRecipeApiList(
        @Url baseUrl: String,
    ): GetRecipeApiList
}