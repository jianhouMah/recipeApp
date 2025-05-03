package com.example.recipeapp.usecase

import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.repo.RecipeRepository

class UpdateRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(recipeList: RecipeList): Boolean {
        return recipeRepository.updateRecipe(recipeList)
    }
}