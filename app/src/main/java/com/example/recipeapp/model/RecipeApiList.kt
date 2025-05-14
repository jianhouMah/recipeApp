package com.example.recipeapp.model

data class GetRecipeApiList(
    val categories: List<CategoriesList>
)

data class CategoriesList(
    val idCategory: String,
    val strCategory: String,
    val strCategoryThumb: String,
    val strCategoryDescription: String,
)
