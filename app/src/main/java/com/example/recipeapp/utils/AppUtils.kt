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
        backupImg: Int = R.drawable.ic_food_placeholder
    ) {
        createRequest(imageSource, backupImg)
            ?.error(backupImg)
            ?.placeholder(backupImg)
            ?.resize(1024, 1024)
            ?.centerCrop()
            ?.into(imageView)
    }

    private fun createRequest(source: Any?, backupImg: Int): RequestCreator? {
        val picasso = Picasso.get()

        return when (source) {
            is String -> when {
                source.isBlank() -> null
                source.startsWith("http", true) -> picasso.load(source)
                else -> picasso.load(File(source))
            }

            is File -> picasso.load(source)
            is Uri -> picasso.load(source)
            is Int -> picasso.load(source)

            else -> picasso.load(backupImg)
        }
    }
}
