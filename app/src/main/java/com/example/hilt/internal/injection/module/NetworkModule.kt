package com.example.hilt.internal.injection.module

import com.example.hilt.BuildConfig
import com.example.hilt.data.remote.ProductService
import com.example.hilt.data.remote.ReviewService
import com.example.hilt.internal.util.ErrorHandlingInterceptor
import com.example.hilt.internal.util.NetworkStateHolder
import com.squareup.moshi.Moshi
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    internal fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .addInterceptor(ErrorHandlingInterceptor(NetworkStateHolder))
            .addInterceptor(loggingInterceptor)

        return httpClient.build()
    }

    @Provides
    @Named(NAMED_REVIEW)
    fun provideReviewRetrofit(client: Lazy<OkHttpClient>, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL_REVIEW)
            .callFactory { client.get().newCall(it) }
            .build()

    @Provides
    @Named(NAMED_PRODUCT)
    fun provideProductRetrofit(client: Lazy<OkHttpClient>, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BuildConfig.BASE_URL_PRODUCT)
            .callFactory { client.get().newCall(it) }
            .build()

    @Provides
    fun provideProductService(@Named(NAMED_PRODUCT) retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Provides
    fun provideReviewServiceService(@Named(NAMED_REVIEW) retrofit: Retrofit): ReviewService =
        retrofit.create(ReviewService::class.java)


    const val CLIENT_TIME_OUT_SEC = 30L
    const val NAMED_REVIEW = "Review"
    const val NAMED_PRODUCT = "Product"
}