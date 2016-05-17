package in.co.dineout.xoppin.dineoutcollection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import in.co.dineout.xoppin.dineoutcollection.utils.RestaurantUploadHandler;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class RestaurantIntentService extends Service {

    private static final String ACTION = "in.co.dineout.xoppin.dineoutcollection.utils.UPLOAD";

    private String mRestId;
    private RestaurantUploadHandler mHandler;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mRestId = intent.getStringExtra("REST_ID");
        mHandler = new RestaurantUploadHandler(getApplicationContext(),mRestId);
        mHandler.initialize();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


