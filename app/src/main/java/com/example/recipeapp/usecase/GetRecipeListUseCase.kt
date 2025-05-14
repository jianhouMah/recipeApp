package com.example.recipeapp.usecase

import com.example.recipeapp.data.FoodType
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.repo.RecipeApiRepository
import com.example.recipeapp.repo.RecipeRepository

class GetRecipeListUseCase(
    private val recipeRepository: RecipeRepository,
    private val getRecipeApiListUseCase: GetRecipeApiListUseCase
) {

    suspend operator fun invoke(): List<RecipeList> {
        val apiRecipe = getRecipeApiListUseCase.invoke()

        val localRecipe = recipeRepository.getRecipes().toMutableList()

        apiRecipe?.data?.categories?.map {ar ->
            localRecipe.add(
                RecipeList(
                    id = ar.idCategory.toInt(),
                    creator = "themealdb",
                    name = ar.strCategory,
                    image =  ar.strCategoryThumb ?: "",
                    type = FoodType.OTHER.ids,
                    ingredient = ar.strCategoryDescription,
                    steps = ar.strCategoryDescription,
                    isEditable = false
                )
            )
        }
        return localRecipe
    }

    suspend fun initRecipeFile() {
        recipeRepository.initializeFile()
    }
}