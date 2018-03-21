package com.fxj.host01;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.component.service.PluginServiceClient;
import com.qihoo360.replugin.model.PluginInfo;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends Activity {

    private String TAG="Host01_MainActivity_fxj0212";
    private ServiceConnection conn;


    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_start_plugin01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Context plugin01Context=RePlugin.fetchContext("plugin01");
//                String msg_plugin01Context="获取到的插件plugin01的Context:"+plugin01Context;
//                Toast.makeText(getApplicationContext(),msg_plugin01Context,Toast.LENGTH_LONG).show();
//                if(BuildConfig.DEBUG){
//                    Log.d(TAG,msg_plugin01Context);
//                }

                /*启动插件的Activity*/
                RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("plugin01",
                        "com.fxj.replugintest01_plugin01.MainActivity"));
            }
        });

        findViewById(R.id.btn_start_plugin01_for_result).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                /*通过startActivityForResult的方式启动插件的Activity*/
                RePlugin.startActivityForResult(MainActivity.this,RePlugin.createIntent("plugin01",
                        "com.fxj.replugintest01_plugin01.ForResultActivity"),0x01);
            }
        });

        findViewById(R.id.btn_start_send_activity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_plugin01_service01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=RePlugin.createIntent("plugin01","com.fxj.replugintest01_plugin01.Plugin01Service_01");
                intent.setAction("Plugin01Service_01.action");
                PluginServiceClient.startService(MainActivity.this,intent);
            }
        });


        conn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d(TAG,"onServiceConnected");
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d(TAG,"onServiceDisconnected");
            }
        };

        findViewById(R.id.btn_bind_plugin01_service01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PluginServiceClient.bindService(MainActivity.this,RePlugin.createIntent("plugin01","com.fxj.replugintest01_plugin01.Plugin01Service_01"),conn,BIND_AUTO_CREATE);
            }
        });


        findViewById(R.id.btn_use_plugin01_method).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClassLoader classLoader=RePlugin.fetchClassLoader("plugin01");/*获取插件的ClassLoader*/

                if(classLoader==null){
                    Toast.makeText(MainActivity.this,"not install plugin01",Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    Class clz =classLoader.loadClass("com.fxj.replugintest01_plugin01.Plugin01Utils");
                    Method showMsgMethod=clz.getDeclaredMethod("showMsg",Context.class,String.class);
                    showMsgMethod.invoke(null,MainActivity.this,"宿主调用插件Plugin01中的方法");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        });

        findViewById(R.id.btn_use_layout_from_plugin01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res=RePlugin.fetchResources("plugin01");/*获取插件的Resources对象*/
                /*从Resources对象中获取资源文件Id,注意使用Resources.getIdentifier方法时传入参数的格式*/
                int resId=res.getIdentifier("com.fxj.replugintest01_plugin01:layout/layout_from_plugin01",null,null);
                if(resId==0){
                    Toast.makeText(MainActivity.this,"插件中的资源文件不存在",Toast.LENGTH_LONG).show();
                    return;
                }
                /*通过布局加载器得到View对象*/
                View contentView=LayoutInflater.from(RePlugin.fetchContext("plugin01")).inflate(resId,null);

                Dialog dialog=new Dialog(MainActivity.this);
                dialog.setContentView(contentView);
                dialog.show();
            }
        });

        final String plugin02Name="plugin02.apk";

        findViewById(R.id.btn_install_plugin02_from_sd).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                pluginInfo = RePlugin.install("/sdcard/plugin02.apk");
//                Log.d(TAG,"PluginInfo="+ pluginInfo);
                final ProgressDialog pd=ProgressDialog.show(MainActivity.this,"Install……","Please waite……",true,true);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        installExternalPluginFromSDCard(Environment.getExternalStorageDirectory().getAbsolutePath(),"plugin02.apk");
                        pd.dismiss();
                    }
                }, 1000);

            }
        });

        findViewById(R.id.btn_start_plugin02).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RePlugin.startActivity(MainActivity.this,RePlugin.createIntent(/*pluginInfo!=null?pluginInfo.getName():*/"com.fxj.replugintest01_plugin02","com.fxj.replugintest01_plugin02.MainActivity"));
            }
        });

    }

    private void installExternalPluginFromSDCard(String filePath,String fileName){
        if(TextUtils.isEmpty(filePath)||TextUtils.isEmpty(fileName)){
            Toast.makeText(MainActivity.this,"参数错误!",Toast.LENGTH_LONG).show();
        }
        Log.d(TAG,"filePath="+filePath+",fileName="+fileName);
        String apkFilePath=filePath+File.separator+fileName;
        File apkFile=new File(apkFilePath);
        if(!apkFile.exists()){
            String msg="在SD卡上的apk文件不存在!";
            Log.d(TAG,msg);
            Toast.makeText(MainActivity.this,msg,Toast.LENGTH_LONG).show();
            return;
        }

        PluginInfo info =RePlugin.install(apkFile.getAbsolutePath());
        if(info!=null){
            Log.d(TAG,"外置插件安装成功!APK存放目录:"+info.getApkDir()+",APK存放的文件信息:"+info.getApkFile());
            Toast.makeText(MainActivity.this,"外置插件安装成功!",Toast.LENGTH_LONG).show();
        }else if(info==null){
            Log.d(TAG,"外置插件安装失败!");
            Toast.makeText(MainActivity.this,"外置插件安装失败!",Toast.LENGTH_LONG).show();
            return;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0x01&&resultCode==0x71){
            Toast.makeText(this,"data:"+data.getStringExtra("data"),Toast.LENGTH_LONG).show();
        }
    }
}
