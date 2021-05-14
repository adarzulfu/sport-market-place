package com.example.hilt.internal.util

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ErrorHandlingInterceptor(private val networkStateHolder: NetworkStateHolder) : Interceptor {

    @Throws(IOException::class, Failure::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkStateHolder.isConnected) {
            throw Failure.NoConnectivityError
        }

        return try {
            chain.proceed(chain.request())
        } catch (e: Exception) {
            throw when (e) {
                is UnknownHostException -> Failure.UnknownError(e)
                is HttpException -> Failure.ApiError(e.code(), e.message())
                is SocketTimeoutException -> Failure.TimeOutError(e.message)
                else -> IOException()
            }
        }
    }
}