package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import android.util.Log
import com.zhang.hotfix_plugin_1.AppUtil

object PatchChecker {
    fun isValid(context: Context, patchInfo: PatchInfo): Boolean {
        val result = kotlin.runCatching {
            val baseApkCodeFlag = AppUtil.getVersionCode(context) == patchInfo.baseApkCode
            val patchInfoFromTxt = getPatchInfoFromFile(context, Constant.localPatchPath(context = context) + Constant.patchInfo)
            val versionCodeFlag = (patchInfoFromTxt.versionCode?.toIntOrNull() ?: 0) < (patchInfo.versionCode?.toIntOrNull() ?: 0)
            val md5 = patchInfoFromTxt.md5 != patchInfo.md5
            if(!baseApkCodeFlag) {
                Log.e(PatchLoader.TAG, "***** 基准版本不一致")
            }
            if(!versionCodeFlag) {
                Log.e(PatchLoader.TAG, "***** 新的补丁版本小于或等于旧的补丁版本")
            }
            if(!md5) {
                Log.e(PatchLoader.TAG, "***** 新的补丁文件的MD5值等于旧相等")
            }
            baseApkCodeFlag && versionCodeFlag && md5
        }
        return result.getOrElse {  false }
    }
}