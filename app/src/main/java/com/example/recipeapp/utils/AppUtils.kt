package com.example.recipeapp.utils

import android.net.Uri
import android.widget.ImageView
import com.example.recipeapp.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import org.koin.core.component.KoinComponent
import java.io.File

object AppUtils : KoinComponent {

    fun glidePicture(
        imageSource: Any?,
        imageView: ImageView?,
        backupImg: Int? = R.drawable.ic_food_placeholder
    ) {
        getPicasso(imageSource, backupImg)?.into(imageView)
    }

    private fun getPicasso(source: Any?, backupImg: Int?): RequestCreator? {
        val pGet = Picasso.get()

        val picasso: RequestCreator? = when (source) {
            is String -> if (source.isNotEmpty()) pGet.load(source) else null
            is Int -> pGet.load(source)
            is Uri -> pGet.load(source)
            is File -> pGet.load(source)
            else -> backupImg?.let { pGet.load(it) }
        }

        return picasso?.resize(1024, 1024)?.centerCrop()?.apply {
            backupImg?.let { error(it).placeholder(it) }
        }
    }

}