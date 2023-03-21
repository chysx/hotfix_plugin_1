package com.zhang.hotfix_plugin_1.patch.download

import android.content.Context
import com.zhang.hotfix_plugin_1.FileUtil
import com.zhang.hotfix_plugin_1.patch.Constant
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class FilePatchDownloader: IPatchDownloader {
    override fun download(context: Context, patchPath: String): Boolean{
        val result = kotlin.runCatching {
            File(Constant.localExternalPath() + Constant.assetDir).copyRecursively(
                File(Constant.localPatchPath(context = context) + Constant.assetDir),
                overwrite = true
            )
            FileUtil.copy(FileInputStream(patchPath), FileOutputStream(
                Constant.localPatchPath(
                    context
                ) + Constant.patchTemp
            ))
        }
        result.exceptionOrNull()?.printStackTrace()
        return result.isSuccess
    }
}