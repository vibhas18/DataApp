package in.co.dineout.xoppin.dineoutcollection.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.utils.RestaurantUploadHandler;

/**
 * Created by prateek.aggarwal on 5/16/16.
 */
public class RestaurantBackgroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        List<RestaurantDetailsModel> models = DataDatabaseUtils.getInstance(getBaseContext()).getUnsynedRestaurant();
        initiateSyncing(models);
        return START_STICKY;
    }

    private void initiateSyncing(List<RestaurantDetailsModel> model){

        if(model != null && model.size()>0){

            for(RestaurantDetailsModel detailsModel : model){

                new RestaurantUploadHandler(getBaseContext(),detailsModel.getRestaurantId()).initialize();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
