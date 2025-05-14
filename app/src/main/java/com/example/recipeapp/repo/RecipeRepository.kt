package com.example.recipeapp.repo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Base64
import com.example.recipeapp.R
import com.example.recipeapp.model.RecipeList
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class RecipeRepository(private val context: Context) {

    private val file = File(context.filesDir, "recipetypes.json")

    fun initializeFile() {
        if (!file.exists()) {
            val inputStream = context.resources.openRawResource(R.raw.recipetypes)
            val json = inputStream.bufferedReader().use { it.readText() }
            file.writeText(json)
        }
    }

    fun getRecipes(): List<RecipeList> {
        if (!file.exists()) return emptyList()
        val json = file.readText()
        return Gson().fromJson(json, object : TypeToken<List<RecipeList>>() {}.type)
    }

    private fun saveRecipes(recipes: List<RecipeList>) {
        val json = Gson().toJson(recipes)
        file.writeText(json)
    }

    fun addRecipe(newRecipe: RecipeList): Boolean {
        return try {
            val recipes = getRecipes().toMutableList()
            val uri = Uri.parse(newRecipe.image)
            val bitmap = getBitmapFromUri(uri)
            if (bitmap != null) {
                val savedImagePath = saveImageToFile(bitmap, newRecipe.name)
                if (savedImagePath.isNotEmpty()) {
                    newRecipe.image = savedImagePath
                    recipes.add(newRecipe)
                    saveRecipes(recipes)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }


    fun updateRecipe(updatedRecipe: RecipeList): Boolean {
        val recipes = getRecipes().toMutableList()
        val index = recipes.indexOfFirst { it.id == updatedRecipe.id }
        if (index != -1) {
            val uri = Uri.parse(updatedRecipe.image)
            val bitmap = getBitmapFromUri(uri)
            return if (bitmap != null) {
                val savedImagePath = saveImageToFile(bitmap, updatedRecipe.name)
                if (savedImagePath.isNotEmpty()) {
                    updatedRecipe.image = savedImagePath  // update image path here!
                    recipes[index] = updatedRecipe
                    saveRecipes(recipes)
                    true
                } else {
                    false
                }
            } else {
                false
            }
        }
        return false
    }


    fun deleteRecipe(recipeId: Int): Boolean {
        val recipes = getRecipes().toMutableList()
        val index = recipes.indexOfFirst { it.id == recipeId }
        if (index != -1) {
            recipes.removeAt(index)
            saveRecipes(recipes)
            return true
        }
        return false
    }

    private fun saveImageToFile(bitmap: Bitmap, imageName: String): String {
        val imageFile = File(context.filesDir, "$imageName.jpg")
        return try {
            FileOutputStream(imageFile).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    fun encodeImageToBase64(imageFilePath: String): String {
        val file = File(imageFilePath)
        if (file.exists()) {
            val bytes = file.readBytes()
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        }
        return ""
    }

    fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                @Suppress("DEPRECATION")
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
