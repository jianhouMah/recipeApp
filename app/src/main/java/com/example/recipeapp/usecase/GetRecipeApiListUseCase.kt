package com.example.recipeapp.usecase

import com.example.recipeapp.BuildConfig
import com.example.recipeapp.data.FoodType
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.repo.RecipeApiRepository

class GetRecipeApiListUseCase(
    private val recipeApiRepository: RecipeApiRepository
) {
    suspend operator fun invoke(): List<RecipeList>? {
        val recipeList = recipeApiRepository.getRecipeApiList(BuildConfig.RECIPE_URL + "categories.php")

        return recipeList.data?.categories?.map { ar ->
            RecipeList(
                id = ar.idCategory.toInt(),
                creator = "themealdb",
                name = ar.strCategory,
                image = ar.strCategoryThumb ?: "",
                type = -99,
                ingredient = ar.strCategoryDescription,
                steps = ar.strCategoryDescription,
                isEditable = false
            )
        }
    }
}