package com.example.gitem.utils

import android.content.res.Resources
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.net.SocketTimeoutException

class NetworkExceptionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NetworkException("Timeout occurred", e)
        } catch (e: IOException) {
            throw NetworkException("Timeout occurred", e)
        }
    }
}

class NetworkException(s: String, e: IOException) : IOException(s, e)

fun Throwable.getExceptionMessage(): String {
    return when (this) {
        is Resources.NotFoundException -> {
            "Data not found"
        }
        is NetworkException -> {
            "Network error has occurred"
        }
        else -> {
            "An error has occurred"
        }
    }
}