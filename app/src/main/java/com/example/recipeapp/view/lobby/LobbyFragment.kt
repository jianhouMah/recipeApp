package com.example.recipeapp.view.lobby

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.R
import com.example.recipeapp.data.FoodType
import com.example.recipeapp.databinding.FragmentLobbyBinding
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.view.base.BaseFragment
import com.example.recipeapp.view.home.HomeActivity
import com.example.recipeapp.view.lobby.item.RecipeItem
import com.xwray.groupie.GroupieAdapter
import com.xwray.groupie.Section
import org.koin.core.component.KoinComponent

class LobbyFragment : BaseFragment<LobbyViewModel, FragmentLobbyBinding>(), KoinComponent {

    private val groupieAdapter = GroupieAdapter()
    private val section = Section()
    private var recipeList: List<RecipeList> = emptyList()

    override fun getLayout(): Int = R.layout.fragment_lobby

    override fun setupViews() {
        binding?.apply {
            viewModel.getRecipeList()

            rvRecipe.apply {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = groupieAdapter
            }
            groupieAdapter.clear()
            groupieAdapter.update(listOf(section))
        }

    }

    override fun setupObservers() {
        binding?.apply {
            viewModel.getRecipeList.observe(viewLifecycleOwner) {
                it?.let {
                    setupFoodTypeSpinner()
                    recipeList = it
                    updateRecipeList(it)
                    println("getRecipeList $it")
                }
            }
        }
    }

    private fun setupFoodTypeSpinner() {
        binding?.apply {
            val adapter = ArrayAdapter(
                requireContext(),
                R.layout.item_spinner,
                FoodType.values().map { recipe -> recipe.name }
            )
            acSpinner.adapter = adapter

            acSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, view: View?,
                    position: Int,
                    id: Long
                ) {

                    val foodTypeIds = FoodType.values().find { it.ordinal == position }?.ids ?: -1
                    val newRecipeList = recipeList.filter { it.type == foodTypeIds }
                    updateRecipeList(newRecipeList)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun updateRecipeList(recipeList: List<RecipeList>) {
        binding?.apply {
            val recipe = recipeList?.sortedByDescending { it.id }?.map { recipeList ->
                RecipeItem(recipeList) { rc ->
                    activity?.hideBtmNav(true)
                    navigateTo(LobbyFragmentDirections.actionToDetailsRecipe(true, rc))
                }
            } ?: emptyList()

            section.update(recipe)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}