package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import com.zhang.hotfix_plugin_1.patch.IPatchDownloader

class NetPatchDownloader: IPatchDownloader {
    override fun download(context: Context, patchPath: String): Boolean{
        return false
    }
}