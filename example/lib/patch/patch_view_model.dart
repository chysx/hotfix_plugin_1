import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1/patch.dart';
import 'package:hotfix_plugin_1/patch_info.dart';

class PatchViewModel extends ChangeNotifier {
  List<PatchInfo> patchInfoList = [];

  PatchViewModel() {
    loadPatchData();
  }

  loadPatchData() async {
    await Future.delayed(const Duration(milliseconds: 3));
    // PatchInfo patchInfo = PatchInfo();
    // patchInfo.baseApkCode = "1";
    // patchInfo.versionCode = "1";
    // patchInfo.versionName = "1.0";
    // patchInfo.des = "des";
    // patchInfo.md5 = "md5";
    // patchInfo.patchPath = "/storage/emulated/0/libapp.so";
    //
    // PatchInfo patchInfo2 = PatchInfo();
    // patchInfo2.baseApkCode = "1";
    // patchInfo2.versionCode = "1";
    // patchInfo2.versionName = "1.0";
    // patchInfo2.des = "des";
    // patchInfo2.md5 = "md5";
    // patchInfo2.patchPath = "/storage/emulated/0/libapp.so2222";
    // patchInfoList = [patchInfo, patchInfo2];
    // notifyListeners();

    patchInfoList = await Patch.loadPatch();
    notifyListeners();
  }

  onTapCheck(BuildContext context, PatchInfo patchInfo) {
    Patch.applyPatch(patchInfo);
  }

}
