package com.fxj.host01;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends Activity {

    private String TAG="Host01_MainActivity_fxj0212";

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

        findViewById(R.id.btn_start_send_activity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });

    }
}
