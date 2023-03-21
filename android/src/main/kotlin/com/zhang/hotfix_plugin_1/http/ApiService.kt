package com.zhang.hotfix_plugin_1.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface ApiService {
    @Streaming
    @GET
    fun downloadConfig(@Url url: String?): Call<ResponseBody?>

    @Streaming
    @GET
    fun downloadSo(@Url url: String?): Call<ResponseBody?>

    @Streaming
    @GET
    fun downloadAssets(@Url url: String?): Call<ResponseBody?>
}