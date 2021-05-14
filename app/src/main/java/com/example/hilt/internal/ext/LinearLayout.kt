package com.example.hilt.internal.ext

import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import com.example.hilt.R


fun LinearLayout.addStar(starCount: Int) {
    removeAllViews()
    val totalStarCount = 5
    for (i in 0 until totalStarCount) {
        val starImage = AppCompatImageView(this.context)
        if (i < starCount) {
            starImage.setBackgroundResource(R.drawable.ic_baseline_star_rate_24)
        } else {
            starImage.setBackgroundResource(R.drawable.ic_baseline_star_border_24)
        }
        addView(starImage)
    }
}