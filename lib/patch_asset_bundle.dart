
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hotfix_plugin_1/patch.dart';

class PatchAssetBundle extends PlatformAssetBundle {
  String? dataPath;
  bool isAndroid = true;
  @override
  Future<ByteData> load(String key) async {
    if(isAndroid && dataPath == null){
      try{
        debugPrint("获取asset资源路径");
        dataPath = await Patch.getAssetsPath();
      }catch(e){
        isAndroid = false;
      }
    }
    final ByteData? asset;
    debugPrint("加载资源 : $dataPath/$key");
    File file = File("$dataPath/$key");
    if(file.existsSync()){
      debugPrint("加载成功 ${file.path}");
      Uint8List bytes = await file.readAsBytes();
      asset = bytes.buffer.asByteData();
    }else{
      debugPrint("加载失败，使用系统路径加载");
      asset = await super.load(key);
    }
    return asset;
  }
}