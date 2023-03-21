package com.zhang.hotfix_plugin_1.patch.download

import android.content.Context

class DefaultPatchDownloader: IPatchDownloader {
    override fun download(context: Context, patchPath: String): Boolean {
        return false
    }
}