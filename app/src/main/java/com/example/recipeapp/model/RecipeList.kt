package com.example.recipeapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeList(
    val id: Int,
    val creator: String,
    val name: String,
    var image: String,
    val type: Int,
    val ingredient: String,
    val steps: String,
    val isEditable: Boolean
) : Parcelable
