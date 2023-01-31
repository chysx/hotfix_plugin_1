import Flutter
import UIKit

public class SwiftHotfixPlugin_1Plugin: NSObject, FlutterPlugin {
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "hotfix_plugin_1", binaryMessenger: registrar.messenger())
    let instance = SwiftHotfixPlugin_1Plugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    result("iOS " + UIDevice.current.systemVersion)
  }
}
