package com.example.recipeapp.view.detailrecipe

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.navArgs
import com.example.recipeapp.R
import com.example.recipeapp.data.FoodType
import com.example.recipeapp.databinding.FragmentDetailRecipeBinding
import com.example.recipeapp.model.RecipeList
import com.example.recipeapp.utils.AppUtils
import com.example.recipeapp.view.base.BaseFragment
import org.koin.core.component.KoinComponent

class DetailRecipeFragment : BaseFragment<DetailRecipeViewModel, FragmentDetailRecipeBinding>(), KoinComponent {

    private val args: DetailRecipeFragmentArgs by navArgs()
    private var isEnabledEdit: Boolean = false
    private var foodTypePosition: Int = FoodType.MAIN_COURSE.ordinal
    private var lastRecipeListIds: Int = -1
    private var imagePath: String? = null

    override fun getLayout(): Int = R.layout.fragment_detail_recipe

    override fun setupViews() {
        binding?.apply {
            viewModel.getRecipeList()

            if (args?.isDetails == false) {
                setEnabled(true)
            } else {
                acSpinner.isEnabled = false
                acSpinner.isClickable = false
            }

            tvTitle.text = if (args?.isDetails == false) "Create Recipe" else ""
            ivEdit.visibility = if (args?.isDetails == false || args.recipe?.isEditable == false) View.INVISIBLE else View.VISIBLE
            ivRemove.visibility = if (args?.isDetails == false) View.INVISIBLE else View.VISIBLE
            ivAddImage.visibility = if (args?.isDetails == false) View.VISIBLE else View.GONE


            args?.recipe?.let { rl ->
                tvTitle.text = rl.name
                etCreatorName.setText(rl.creator)
                etFoodName.setText(rl.name)
                etIngredient.setText(rl.ingredient)
                etSteps.setText(rl.steps)
                AppUtils.glidePicture(getImage(rl.image), ivFood)

                ivRemove.setOnClickListener {
                    viewModel.deleteRecipe(rl)
                }
            }

            btnSubmit.setOnClickListener {
                val ids = if (args?.isDetails == true) args?.recipe?.id ?: -1 else lastRecipeListIds
                val newRecipe = RecipeList(
                    id = ids,
                    creator = etCreatorName.text.toString(),
                    name = etFoodName.text.toString(),
                    image = imagePath ?: args.recipe?.image ?: "",
                    type = FoodType.values().find { it.ordinal == foodTypePosition }?.ids ?: FoodType.MAIN_COURSE.ids,
                    ingredient = etIngredient.text.toString(),
                    steps = etSteps.text.toString(),
                    isEditable = true
                )

                if (lastRecipeListIds != -1) {
                    if (args?.isDetails == true) {
                        viewModel.updateRecipe(newRecipe)
                    } else {
                        viewModel.createRecipe(newRecipe)
                    }
                } else {
                    Toast.makeText(context, "Error Occur Recipe Ids Wrong Submit", Toast.LENGTH_LONG).show()
                }
            }


            ivBack.setOnClickListener {
                activity?.hideBtmNav(false)
                popBackStack()
            }

            ivEdit.setOnClickListener {
                setEnabled()
            }

            ivAddImage.setOnClickListener {
                imagePickerLauncher.launch("image/*")
            }

            setupFoodTypeSpinner()
        }
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imagePath = it.toString()
            val bitmap = getBitmapFromUri(it)
            binding?.ivFood?.setImageBitmap(bitmap)
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun setEnabled(isForceEnabled: Boolean = false) {
        binding?.apply {

            isEnabledEdit = if (!isForceEnabled) !isEnabledEdit else true
            tilCreatorName.isEnabled = isEnabledEdit
            tilFoodName.isEnabled = isEnabledEdit
            tilIngredient.isEnabled = isEnabledEdit
            tilSteps.isEnabled = isEnabledEdit
            acSpinner.isEnabled = isEnabledEdit
            acSpinner.isClickable = isEnabledEdit
            clFooter.visibility = if (isEnabledEdit) View.VISIBLE else View.GONE
            ivAddImage.visibility = if (isEnabledEdit) View.VISIBLE else View.GONE
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
            acSpinner.setSelection(args?.recipe?.type ?: FoodType.MAIN_COURSE.ids)

            acSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?, view: View?,
                    position: Int,
                    id: Long
                ) {
                    foodTypePosition = position
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    override fun setupObservers() {
        binding?.apply {
            viewModel.isSuccessUpdateRecipe.observe(viewLifecycleOwner) {
                Toast.makeText(context, if (it) "Successfully Update" else "Fail to Update", Toast.LENGTH_LONG).show()
            }

            viewModel.isSuccessDeleteRecipe.observe(viewLifecycleOwner) {
                Toast.makeText(context, if (it) "Successfully Delete" else "Fail to Delete", Toast.LENGTH_LONG).show()
                if (it) {
                    activity?.hideBtmNav(false)
                    popBackStack()
                }
            }

            viewModel.isSuccessCreateRecipe.observe(viewLifecycleOwner) {
                Toast.makeText(context, if (it) "Successfully Create New Recipe" else "Fail to Create New Recipe", Toast.LENGTH_LONG).show()
                if (it) {
                    clearText()
                }
            }

            viewModel.getRecipeList.observe(viewLifecycleOwner) {
                it?.let {
                    lastRecipeListIds = (it.lastOrNull()?.id ?: -2) + 1
                }
                println("getRecipeList $it")
            }
        }
    }

    private fun clearText() {
        binding?.apply {
            etCreatorName.setText("")
            etFoodName.setText("")
            etIngredient.setText("")
            etSteps.setText("")
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


    override fun onResume() {
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}