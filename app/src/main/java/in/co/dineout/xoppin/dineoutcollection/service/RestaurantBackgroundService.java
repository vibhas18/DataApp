package in.co.dineout.xoppin.dineoutcollection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.datanetworkmodule.DineoutNetworkManager;

import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.utils.RestaurantUploadHandler;

/**
 * Created by prateek.aggarwal on 5/16/16.
 */
public class RestaurantBackgroundService extends Service {


   private DineoutNetworkManager manager;
    @Override
    public void onCreate() {
        super.onCreate();

        manager = DineoutNetworkManager.newInstance(getBaseContext(), "");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        List<RestaurantDetailsModel> models = DataDatabaseUtils.getInstance(getBaseContext()).getUnsynedRestaurant();
        initiateSyncing(models);
        return START_STICKY;
    }

    private synchronized void initiateSyncing(List<RestaurantDetailsModel> model){

        if(model != null && model.size()>0){

            for(RestaurantDetailsModel detailsModel : model){

                RestaurantUploadHandler handler =
                        new RestaurantUploadHandler(getBaseContext(),detailsModel.getRestaurantId(),manager);
                Log.d("Background","Name ===== >> "+detailsModel.getProfile_name());
                if(detailsModel.getMode() == DataDatabaseUtils.SYNC_READY){
                        handler.initialize();
                    DataDatabaseUtils.getInstance(getBaseContext()).
                            markRestaurantSyncProgress(detailsModel.getRestaurantId());
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
