package com.example.hilt.internal.module

import com.example.hilt.BuildConfig
import com.example.hilt.data.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {

    @Provides
    fun provideProductService(): ProductService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .build()
            .create(ProductService::class.java)
    }
}