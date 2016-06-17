package in.co.dineout.xoppin.dineoutcollection.imageupload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 16/03/16.
 */
public class BootCompletedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("BootCompletedReceiver", "Boot completed");
        if (Utils.isConnected(context)) {
            DataDatabaseUtils.getInstance(context).markProgressToReady();
            context.startService(new Intent(context, BackgroundSyncService.class));
        }
    }
}
