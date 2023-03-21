package com.zhang.hotfix_plugin_1.http

//被依赖端注入进来的get请求实现
typealias ExternalGet = (
    host: String,
    path: String,
    signKey: String
) -> String?

//被依赖端注入进来的post请求实现
typealias ExternalPost = (
    host: String,
    path: String,
    signKey: String,
    body: String?,
) -> String?

object PatchHttp : HttpMethod {

    private var getRef: ExternalGet? = null

    private var postRef: ExternalPost? = null

    override fun get(
        host: String,
        path: String,
        signKey: String
    ): String? {
        var result: String? = null
        try {
            result = getRef?.invoke(host, path, signKey)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return result
    }

    override fun post(
        host: String,
        path: String,
        signKey: String,
        body: String?,
    ): String? {
        var result: String? = null
        try {
            result = postRef?.invoke(host, path, signKey, body)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        return result
    }

    override fun download(host: String, path: String, signKey: String): Boolean? {
        TODO("Not yet implemented")
    }


    fun registerNativeGet(nativeGet: ExternalGet) {
        this.getRef = nativeGet
    }

    fun registerNativePost(nativePost: ExternalPost) {
        this.postRef = nativePost
    }

}