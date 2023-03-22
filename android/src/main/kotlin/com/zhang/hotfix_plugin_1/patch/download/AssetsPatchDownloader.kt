package com.zhang.hotfix_plugin_1.patch.download

import android.content.Context
import android.util.Log
import com.zhang.hotfix_plugin_1.FileUtil
import com.zhang.hotfix_plugin_1.UnFile
import com.zhang.hotfix_plugin_1.http.apiService
import com.zhang.hotfix_plugin_1.patch.Constant
import java.io.*


class AssetsPatchDownloader: IPatchDownloader {
//    override fun download(context: Context, patchPath: String): Boolean{
//        Log.e("AssetsPatchDownloader", "***** patchPath = " + patchPath)
//        val result = kotlin.runCatching {
//            val response =
//                apiService.downloadAssets(patchPath).execute()
//            if(response.isSuccessful) {
//                Log.e("AssetsPatchDownloader", "***** asset资源下载成功")
//                val body = response.body()
//                if(body != null){
//                    Log.e("AssetsPatchDownloader", "***** asset资源body 有值")
//                    FileUtil.copy(
//                        body.byteStream(),
//                        FileOutputStream(Constant.localPatchPath(context) + Constant.assetZip)
//                    )
//                    Log.e("AssetsPatchDownloader", "***** asset资源文件写入成功：" + Constant.localPatchPath(context) + Constant.assetZip)
//                    UnFile.upZipFile(
//                        Constant.localPatchPath(context) + Constant.assetZip,
//                        Constant.localPatchPath(context)
//                    )
//                    Log.e("AssetsPatchDownloader", "***** asset资源文件解压成功：")
//
//                    File(Constant.localPatchPath(context) + Constant.assetZip).deleteOnExit()
//                }else {
//                    throw Exception("body 为 null")
//                }
//            }else {
//                throw Exception("response 响应失败")
//            }
//        }
//        result.exceptionOrNull()?.printStackTrace()
//        return result.isSuccess
//    }

    override fun download(context: Context, patchPath: String): Boolean{
        val result = kotlin.runCatching {
            File(Constant.localExternalPath() + Constant.assetDir).copyRecursively(
                File(Constant.localPatchPath(context = context) + Constant.assetDir),
                overwrite = true
            )
            Log.e("AssetsPatchDownloader", "***** asset资源copy成功")
        }
        result.exceptionOrNull()?.printStackTrace()
        return result.isSuccess
    }

}