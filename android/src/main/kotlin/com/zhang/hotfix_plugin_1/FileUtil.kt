package com.zhang.hotfix_plugin_1

import android.content.Context
import com.zhang.hotfix_plugin_1.patch.Constant
import com.zhang.hotfix_plugin_1.patch.PatchInfo
import com.zhang.hotfix_plugin_1.patch.getPatchInfoFromFile
import java.io.*

object FileUtil {

    fun doCopy(context: Context, assetsPath: String, desPath: String) {
        if(!mkdirsIfNeed(desPath)) return
        var srcFiles = listOf<String>()
        try {
            srcFiles = context.assets.list(assetsPath)?.toList() ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        for (srcFileName in srcFiles) {
            val outFileName = desPath + File.separator + srcFileName
            val inFileName = assetsPath + File.separator + srcFileName
            try {
                val inputStream = context.assets.open(inFileName)
                copyAndClose(inputStream, FileOutputStream(outFileName))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun mkdirsIfNeed(dir: String): Boolean{
        if(dir.isEmpty()) return false
        var result = false
        val file = File(dir)
        if (!file.exists()) {
            try {
                result = file.mkdirs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else{
            result = true
        }
        return result
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        val buffer = ByteArray(1024)
        var read: Int
        while (inputStream.read(buffer).also { read = it } != -1) {
            outputStream.write(buffer, 0, read)
        }
    }

    fun copyAssets(context: Context, fileName: String, destFile: File): Boolean {
        if(fileName.isEmpty()) return false
        val isSuccess: Boolean
        var inputStream: InputStream? = null
        var out: FileOutputStream? = null
        try {
            inputStream = context.assets.open(fileName)
            out = FileOutputStream(destFile)
            copyFile(inputStream, out)
            isSuccess = true
        } finally {
            closeQuietly(inputStream)
            closeQuietly(out)
        }
        return isSuccess
    }

    @Throws(IOException::class)
    fun copy(inputStream: InputStream, out: OutputStream) {
        val buffer = ByteArray(1024)
        var n = 0
        while (-1 != inputStream.read(buffer).also { n = it }) {
            out.write(buffer, 0, n)
        }
    }

    @Throws(IOException::class)
    private fun copyAndClose(inputStream: InputStream, out: OutputStream) {
        copy(inputStream, out)
        closeQuietly(inputStream)
        closeQuietly(out)
    }

    private fun closeQuietly(out: OutputStream?) {
        try {
            out?.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    private fun closeQuietly(inputStream: InputStream?) {
        try {
            inputStream?.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }

    fun loadPatch(context: Context): List<Map<String, String>>{
        val dir = Constant.localExternalPath()
        if(!mkdirsIfNeed(dir)) return emptyList()
        val result = mutableListOf<Map<String, String>>()
        File(dir).walk().maxDepth(1)
            .forEach {  file ->
                if(file.extension == "txt"){
                    val patchInfo = getPatchInfoFromFile(context, file.absolutePath)
                    patchInfo.md5 = Md5Util.getMD5(patchInfo.patchPath)
                    val map = mutableMapOf<String, String>()
                    PatchInfo::class.java.declaredFields.forEach { field ->
                        if (field.name != "CREATOR") {
                            field.isAccessible = true
                            map[field.name] = field.get(patchInfo) as String
                        }
                    }
                    result.add(map)
                }
            }
//        File(dir).list { file, _ ->
//            if(file.extension == "txt"){
//                val patchInfo = getPatchInfoFromFile(context, file.absolutePath)
//                patchInfo.md5 = Md5Util.getMD5(patchInfo.patchPath)
//                val map = mutableMapOf<String, String>()
//                PatchInfo::class.java.declaredFields.forEach { field ->
//                    if (field.name != "CREATOR") {
//                        field.isAccessible = true
//                        map[field.name] = field.get(patchInfo) as String
//                    }
//                }
//                result.add(map)
//            }
//            true
//        }
        return result
    }

}