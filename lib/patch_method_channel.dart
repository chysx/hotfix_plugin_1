import 'package:hotfix_plugin_1/native_platform_interface.dart';
import 'package:hotfix_plugin_1/patch_info.dart';

class PatchMethodChannel extends NativePlatformInterface {
  static const String load_patch = "load_patch";
  static const String apply_patch = "apply_patch";
  static const String get_assets_path = "get_assets_path";
  static const String is_can_load = "is_can_load";
  static const String get_best_abi_flag = "get_best_abi_flag";

  @override
  Future<List<PatchInfo>> loadPatch() async {
    final list = await runNativeMethod(load_patch, {}) as List<dynamic>?;
    if(list == null || list.isEmpty) return [];
    List<PatchInfo> result = [];
    for( var map in list) {
      if(map == null) continue;
      PatchInfo patchInfo = PatchInfo();
      for (var element in map.entries) {
        if(element.key == "baseApkCode") {
          patchInfo.baseApkCode = element.value;
        }
        if(element.key == "versionCode") {
          patchInfo.versionCode = element.value;
        }
        if(element.key == "versionName") {
          patchInfo.versionName = element.value;
        }
        if(element.key == "des") {
          patchInfo.des = element.value;
        }
        if(element.key == "md5") {
          patchInfo.md5 = element.value;
        }
        if(element.key == "patchPath") {
          patchInfo.patchPath = element.value;
        }
      }
      result.add(patchInfo);
    }

    return result;
  }

  @override
  Future applyPatch(PatchInfo patchInfo) async {
    Map<String, dynamic> params = {
      'baseApkCode': patchInfo.baseApkCode,
      'versionCode': patchInfo.versionCode,
      'versionName': patchInfo.versionName,
      'des': patchInfo.des,
      'md5': patchInfo.md5,
      'patchPath': patchInfo.patchPath,
    };
    return await runNativeMethod(apply_patch, params);
  }

  @override
  Future<String> getAssetsPath() async {
    return await runNativeMethod(get_assets_path, {});
  }

  @override
  Future<bool> isCanLoad() async {
    return await runNativeMethod(is_can_load, {});
  }

  @override
  Future<int> getBestAbiFlag() async {
    return await runNativeMethod(get_best_abi_flag, {});
  }

}
