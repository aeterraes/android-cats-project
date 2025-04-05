package my.mvp.api

import android.util.Log
import my.mvp.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor : Interceptor {
    private val key = BuildConfig.API_KEY
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-api-key", key)
            .build()
        Log.d("API", "Request: ${request.url}")
        return chain.proceed(request)
    }
}
