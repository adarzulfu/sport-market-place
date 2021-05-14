package com.example.hilt.internal.ext

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
    }

    Glide.with(this)
        .load(url)
        .apply(requestOptions)
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