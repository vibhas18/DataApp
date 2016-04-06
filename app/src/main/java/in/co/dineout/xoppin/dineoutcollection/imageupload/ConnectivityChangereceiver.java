package in.co.dineout.xoppin.dineoutcollection.imageupload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 16/03/16.
 */
public class ConnectivityChangereceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ConnectivityChanged", "Connectivity changed completed");
        if (Utils.isConnected(context)) {
            Intent intent1 = new Intent(context, BackgroundSyncService.class);
//            intent1.setAction("in.co.dineout.xoppin.dineoutcollection.imageupload.BackgroundSyncService");
            context.startService(intent1);
        } else {
            try {
              //  context.stopService(new Intent(context, BackgroundSyncService.class));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
