import 'package:flutter/services.dart';
import 'package:hotfix_plugin_1/patch_info.dart';
import 'package:hotfix_plugin_1/patch_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

abstract class NativePlatformInterface extends PlatformInterface {
  /// Constructs a HotfixPlugin_1Platform.
  NativePlatformInterface() : super(token: _token);

  static final Object _token = Object();

  static NativePlatformInterface _instance = PatchMethodChannel();

  /// The default instance of [NativePlatformInterface] to use.
  ///
  /// Defaults to [MethodChannelHotfixPlugin_1].
  static NativePlatformInterface get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [NativePlatformInterface] when
  /// they register themselves.
  static set instance(NativePlatformInterface instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  ///flutter调用原生 通道消息
  ///method 方法  String
  ///params  传递的参数x
  Future runNativeMethod(String method, Map<String, dynamic> params) async {
    try {
      Map<String, dynamic> map = {"params": params};
      var result = await const MethodChannel('native_2').invokeMethod(method, map);
      return Future.value(result);
    } on PlatformException catch (e) {
      return Future.error(e.toString());
    }
  }

  Future<List<PatchInfo>> loadPatch();

  Future applyPatch(PatchInfo patchInfo);
}
