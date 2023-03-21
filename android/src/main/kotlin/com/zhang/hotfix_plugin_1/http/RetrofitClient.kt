package com.zhang.hotfix_plugin_1.http

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val apiService by lazy {
    RetrofitClient.apiService
}

internal object RetrofitClient {
    private const val baseUrl = "https://pd.test027.com/remote.php/dav/files/4BC046E1-E5B7-4C05-BECA-33ED31E51116/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient().newBuilder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(HeaderInterceptor())

        return builder.build()
    }

    private class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Basic d2VpLnpodTpDZmxAMjAyMg==")
                .build()
            return chain.proceed(request)
        }
    }

}