package com.example.recipeapp.view.lobby.item

import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemApiRecipeListBinding
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.utils.AppUtils.glidePicture
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

class ApiRecipeItem(
    private val rl: RecipeList,
    private val onRecipeClicked: ((rl: RecipeList) -> Unit)? = null
) : BindableItem<ItemApiRecipeListBinding>() {

    override fun getLayout() = R.layout.item_api_recipe_list

    override fun bind(viewBinding: ItemApiRecipeListBinding, position: Int) {
        viewBinding.apply {

            rl.let { recipe ->
                glidePicture(recipe.image, ivFood)
                tvFoodName.text = recipe.name

                clRecipe.setOnClickListener {
                    onRecipeClicked?.invoke(recipe)
                }
            }
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is ApiRecipeItem) {
            return other.rl == rl
        }
        return super.hasSameContentAs(other)
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is ApiRecipeItem) {
            return other.rl == rl
        }
        return super.isSameAs(other)
    }
}
