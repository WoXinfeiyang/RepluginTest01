package com.fxj.replugintest01_plugin01;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Plugin01Service_01 extends Service {

    private String TAG="Plugin01Service_01";

    public Plugin01Service_01() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG,"Plugin01Service_01.onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"Plugin01Service_01.onStartCommand");
        String action=intent.getAction();
        Toast.makeText(this,"action="+action,Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        Log.d(TAG,"Plugin01Service_01.onBind");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG,"Plugin01Service_01.onUnbind");
        return super.onUnbind(intent);
    }
}
