package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import android.os.Build
import android.os.Environment

object Constant {
    const val patchDir = "/patch/"
    const val patchInfo = "patchInfo.txt"
    const val patchInfoTemp = "patchInfoTemp.txt"
    const val patchFile = "libapp.so"
    const val patchTemp = "patchTemp.wy"
    fun localPatchPath(context: Context): String {
        // android N 对应的是7，发布于2016-08-01，补丁包不支持Android7
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return ""
        return context.dataDir.absolutePath + patchDir
    }
    fun localExternalPath(): String {
        return Environment.getExternalStorageDirectory().absolutePath + patchDir
    }
}