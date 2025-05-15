package com.example.recipeapp.di

import com.example.recipeapp.usecase.CreateRecipeListUseCase
import com.example.recipeapp.usecase.DeleteRecipeListUseCase
import com.example.recipeapp.usecase.GetRecipeApiListUseCase
import com.example.recipeapp.usecase.GetRecipeListUseCase
import com.example.recipeapp.usecase.UpdateRecipeListUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetRecipeListUseCase(get())
    }

    factory {
        GetRecipeApiListUseCase(get())
    }

    factory {
        UpdateRecipeListUseCase(get())
    }

    factory {
        DeleteRecipeListUseCase(get())
    }

    factory {
        CreateRecipeListUseCase(get())
    }
}
