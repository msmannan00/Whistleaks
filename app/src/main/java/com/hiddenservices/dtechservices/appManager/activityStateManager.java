package com.hiddenservices.dtechservices.appManager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import androidx.core.app.NotificationManagerCompat;
import com.hiddenservices.dtechservices.constants.status;
import org.torproject.android.service.OrbotService;
import org.torproject.android.service.wrapper.orbotLocalConstants;

public class activityStateManager extends Service {

    private final IBinder mLocalBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public activityStateManager getService() {
            return activityStateManager.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mLocalBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        orbotLocalConstants.mAppForceExit = true;
        status.sNoTorTriggered = false;
        NotificationManagerCompat.from(this).cancel(1025);
        NotificationManagerCompat.from(this).cancel(1030);
        Intent mServiceIntent = new Intent(this, OrbotService.class);
        this.stopService(mServiceIntent);

        if (OrbotService.getServiceObject() != null) {
            OrbotService.getServiceObject().onDestroy();
        }

        status.sSettingIsAppStarted = false;
        orbotLocalConstants.mAppStarted = false;

        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        orbotLocalConstants.mAppForceExit = true;
        status.sNoTorTriggered = false;
        NotificationManagerCompat.from(this).cancel(1025);
        NotificationManagerCompat.from(this).cancel(1030);
        Intent mServiceIntent = new Intent(this, OrbotService.class);
        this.stopService(mServiceIntent);
        if (OrbotService.getServiceObject() != null) {
            OrbotService.getServiceObject().onDestroy();
        }

        status.sSettingIsAppStarted = false;
        orbotLocalConstants.mAppStarted = false;


        stopSelf();
        super.onDestroy();
    }
}