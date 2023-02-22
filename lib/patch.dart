
import 'package:hotfix_plugin_1/native_platform_interface.dart';
import 'package:hotfix_plugin_1/patch_info.dart';

class Patch {
  static Future<List<PatchInfo>> loadPatch() async{
    return await NativePlatformInterface.instance.loadPatch();
  }

  static Future applyPatch(PatchInfo patchInfo) async{
    return await NativePlatformInterface.instance.applyPatch(patchInfo);
  }

  static Future<String> getAssetsPath() async{
    return await NativePlatformInterface.instance.getAssetsPath();
  }

  static Future<bool> isCanLoad() async{
    return await NativePlatformInterface.instance.isCanLoad();
  }

  static Future<int> getBestAbiFlag() async{
    return await NativePlatformInterface.instance.getBestAbiFlag();
  }

  static Future<PatchInfo> checkPatch() async{
    return await NativePlatformInterface.instance.checkPatch();
  }

}
