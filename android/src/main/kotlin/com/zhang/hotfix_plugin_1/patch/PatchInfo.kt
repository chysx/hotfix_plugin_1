package com.zhang.hotfix_plugin_1.patch

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import java.io.File

class PatchInfo() : Parcelable {
    /**
     * apk基准版本号
     */
    var baseApkCode: String? = null

    /**
     * 补丁版本号
     */
    var versionCode: String? = null

    /**
     * 补丁版本名称
     */
    var versionName: String? = null

    /**
     * 补丁描述信息
     */
    var des: String? = null

    /**
     * 补丁md5校验码
     */
    var md5: String? = null

    /**
     * 补丁下载路径
     */
    var patchPath: String? = null

    constructor(parcel: Parcel) : this() {
        baseApkCode = parcel.readString()
        versionCode = parcel.readString()
        versionName = parcel.readString()
        des = parcel.readString()
        md5 = parcel.readString()
        patchPath = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(baseApkCode)
        parcel.writeString(versionCode)
        parcel.writeString(versionName)
        parcel.writeString(des)
        parcel.writeString(md5)
        parcel.writeString(patchPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PatchInfo> {
        override fun createFromParcel(parcel: Parcel): PatchInfo {
            return PatchInfo(parcel)
        }

        override fun newArray(size: Int): Array<PatchInfo?> {
            return arrayOfNulls(size)
        }
    }

}

object PatchInfoConstant {
    const val baseApkCode = "baseApkCode"
    const val versionCode = "versionCode"
    const val versionName = "versionName"
    const val des = "des"
    const val md5 = "md5"
    const val patchPath = "patchPath"
}

fun getPatchInfoFromFile(context: Context,path: String) : PatchInfo {
    val result = kotlin.runCatching {
        val patchInfo = PatchInfo()
        val file = File(path)
        file.readLines().forEach {
            val name = it.split("=")[0]
            val value = it.split("=")[1]
            PatchInfo::class.java.declaredFields.forEach { field ->
                if (field.name != "CREATOR") {
                    field.isAccessible = true
                    if(name == field.name) field.set(patchInfo, value)
                }
            }
        }
        patchInfo
    }
    return result.getOrElse { PatchInfo() }
}

sealed class PatchPathType(open val patchPath: String){
    companion object{
        fun parse(patchPath: String?): PatchPathType {
            return when{
                patchPath == null || patchPath.isEmpty() -> NonePatch("")
                patchPath.contains("http") || patchPath.contains("https") -> UrlPatch(patchPath)
                else-> FilePatch(patchPath)
            }
        }
    }
}


class NonePatch(override val patchPath: String) : PatchPathType(patchPath)
class UrlPatch(override val patchPath: String): PatchPathType(patchPath)
class FilePatch(override val patchPath: String): PatchPathType(patchPath)

