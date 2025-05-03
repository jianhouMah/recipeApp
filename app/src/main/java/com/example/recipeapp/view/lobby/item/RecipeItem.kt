package com.example.recipeapp.view.lobby.item

import com.example.recipeapp.R
import com.example.recipeapp.databinding.ItemRecipeListBinding
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.utils.AppUtils.glidePicture
import com.xwray.groupie.Item
import com.xwray.groupie.databinding.BindableItem

class RecipeItem(
    private val rl: RecipeList,
    private val onRecipeClicked: ((rl: RecipeList) -> Unit)? = null
) : BindableItem<ItemRecipeListBinding>() {

    override fun getLayout() = R.layout.item_recipe_list

    override fun bind(viewBinding: ItemRecipeListBinding, position: Int) {
        viewBinding.apply {

            rl.let { recipe ->
                glidePicture(getImage(recipe.image), ivFood)
                tvCreatorName.text = recipe.creator
                tvFoodName.text = recipe.name

                clRecipe.setOnClickListener {
                    onRecipeClicked?.invoke(recipe)
                }
            }
        }
    }

    private fun getImage(image: String): Any {
        return when (image) {
            "ic_food_pizza" -> R.drawable.ic_food_pizza
            "ic_food_spagetti" -> R.drawable.ic_food_spagetti
            "ic_food_cake" -> R.drawable.ic_food_cake
            else -> image
        }
    }

    override fun hasSameContentAs(other: Item<*>): Boolean {
        if (other is RecipeItem) {
            return other.rl == rl
        }
        return super.hasSameContentAs(other)
    }

    override fun isSameAs(other: Item<*>): Boolean {
        if (other is RecipeItem) {
            return other.rl == rl
        }
        return super.isSameAs(other)
    }
}
