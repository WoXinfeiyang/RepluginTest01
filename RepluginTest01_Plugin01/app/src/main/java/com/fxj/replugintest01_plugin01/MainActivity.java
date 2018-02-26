package com.fxj.replugintest01_plugin01;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        findViewById(R.id.btn_start_host01_second_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setComponent(new ComponentName(RePlugin.getHostContext().getPackageName(),"com.fxj.host01.SecondActivity"));
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_start_for_result_activity).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ForResultActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.btn_start_plugin01_service01).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Plugin01Service_01.class);
                intent.setAction("Plugin01Service_01.action");
                startService(intent);
            }
        });
    }
}
