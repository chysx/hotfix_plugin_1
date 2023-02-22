import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1/patch.dart';
import 'package:hotfix_plugin_1/patch_info.dart';

class PatchViewModel extends ChangeNotifier {
  List<PatchInfo> patchInfoList = [];

  PatchViewModel() {
    loadPatchData();
  }

  loadPatchData() async {
    // patchInfoList = await Patch.loadPatch();
    // patchInfoList = await Patch.loadPatch();
    patchInfoList.add(await Patch.checkPatch());
    notifyListeners();
  }

  onTapCheck(BuildContext context, PatchInfo patchInfo) {
    Patch.applyPatch(patchInfo);
  }

}
