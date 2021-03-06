package com.example.hilt.internal.ext

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.hilt.R
import java.io.IOException


fun View.hideKeyboard() {
    val inputManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
    inputManager?.hideSoftInputFromWindow(this.windowToken, 0)
}

fun ImageView.loadImage(
    url: String?,
    @DrawableRes placeholderRes: Int? = R.drawable.ic_baseline_insert_photo_24,
) {
    val requestOptions = RequestOptions().apply {
        placeholderRes?.let { placeholder(it) }
        apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
    }

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .into(this)
}

fun ImageView.loadImageForSharedAnimation(
    url: String?,
    onLoadCompleted: () -> Unit
) {
    val requestOptions = RequestOptions().apply {
        apply(RequestOptions.bitmapTransform(RoundedCorners(60)))
    }
    Glide.with(this)
        .load(url)
        .apply(requestOptions)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadCompleted.invoke()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                onLoadCompleted.invoke()
                return false
            }

        })
        .into(this)
}

fun Context.loadJsonFromAsset(name: String): String? {
    return try {
        with(assets.open(name)) {
            val size: Int = available()
            val buffer = ByteArray(size)
            read(buffer)
            close()
            String(buffer)
        }
    } catch (ex: IOException) {
        ex.printStackTrace()
        return null
    }
}