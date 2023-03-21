package com.zhang.hotfix_plugin_1.patch.download

import android.content.Context
import com.zhang.hotfix_plugin_1.FileUtil
import com.zhang.hotfix_plugin_1.http.apiService
import com.zhang.hotfix_plugin_1.patch.Constant
import java.io.*


class NetPatchDownloader: IPatchDownloader {
    override fun download(context: Context, patchPath: String): Boolean{
        val result = kotlin.runCatching {
            val response =
                apiService.downloadSo(patchPath).execute()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null){
                    FileUtil.copy(
                        body.byteStream(),
                        FileOutputStream(Constant.localPatchPath(context) + Constant.patchTemp)
                    )
                }else {
                    throw Exception("body 为 null")
                }
            }else {
                throw Exception("response 响应失败")
            }
        }
        result.exceptionOrNull()?.printStackTrace()
        return result.isSuccess
    }

}