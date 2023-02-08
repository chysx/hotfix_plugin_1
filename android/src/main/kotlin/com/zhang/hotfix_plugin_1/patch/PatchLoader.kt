package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import android.util.Log
import com.zhang.hotfix_plugin_1.AppUtil
import io.flutter.embedding.engine.FlutterShellArgs
import java.io.File

object PatchLoader {
    const val TAG = "PatchLoader"
    private fun isCanLoad(context: Context): Boolean {
        val result = kotlin.runCatching {
            Log.e(TAG, "*************** 开始加载补丁文件 ***************")
            val patchDir = Constant.localPatchPath(context = context)
            Log.e(TAG, "***** 1. 校验补丁文件目录是否存在")
            if(!File(patchDir).exists()) return false
            val patchInfoFileFlag = File(Constant.localPatchPath(context = context) + Constant.patchInfo).exists()
            val patchFileFlag = File(Constant.localPatchPath(context = context) + Constant.patchFile).exists()
            Log.e(TAG, "***** 2. 校验补丁文件是否存在")
            if(patchInfoFileFlag && patchFileFlag) {
                val patchInfoPathTemp = Constant.localPatchPath(context) + Constant.patchInfoTemp
                val patchPathTemp = Constant.localPatchPath(context) + Constant.patchTemp
                File(patchInfoPathTemp).deleteOnExit()
                File(patchPathTemp).deleteOnExit()

                val patchInfo = getPatchInfoFromFile(context, Constant.localPatchPath(context = context) + Constant.patchInfo)
                Log.e(TAG, "***** 3. 校验基准版本号")
                if(AppUtil.getVersionCode(context) != patchInfo.baseApkCode){
                    File(patchDir).delete()
                    throw RuntimeException("patch load fail")
                }
            }else {
                File(patchDir).delete()
                throw RuntimeException("patch load fail")
            }
            Log.e(TAG, "*************** 结束加载补丁文件 ***************")
        }
        return result.isSuccess
    }

    fun loadIfNeed(context: Context, flutterShellArgs: FlutterShellArgs) {
        if(isCanLoad(context)) {
            val patchPath = Constant.localPatchPath(context) + Constant.patchFile
            flutterShellArgs.add("--aot-shared-library-name=$patchPath")
        }
    }

}