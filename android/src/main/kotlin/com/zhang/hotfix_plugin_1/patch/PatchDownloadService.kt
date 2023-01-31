package com.zhang.hotfix_plugin_1.patch

import android.app.IntentService
import android.content.Intent
import android.content.Context
import android.os.Environment
import android.util.Log
import com.zhang.hotfix_plugin_1.FileUtil
import com.zhang.hotfix_plugin_1.Md5Util
import java.io.File

private const val EXTRA_PATCH_INFO = "extra_patch_info"
private const val TAG = "PatchDownloadService"

class PatchDownloadService : IntentService("PatchDownloadService") {

    override fun onHandleIntent(intent: Intent?) {
        val dir = getExternalFilesDir(null)?.absolutePath
        val dir2 = Environment.getExternalStorageDirectory().absolutePath
        Log.e("patch","onHandleIntent")
        Log.e("dir",dir ?: "null")
        Log.e("dir2",dir2 ?: "null")
        val patchInfo = intent?.getParcelableExtra<PatchInfo>(EXTRA_PATCH_INFO) ?: return
        Log.e(TAG, "*************** 开始下载补丁文件 ***************")
        Log.e(TAG, "***** 1. 校验该补丁的合法性")
        if(!PatchChecker.isValid(this, patchInfo)) return
        Log.e(TAG, "***** 2. 检测补丁文件夹xxx/patch")
        if(!FileUtil.mkdirsIfNeed(Constant.localPatchPath(context = this))) return
        Log.e(TAG, "***** 3. 新建临时文件patchInfoTemp.txt,并且将flutter侧传来的补丁信息写入到该文件中")
        writeToPatchInfoTemp(patchInfo)
        val patchPathType = PatchPathType.parse(patchInfo.patchPath)
        Log.e(TAG, "***** 4. 下载补丁文件")
        val isSuccess = PatchDownloaderFactory.createPatchDownloader(patchPathType)
            .download(this, patchPathType.patchPath)
        if(!isSuccess) return
        Log.e(TAG, "***** 5. 补丁文件下载成功")
        val patchInfoPathTemp = Constant.localPatchPath(this) + Constant.patchInfoTemp
        val patchPathTemp = Constant.localPatchPath(this) + Constant.patchTemp
        Log.e(TAG, "***** 6. 校验md5")
        if(patchInfo.md5 != Md5Util.getMD5(patchPathTemp)) {
            File(patchInfoPathTemp).delete()
            File(patchPathTemp).delete()
            return
        }
        Log.e(TAG, "***** 7. 旧补丁删除和新补丁文件重命名")
        val patchInfoPath = Constant.localPatchPath(this) + Constant.patchInfo
        val patchFilePath = Constant.localPatchPath(this) + Constant.patchFile
        File(patchInfoPath).delete()
        File(patchInfoPathTemp).renameTo(File(patchInfoPath))
        File(patchFilePath).delete()
        File(patchPathTemp).renameTo(File(patchFilePath))
        Log.e(TAG, "*************** 结束下载补丁文件 ***************")

    }

    private fun writeToPatchInfoTemp(patchInfo: PatchInfo) {
        val localPatchPath = Constant.localPatchPath(context = this)
        if(localPatchPath.isEmpty()) return
        val file = File(localPatchPath + Constant.patchInfoTemp)
        val map = mutableMapOf<String, String>()
        PatchInfo::class.java.declaredFields.forEach { field ->
            if (field.name != "CREATOR") {
                field.isAccessible = true
                map[field.name] = field.get(patchInfo) as String
            }
        }
        val list = map.map { it.key + "=" +it.value }
        file.writeText(list.joinToString("\n"))
    }

    companion object {
        @JvmStatic
        fun start(context: Context, patchInfo: PatchInfo) {
            val intent = Intent(context, PatchDownloadService::class.java).apply {
                putExtra(EXTRA_PATCH_INFO, patchInfo)
            }
            context.startService(intent)
        }
    }
}