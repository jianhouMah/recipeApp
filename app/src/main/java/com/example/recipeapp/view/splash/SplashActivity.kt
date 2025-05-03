package com.example.recipeapp.view.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.example.recipeapp.databinding.ActivitySplashBinding
import com.example.recipeapp.view.home.HomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity(), KoinComponent {
    private lateinit var binding: ActivitySplashBinding
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashViewModel.initFile()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }, 3000)
    }
}
