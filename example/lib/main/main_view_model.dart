import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1_example/app_route.dart';

class MainViewModel extends ChangeNotifier {

  onTapProvide(BuildContext context) {
    Navigator.of(context).pushNamed(AppRoute.testProviderPage.routeName);
  }

  onTapLoadPatch(BuildContext context) {
    Navigator.of(context).pushNamed(AppRoute.patchPage.routeName);
  }
}
