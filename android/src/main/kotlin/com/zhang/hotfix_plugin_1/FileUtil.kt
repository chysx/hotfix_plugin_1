package com.zhang.hotfix_plugin_1

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.zhang.hotfix_plugin_1.patch.Constant
import com.zhang.hotfix_plugin_1.patch.PatchInfo
import com.zhang.hotfix_plugin_1.patch.getPatchInfoFromFile
import java.io.*
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile


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
                    Log.e("md5", "********fileName = " + file.name)
                    Log.e("md5", "********md5 = " + patchInfo.md5)
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

    fun unFile() {
        val dir = Constant.localExternalPath()
        if(!mkdirsIfNeed(dir)) return
//        unZipFile(dir + "test.zip", dir)
        UnFile.upZipFile(dir + "test.zip", dir)
    }

    /**
     * @param zipFile zip压缩包路径
     * @param folderPath 解压文件存放路径
     */
    fun unZipFile(zipFile: String, folderPath: String) {
        try {
            var zFile =  ZipFile(zipFile)
            val zList = zFile.entries()
            var ze: ZipEntry? = null
            val buf = ByteArray(1024 * 1024)
            while (zList.hasMoreElements()) {
                ze = zList.nextElement()

                // 列举的压缩文件里面的各个文件，判断是否为目录
                if (ze.isDirectory()) {
                    val dirstr = folderPath + ze.getName()
                    dirstr.trim()
                    val f =  File(dirstr)
                    f.mkdir();
                    continue
                }

                var os: OutputStream? = null
                var fos: FileOutputStream? = null
                // ze.getName()会返回 script/start.script这样的，是为了返回实体的File
//                val realFile: File = getRealFileName(folderPath, ze.getName())
                Log.e("haha",folderPath + ze.name)
                val realFile = File(folderPath + ze.name)
                fos = FileOutputStream(realFile)
                os = BufferedOutputStream(fos)
                val inputStream = BufferedInputStream(zFile.getInputStream(ze))
                var readLen = 0
                // 进行一些内容复制操作
                while (inputStream.read(buf, 0, 1024).also { readLen = it } != -1) {
                    os.write(buf, 0, readLen)
                }
                inputStream.close()
                os.close()
            }
            zFile.close()

            //解压完成后，删除压缩包文件（此处根据需要可进行删除）
            val file = File(zipFile)
            file.delete()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

}