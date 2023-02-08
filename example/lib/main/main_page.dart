import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1_example/provider/lib/provider.dart';
import 'package:hotfix_plugin_1_example/provider/lib/util.dart';

import 'main_view_model.dart';

class MainPage extends StatelessWidget {
  const MainPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => MainViewModel(),
      builder: (context) => _buildPage(context),
    );
  }

  Widget _buildPage(BuildContext context) {
    MainViewModel model = ProviderUtil.of(context);
    return Scaffold(
        appBar: AppBar(title: const Text('主界面')),
        body: Stack(
          children: [
            Image.asset('asset/image/login/pic_dlbj@2x.png'),
            Column(
              children: [
                GestureDetector(
                    onTap: () {
                      model.onTapProvide(context);
                    },
                    child: _makeItem('test provide')),
                GestureDetector(
                    onTap: () {
                      model.onTapLoadPatch(context);
                    },
                    child: _makeItem('test load patch')),
                Image.asset('asset/image/login/pic_zxzh@2x.png'),
              ],
            ),
          ],
        ));
  }

  Widget _makeItem(String content) {
    return Container(
      margin: const EdgeInsets.only(left: 20, right: 20, top: 20),
      height: 160,
      alignment: Alignment.center,
      decoration: const BoxDecoration(
          color: Colors.yellow,
          borderRadius: BorderRadius.all(Radius.circular(8))),
      child: Text(
        content,
        style: const TextStyle(fontSize: 18, color: Colors.white),
      ),
    );
  }


}
