package com.zhang.hotfix_plugin_1

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build


object AppUtil {
    fun getVersionName(context: Context): String {
        return getPackageInfo(context)?.versionName ?: ""
    }

    fun getVersionCode(context: Context): String {
        return getPackageInfo(context)?.versionCode?.toString() ?: ""
    }

    private fun getPackageInfo(context: Context): PackageInfo? {
        val result = kotlin.runCatching {
            context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_CONFIGURATIONS
            )
        }
        return result.getOrNull()
    }

    private fun getBestAbi(): String{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Build.SUPPORTED_ABIS.first()
        } else {
            ""
        }
    }

    fun getBestAbiFlag(): Int{
        return when(getBestAbi()) {
            "arm64-v8a" -> 1
            "armeabi-v7a" -> 2
            else -> 2
        }
    }

}