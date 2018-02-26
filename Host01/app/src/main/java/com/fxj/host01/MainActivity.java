package com.fxj.host01;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;
import com.qihoo360.replugin.component.service.PluginServiceClient;

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


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==0x01&&resultCode==0x71){
            Toast.makeText(this,"data:"+data.getStringExtra("data"),Toast.LENGTH_LONG).show();
        }
    }
}
