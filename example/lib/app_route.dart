import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1_example/patch/patch_page.dart';
import 'package:hotfix_plugin_1_example/provider/test/test_provider_page.dart';

import 'main/main_page.dart';

class AppRoute {
  static List<BaseRouteItem> get _initRouteList => [
    root,
    mainPage,
    testProviderPage,
    patchPage
  ];

  static Map<String, WidgetBuilder> getRouteList() {
    Map<String, WidgetBuilder> map = {};
    for (var element in _initRouteList) {
      map[element.routeName] = element.widgetBuilder;
    }
    return map;
  }

  static BaseRouteItem root =
  BaseRouteItem("/", (context, {arguments}) {
    return const MainPage();
  });

  static BaseRouteItem mainPage =
  BaseRouteItem("/mainPage", (context, {arguments}) {
    return const MainPage();
  });

  static BaseRouteItem patchPage =
  BaseRouteItem("/patch/patchPage", (context, {arguments}) {
    return const PatchPage();
  });

  static BaseRouteItem testProviderPage =
  BaseRouteItem("/provider/testProviderPage", (context, {arguments}) {
    return const TestProviderPage();
  });
}

///路由
class BaseRouteItem {
  ///路由名称
  final String routeName;

  ///页面构造器
  final WidgetBuilder widgetBuilder;
  final String? params;

  BaseRouteItem(
      this.routeName,
      this.widgetBuilder, {
        this.params,
      }) : assert(
  routeName != "",
  '路由名称不能为空',
  );

  @override
  bool operator ==(Object other) {
    if (other is BaseRouteItem) {
      if (other.routeName == routeName) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }

  @override
  int get hashCode => super.hashCode;


}