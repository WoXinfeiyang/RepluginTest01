package com.fxj.host01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.qihoo360.replugin.RePlugin;

public class MainActivity extends Activity {

    private Button btnStartPlugin01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartPlugin01=(Button) findViewById(R.id.btn_start_plugin01);
        btnStartPlugin01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RePlugin.startActivity(MainActivity.this, RePlugin.createIntent("plugin01",
                        "com.fxj.replugintest01_plugin01.MainActivity"));
            }
        });
    }
}
