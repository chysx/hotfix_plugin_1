import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1/patch_asset_bundle.dart';
import 'app_route.dart';

void main() {
  runApp(DefaultAssetBundle(
    bundle: PatchAssetBundle(),
      child: const MyApp()));
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      routes: AppRoute.getRouteList(),
      initialRoute: '/',
    );
  }
}
