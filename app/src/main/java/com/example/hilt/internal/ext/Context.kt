package com.example.hilt.internal.ext

import android.content.Context
import com.example.hilt.internal.popup.Popup
import com.example.hilt.internal.popup.PopupListener
import com.example.hilt.internal.popup.PopupModel

fun Context.showPopup(model: PopupModel, listener: PopupListener? = null) {
    Popup(this, model, listener).show()
}