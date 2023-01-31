
import 'package:hotfix_plugin_1/native_platform_interface.dart';
import 'package:hotfix_plugin_1/patch_info.dart';

class Patch {
  static Future<List<PatchInfo>> loadPatch() async{
    return await NativePlatformInterface.instance.loadPatch();
  }

  static Future applyPatch(PatchInfo patchInfo) async{
    return await NativePlatformInterface.instance.applyPatch(patchInfo);
  }
}
