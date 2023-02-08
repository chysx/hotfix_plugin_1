
import 'dart:io';
import 'dart:typed_data';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:hotfix_plugin_1/patch.dart';

class PatchAssetBundle extends PlatformAssetBundle {
  String? assetsPath;
  bool isAndroid = true;
  @override
  Future<ByteData> load(String key) async {
    if(isAndroid && assetsPath == null){
      try{
        debugPrint("获取asset资源路径");
        assetsPath = await Patch.getAssetsPath();
      }catch(e){
        isAndroid = false;
      }
    }
    final ByteData? asset;
    debugPrint("加载资源 : $assetsPath/$key");
    if(!isAndroid || assetsPath == "-1") {// -1表示不能加载补丁资源
      debugPrint("直接使用系统路径加载");
      asset = await super.load(key);
    }else {
      File file = File("$assetsPath/$key");
      if(file.existsSync()){
        debugPrint("加载成功 ${file.path}");
        Uint8List bytes = await file.readAsBytes();
        asset = bytes.buffer.asByteData();
      }else{
        debugPrint("加载失败，使用系统路径加载");
        asset = await super.load(key);
      }
    }

    return asset;
  }
}