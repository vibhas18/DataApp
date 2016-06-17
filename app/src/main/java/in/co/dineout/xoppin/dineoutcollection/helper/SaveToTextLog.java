package in.co.dineout.xoppin.dineoutcollection.helper;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;

/**
 * Created by suraj on 15/03/16.
 */
public class SaveToTextLog {

    public SaveToTextLog() {
    }

    private static boolean checkForRoot() {
        File folder = new File(Environment.getExternalStorageDirectory() + "/DineoutCollection");
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        if (success) {
            // Do something on success
            return true;
        } else {
            // Do something else on failure
            return false;
        }
    }

    private static boolean checkForHotelFolder(String restaurantName) {

        if (checkForRoot()) {
            File folder = new File(Environment.getExternalStorageDirectory() + "/DineoutCollection/" + restaurantName);
            boolean success = true;
            if (!folder.exists()) {
                success = folder.mkdir();
            }
            if (success) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void saveLogData(String restaurantName, String logs, Context context) {

        try {
            if (!TextUtils.isEmpty(restaurantName)) {
                restaurantName = restaurantName.replaceAll("/", "-");
                restaurantName = restaurantName.replace(" ", "_");
            }

            if (checkForHotelFolder(restaurantName)) {

                Date date = new Date();
                String filename = "logs_" + date.toString() + ".txt";

                String data = date.toString() + "\n:" + logs + "\n\n\n\n\n";

                Log.i("prateek", "saving in log " + data);

                FileWriter writer = new FileWriter(new File(Environment.getExternalStorageDirectory() + "/DineoutCollection/" + restaurantName, filename));
                writer.append(data);
                writer.flush();
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
