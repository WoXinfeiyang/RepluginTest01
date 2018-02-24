package com.fxj.replugintest01_plugin01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ForResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_result);

        findViewById(R.id.btn_finish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent data=new Intent();
                data.putExtra("data","data from Plugin01,resultCode is 0x71");
                setResult(0x71,data);
                ForResultActivity.this.finish();
            }
        });
    }

}
