package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import android.util.Log
import com.zhang.hotfix_plugin_1.FileUtil
import com.zhang.hotfix_plugin_1.patch.download.ConfigDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

object ConfigChecker {
    private const val TAG = "ConfigChecker"

    fun check(context: Context, callback: (PatchInfo) -> Unit){
        val configDownloader = ConfigDownloader()
        Log.e(ConfigChecker.TAG, "***** 1. 在线检测配置文件")
        if(!FileUtil.mkdirsIfNeed(Constant.localPatchPath(context = context))) return
        GlobalScope.launch {
            val flag = withContext(Dispatchers.IO) {
                configDownloader.download(context,
                    "https://pd.test027.com/remote.php/dav/files/4BC046E1-E5B7-4C05-BECA-33ED31E51116/app/sjb/patch/patchInfo.txt")
            }
            val patchInfo: PatchInfo
            if(flag) {
                Log.e(ConfigChecker.TAG, "***** 2. 配置文件下载成功")

                patchInfo = getPatchInfoFromFile(context, Constant.localPatchPath(context = context) + Constant.patchInfo)
                File(Constant.localPatchPath(context = context) + Constant.patchInfo).delete()

                callback(patchInfo)
            }
        }

    }
}