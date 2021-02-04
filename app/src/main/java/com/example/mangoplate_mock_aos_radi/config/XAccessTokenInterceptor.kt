package com.example.mangoplate_mock_aos_radi.config

import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.mangoplate_mock_aos_radi.config.ApplicationClass.Companion.sSharedPreferences
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class XAccessTokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val oAuthToken: String? = sSharedPreferences.getString(X_ACCESS_TOKEN, null)
        if (oAuthToken != null) {
            builder.addHeader("X-ACCESS-TOKEN", oAuthToken)
        }
        return chain.proceed(builder.build())
    }
}