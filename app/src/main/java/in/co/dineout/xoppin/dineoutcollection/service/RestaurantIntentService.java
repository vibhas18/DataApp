package in.co.dineout.xoppin.dineoutcollection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.datanetworkmodule.DineoutNetworkManager;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.utils.RestaurantUploadHandler;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class RestaurantIntentService extends Service {

    private static final String ACTION = "in.co.dineout.xoppin.dineoutcollection.utils.UPLOAD";

    private String mRestId;
    private RestaurantUploadHandler mHandler;

    private DineoutNetworkManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        manager = DineoutNetworkManager.newInstance(getBaseContext(), "");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
        mRestId = intent.getStringExtra("REST_ID");
        mHandler = new RestaurantUploadHandler(getApplicationContext(),mRestId,manager);

        mHandler.initialize();
            DataDatabaseUtils.getInstance(getBaseContext()).markRestaurantSyncProgress(mRestId);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}


