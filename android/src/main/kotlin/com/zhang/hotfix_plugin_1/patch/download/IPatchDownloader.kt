package com.zhang.hotfix_plugin_1.patch.download

import android.content.Context
import com.zhang.hotfix_plugin_1.patch.FilePatch
import com.zhang.hotfix_plugin_1.patch.PatchPathType
import com.zhang.hotfix_plugin_1.patch.UrlPatch

interface IPatchDownloader {
    fun download(context: Context, patchPath: String): Boolean
}

object PatchDownloaderFactory {
    fun createPatchDownloader(patchPathType: PatchPathType): IPatchDownloader {
        return when(patchPathType) {
            is UrlPatch -> NetPatchDownloader()
            is FilePatch -> FilePatchDownloader()
            else -> DefaultPatchDownloader()
        }
    }
}