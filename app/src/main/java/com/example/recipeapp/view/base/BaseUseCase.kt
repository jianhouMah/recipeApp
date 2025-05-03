package com.example.recipeapp.view.base

abstract class BaseUseCase {
    protected fun getDefaultCacheKey(): String {
        return this::class.simpleName ?: ""
    }
}
