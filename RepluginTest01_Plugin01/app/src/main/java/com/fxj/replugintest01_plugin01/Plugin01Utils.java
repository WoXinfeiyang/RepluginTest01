package com.fxj.replugintest01_plugin01;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by fuxianjin-hj on 2018/2/26.
 */

public class Plugin01Utils {

    public static void showMsg(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
