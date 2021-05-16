package com.example.hilt.scene.productdetail.rating

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.hilt.R


open class RatingDialogManager(
    val context: Context,
    private val listener: RatingListener,
    private val totalRatingCount: Int = 5
) {
    private val alertDialogBuilder =
        AlertDialog.Builder(context)

    init {
        with(alertDialogBuilder) {
            setTitle(context.resources.getString(R.string.rating_title))
            setUI()
        }
    }

    private fun setUI() = with(alertDialogBuilder) {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        val ratingView = createRatingView()
        val editText = createEditTextView()

        linearLayout.addView(ratingView)
        linearLayout.addView(editText)

        alertDialogBuilder.setView(linearLayout)

        setPositiveButton(context.getString(R.string.common_save)) { _, _ ->
            listener.onRatingReady(ratingView.progress, editText.text.toString())
        }
        setNegativeButton(context.getString(R.string.common_cancel)) { _, _ ->

        }
    }

    private fun createRatingView(): RatingBar {
        val rating = RatingBar(context)
        val layoutParams = createCommonLayoutParams()

        layoutParams.topMargin = context.resources.getDimension(R.dimen.margin_medium).toInt()
        layoutParams.marginStart = context.resources.getDimension(R.dimen.margin_medium).toInt()
        val layers = rating.progressDrawable as LayerDrawable
        DrawableCompat.setTint(
            layers.getDrawable(0),
            ContextCompat.getColor(context, R.color.black)
        )
        DrawableCompat.setTint(
            layers.getDrawable(1),
            ContextCompat.getColor(context, R.color.transparent)
        )

        DrawableCompat.setTint(
            layers.getDrawable(2),
            ContextCompat.getColor(context, R.color.seafoam)
        )

        rating.layoutParams = layoutParams
        rating.numStars = totalRatingCount
        rating.stepSize = 1f
        return rating
    }

    private fun createEditTextView(): EditText {
        val editText = AppCompatEditText(context)
        editText.hint = context.resources.getString(R.string.rating_hint)
        val layoutParams = createCommonLayoutParams(width = LinearLayout.LayoutParams.MATCH_PARENT)
        editText.layoutParams = layoutParams
        return editText
    }

    private fun createCommonLayoutParams(
        width: Int = LinearLayout.LayoutParams.WRAP_CONTENT,
        height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
    ): LinearLayout.LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(
            width,
            height
        )
        layoutParams.marginStart = context.resources.getDimension(R.dimen.margin_medium).toInt()
        layoutParams.marginEnd = context.resources.getDimension(R.dimen.margin_medium).toInt()

        return layoutParams
    }

    fun show() {
        alertDialogBuilder.show()
    }
}
