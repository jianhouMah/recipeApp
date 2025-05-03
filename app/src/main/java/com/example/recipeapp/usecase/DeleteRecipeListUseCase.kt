package com.example.recipeapp.usecase

import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.repo.RecipeRepository

class DeleteRecipeListUseCase(
    private val recipeRepository: RecipeRepository
) {

    suspend operator fun invoke(recipeList: RecipeList): Boolean {
        return recipeRepository.deleteRecipe(recipeList.id)
    }
}