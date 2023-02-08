package com.zhang.hotfix_plugin_1

import android.content.Context
import androidx.annotation.NonNull
import com.zhang.hotfix_plugin_1.patch.Constant
import com.zhang.hotfix_plugin_1.patch.PatchDownloadService
import com.zhang.hotfix_plugin_1.patch.PatchInfo
import com.zhang.hotfix_plugin_1.patch.PatchInfoConstant

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class HotfixPlugin_1Plugin: FlutterPlugin, MethodCallHandler {

  private lateinit var channel : MethodChannel
  private lateinit var context : Context

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    context = flutterPluginBinding.applicationContext
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "native_2")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    val params = call.arguments as Map<*, *>?
    if (call.method == "apply_patch") {
      if(params != null) {
        val map = params["params"] as Map<*, *>?
        if(map != null) {
          val patchInfo = PatchInfo()
          patchInfo.baseApkCode = map[PatchInfoConstant.baseApkCode] as String?
          patchInfo.versionCode = map[PatchInfoConstant.versionCode] as String?
          patchInfo.versionName = map[PatchInfoConstant.versionName] as String?
          patchInfo.md5 = map[PatchInfoConstant.md5] as String?
          patchInfo.patchPath = map[PatchInfoConstant.patchPath] as String?
          patchInfo.des = map[PatchInfoConstant.des] as String?
          PatchDownloadService.start(context, patchInfo)
        }
      }

    }else if (call.method == "load_patch") {
      val list = FileUtil.loadPatch(context)
      result.success(list)
    }else if (call.method == "get_assets_path") {
      val assetsPath = Constant.localPatchPath(context) + Constant.assetDir
      result.success(assetsPath)
    } else {
      result.notImplemented()
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }
}
