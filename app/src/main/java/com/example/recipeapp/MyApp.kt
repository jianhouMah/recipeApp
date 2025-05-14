package com.example.recipeapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.recipeapp.di.appModule
import com.example.recipeapp.di.repoModule
import com.example.recipeapp.di.useCaseModule
import com.example.recipeapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MyApp : Application() {

    override fun onCreate() {

        super.onCreate()
        GlobalContext.startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    appModule,
                    repoModule,
                    viewModelModule,
                    useCaseModule,
                )
            )
        }
    }

}