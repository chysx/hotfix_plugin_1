package com.zhang.hotfix_plugin_1.patch

import android.content.Context

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