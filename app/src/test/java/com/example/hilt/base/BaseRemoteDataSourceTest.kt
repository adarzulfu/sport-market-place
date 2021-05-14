package com.example.hilt.base

import com.example.hilt.internal.injection.module.MoshiModule
import com.example.hilt.internal.injection.module.NetworkModule
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

abstract class BaseRemoteDataSourceTest : BaseTest() {

    internal lateinit var moshiModule: MoshiModule
    internal lateinit var retrofit: Retrofit
    private lateinit var mockServer: MockWebServer

    override fun setUp() {
        moshiModule = MoshiModule()

        mockServer = MockWebServer()
        mockServer.start()

        val client = OkHttpClient.Builder()
            .connectTimeout(NetworkModule.CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .readTimeout(NetworkModule.CLIENT_TIME_OUT_SEC, TimeUnit.SECONDS)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(mockServer.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshiModule.provideMoshi()))
            .client(client)
            .build()
    }

    open fun mockHttpResponse(body: String, responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
                .setBody(body)
        )

    open fun mockHttpResponse(responseCode: Int) =
        mockServer.enqueue(
            MockResponse()
                .setResponseCode(responseCode)
        )
}
