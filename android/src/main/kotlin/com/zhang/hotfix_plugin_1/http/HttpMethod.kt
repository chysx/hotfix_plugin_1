package com.zhang.hotfix_plugin_1.http
internal interface HttpMethod {

    /**
     * get请求方法
     * host:域名
     * path:api路径
     * signKey:签名key
     */
    fun get(
        host: String,
        path: String,
        signKey: String
    ): String?

    /**
     * post请求方法
     * host:域名
     * path:api路径
     * signKey:签名key
     * body:post参数
     */
    fun post(
        host: String,
        path: String,
        signKey: String,
        body: String? = null,
    ): String?

    fun download(
        host: String,
        path: String,
        signKey: String,
    ): Boolean?

}