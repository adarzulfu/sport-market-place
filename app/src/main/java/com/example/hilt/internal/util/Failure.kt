package com.example.hilt.internal.util

import java.io.IOException

sealed class Failure : IOException() {
    class ApiError(var code: Int, override var message: String) : Failure()
    class UnknownError(val exception: Exception) : Failure()
    class TimeOutError(override var message: String?) : Failure()
    object NoConnectivityError : Failure()
    object EmptyResponse : Failure()

    abstract class FeatureFailure : Failure()
}
