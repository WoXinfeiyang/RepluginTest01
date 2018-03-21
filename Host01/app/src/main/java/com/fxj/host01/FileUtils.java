package com.fxj.host01;

import android.os.Environment;
import android.util.Log;

/**
 * Created by fuxianjin-hj on 2018/2/27.
 */

public class FileUtils {

    private static String TAG="FileUtils";

    public static String getSDCardRootDir(){
        String rootDir=null;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            rootDir=Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        Log.d(TAG,"rootDir="+rootDir);
        return rootDir;
    }

}
