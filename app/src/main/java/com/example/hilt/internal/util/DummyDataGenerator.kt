package com.example.hilt.internal.util

import android.content.Context
import com.example.hilt.data.local.model.ImageInfoModel
import com.example.hilt.internal.ext.fromJsonList
import com.example.hilt.internal.ext.loadJsonFromAsset
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DummyDataGenerator @Inject constructor(
    @ApplicationContext val context: Context,
    val moshi: Moshi
) {
    var dummyImageList: List<ImageInfoModel>

    init {
        dummyImageList = generateList("image.json")
    }

    private inline fun <reified T> generateList(assetPath: String): List<T> {
        val json = this.context.loadJsonFromAsset(assetPath)
        return moshi.fromJsonList(json)!!
    }
}