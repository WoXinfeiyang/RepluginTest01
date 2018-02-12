package com.fxj.host01;

import android.content.Context;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;

/**
 * Created by fuxianjin-hj on 2018/2/12.
 */

public class BaseApplication extends RePluginApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // FIXME 允许接收rpRunPlugin等Gradle Task，发布时请务必关掉，以免出现问题
        RePlugin.enableDebugger(base, BuildConfig.DEBUG);

    }
}
