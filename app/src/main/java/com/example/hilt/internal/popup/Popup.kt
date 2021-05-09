package com.example.hilt.internal.popup

import android.app.AlertDialog
import android.content.Context
import com.example.hilt.R

class Popup(
    context: Context,
    private val model: PopupModel,
    private val listener: PopupListener? = null
) {
    private val alertDialogBuilder =
        AlertDialog.Builder(context, model.styleRes)

    init {
        with(alertDialogBuilder) {
            model.title?.let { setTitle(it) }
            model.message?.let { setMessage(it) }

            val cancelable = model.cancelable || listener == null

            setCancelable(cancelable)
            setButtons()
        }
    }

    private fun setButtons() = with(alertDialogBuilder) {
        val resources = alertDialogBuilder.context.resources
        listener?.let {
            val positiveButtonText =
                model.positiveButtonText ?: resources.getString(R.string.common_yes)
            val negativeButtonText =
                model.negativeButtonText ?: resources.getString(R.string.common_no)

            it.onPositiveButtonClick?.let {
                setPositiveButton(positiveButtonText) { _, _ -> it.invoke() }
            }
            it.onNegativeButtonClick?.let {
                setNegativeButton(negativeButtonText) { _, _ -> it.invoke() }
            }
        }
    }

    fun show() = alertDialogBuilder.show()
}
