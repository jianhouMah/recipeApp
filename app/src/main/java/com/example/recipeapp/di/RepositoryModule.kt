package com.example.recipeapp.di

import com.example.recipeapp.repo.RecipeRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repoModule = module {

    single { RecipeRepository(androidContext()) }
}
