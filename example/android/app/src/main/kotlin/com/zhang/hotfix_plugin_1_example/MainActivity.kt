package com.zhang.hotfix_plugin_1_example

import com.zhang.hotfix_plugin_1.patch.PatchLoader
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterShellArgs

class MainActivity: FlutterActivity() {
    override fun getFlutterShellArgs(): FlutterShellArgs {
        val flutterShellArgs = super.getFlutterShellArgs()
        PatchLoader.loadIfNeed(this, flutterShellArgs)
        return flutterShellArgs
    }
}
