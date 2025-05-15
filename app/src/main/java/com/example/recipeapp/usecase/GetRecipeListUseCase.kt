package com.example.recipeapp.usecase

import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.repo.RecipeRepository

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository,
) {

    suspend operator fun invoke(): List<RecipeList> {
        return recipeRepository.getRecipes()
    }

    suspend fun initRecipeFile() {
        recipeRepository.initializeFile()
    }
}