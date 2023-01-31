package com.zhang.hotfix_plugin_1

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager


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
}