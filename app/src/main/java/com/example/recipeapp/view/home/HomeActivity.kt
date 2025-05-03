package com.example.recipeapp.view.home

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.recipeapp.R
import com.example.recipeapp.databinding.ActivityHomeBinding
import com.example.recipeapp.view.lobby.LobbyFragmentDirections
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.component.KoinComponent

class HomeActivity : AppCompatActivity(), KoinComponent {
    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private var navController: NavController? = null
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = true

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                navHostFragment = supportFragmentManager.findFragmentById(R.id.navFragment) as NavHostFragment
                navController = navHostFragment.navController
            }
        }

        binding.apply {
            clBtnAddRecipe.setOnClickListener {
                hideBtmNav(true)
                navMainLevelPage(R.id.detailRecipeFragment)
            }
        }
    }

    private fun navMainLevelPage(id: Int) {
        val navController = navController ?: return
        try {
            navController.popBackStack(navController.graph.startDestinationId, id == navController.graph.startDestinationId)
            val action = LobbyFragmentDirections.actionToDetailsRecipe(false, null)
            navController.navigate(action)
        } catch (e: Exception) {
        }
    }

    fun hideBtmNav(isHide: Boolean) {
        binding?.apply {
            clFooterNav.visibility = if (isHide) View.GONE else View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
    }
}