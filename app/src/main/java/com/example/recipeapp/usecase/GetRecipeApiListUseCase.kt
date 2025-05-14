package com.example.recipeapp.usecase

import com.example.recipeapp.BuildConfig
import com.example.recipeapp.model.GetRecipeApiList
import com.example.recipeapp.repo.RecipeApiRepository
import com.example.recipeapp.utils.Resource

class GetRecipeApiListUseCase(
    private val recipeApiRepository: RecipeApiRepository
) {
    suspend operator fun invoke(): Resource<GetRecipeApiList> {
        return recipeApiRepository.getRecipeApiList(BuildConfig.RECIPE_URL + "categories.php")
    }

}