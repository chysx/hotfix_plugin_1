#import "HotfixPlugin_1Plugin.h"
#if __has_include(<hotfix_plugin_1/hotfix_plugin_1-Swift.h>)
#import <hotfix_plugin_1/hotfix_plugin_1-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "hotfix_plugin_1-Swift.h"
#endif

@implementation HotfixPlugin_1Plugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftHotfixPlugin_1Plugin registerWithRegistrar:registrar];
}
@end
