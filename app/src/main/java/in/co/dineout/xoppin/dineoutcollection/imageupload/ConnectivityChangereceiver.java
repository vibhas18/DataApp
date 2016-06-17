package in.co.dineout.xoppin.dineoutcollection.imageupload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.service.RestaurantBackgroundService;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 16/03/16.
 */
public class ConnectivityChangereceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ConnectivityChanged", "Connectivity changed completed");
        if (Utils.isConnected(context)) {
            DataDatabaseUtils.getInstance(context).markProgressToReady();
            Intent intent1 = new Intent(context, RestaurantBackgroundService.class);
//            intent1.setAction("in.co.dineout.xoppin.dineoutcollection.imageupload.BackgroundSyncService");
            context.startService(intent1);
        } else {
            try {
                context.stopService(new Intent(context, RestaurantBackgroundService.class));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
