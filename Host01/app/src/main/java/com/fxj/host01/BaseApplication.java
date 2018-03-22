package com.fxj.host01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.RePluginApplication;
import com.qihoo360.replugin.RePluginCallbacks;
import com.qihoo360.replugin.RePluginConfig;
import com.qihoo360.replugin.RePluginEventCallbacks;
import com.qihoo360.replugin.model.PluginInfo;

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

    /**Replugin "自定义行为"*/
    @Override
    protected RePluginConfig createConfig() {
        RePluginConfig c=new RePluginConfig();

        /*设置插件是否可以使用宿主中的类,true为允许使用,false为不允许,默认值为"关闭,不允许"*/
        c.setUseHostClassIfNotFound(true);

        /*设置Replugin是否对安装的外置插件进行签名校验,true为进行校验,false为不进行校验,默认情况下Replugin会对安装的外置插件进行签名校验*/
        c.setVerifySign(!BuildConfig.DEBUG);

        /*添加插件化框架对外事件的回调接口*/
        c.setEventCallbacks(new HostEventCallbacks(this));

        /*设置安装外置插件的时候是否移动源插件文件,true为允许移动*/
        c.setMoveFileWhenInstalling(false);

        c.setPrintDetailLog(BuildConfig.DEBUG);
        return c;
    }



    class HostEventCallbacks extends RePluginEventCallbacks{
        private String TAG="HostEventCallbacks_fxj0212";

        public HostEventCallbacks(Context context) {
            super(context);
        }

        @Override
        public void onInstallPluginFailed(String path, InstallResult code) {
            /*插件化安装失败会回调此方法*/
            if(BuildConfig.DEBUG){
                Log.d(TAG,"插件安装失败!onInstallPluginFailed:Failed!Path="+path+",InstallResult="+code);
            }
            super.onInstallPluginFailed(path, code);
        }

        @Override
        public void onInstallPluginSucceed(PluginInfo info) {
            if(BuildConfig.DEBUG){
                Log.d(TAG,"插件安装成功!onInstallPluginSucceed:Succeed!PluginInfo="+info);
            }
            super.onInstallPluginSucceed(info);
        }

        @Override
        public void onStartActivityCompleted(String plugin, String activity, boolean result) {
            /*启动插件Activity会回调此方法*/
            if(BuildConfig.DEBUG){
                Log.d(TAG,"插件"+plugin+"中Acitivity:"+activity+"已启动完成,启动结果为:"+result);
            }
            Toast.makeText(getApplicationContext(),"插件"+plugin+"中Acitivity:"+activity+"已启动完成,启动结果为:"+result,Toast.LENGTH_SHORT).show();
            super.onStartActivityCompleted(plugin, activity, result);
        }

        @Override
        public void onPrepareAllocPitActivity(Intent intent) {
            super.onPrepareAllocPitActivity(intent);
        }

        @Override
        public void onPrepareStartPitActivity(Context context, Intent intent, Intent pittedIntent) {
            super.onPrepareStartPitActivity(context, intent, pittedIntent);
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if(BuildConfig.DEBUG){
                Log.d(TAG,"onActivityDestroyed,Activity:"+activity.toString());
            }
            super.onActivityDestroyed(activity);
        }

        @Override
        public void onBinderReleased() {
            super.onBinderReleased();
        }
    }
}
