import 'package:flutter/material.dart';
import 'package:hotfix_plugin_1/patch_info.dart';
import 'package:hotfix_plugin_1_example/provider/lib/comsumer.dart';
import 'package:hotfix_plugin_1_example/provider/lib/provider.dart';

import 'patch_view_model.dart';

class PatchPage extends StatelessWidget {
  const PatchPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return ChangeNotifierProvider(
      create: (context) => PatchViewModel(),
      builder: (context) => _buildPage(context),
    );
  }

  Widget _buildPage(BuildContext context) {
    return Consumer<PatchViewModel>(builder: (context, model) {
      return Scaffold(
          appBar: AppBar(title: const Text('补丁界面')),
          body: ListView.builder(
            itemCount: model.patchInfoList.length,
              itemBuilder: (context, index){
                PatchInfo patchInfo = model.patchInfoList[index];
                return GestureDetector(
                  onTap: () {
                    model.onTapCheck(context, patchInfo);
                  },
                    child: _makeItem(model.patchInfoList[index])
                );
              }),
      );
    });
  }

  Widget _makeItem(PatchInfo patchInfo) {
    return Container(
      margin: const EdgeInsets.only(left: 20, right: 20, top: 20),
      padding: const EdgeInsets.only(left: 10, right: 10, top: 10, bottom: 10),
      decoration: const BoxDecoration(
          color: Colors.blue,
          borderRadius: BorderRadius.all(Radius.circular(8))),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Text(
            'baseApkCode: ${patchInfo.baseApkCode}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          ),
          Text(
            'versionCode: ${patchInfo.versionCode}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          ),
          Text(
            'versionName: ${patchInfo.versionName}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          ),
          Text(
            'des: ${patchInfo.des}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          ),
          Text(
            'md5: ${patchInfo.md5}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          ),
          Text(
            'patchPath: ${patchInfo.patchPath}',
            style: const TextStyle(fontSize: 16, color: Colors.white),
          )
        ],
      ),
    );
  }
}
