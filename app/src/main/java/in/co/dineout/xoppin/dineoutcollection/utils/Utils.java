package in.co.dineout.xoppin.dineoutcollection.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by suraj on 03/02/16.
 */
public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    public static String getStringVal(JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getString(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getJsonObject(JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getJSONObject(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONArray getJsonArray(JSONObject jsonObject, String key) {
        try {
            return jsonObject.has(key) && !jsonObject.isNull(key) ? jsonObject.getJSONArray(key) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static boolean isConnectedWithToast(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        boolean networkAvailable = (info != null && info.isConnected());
        if (!networkAvailable) {
            Toast.makeText(context, "Internet not available", Toast.LENGTH_SHORT).show();
        }
        return networkAvailable;
    }

    public static String getStringFromRawFile(Context context, int rawId) {
        String jsonString = null;
        try {
            InputStream is = context.getResources().openRawResource(rawId);
            int length = is.available();
            byte[] data = new byte[length];
            is.read(data);
            jsonString = new String(data);
        } catch (Exception e1) {
            Log.e(TAG, "Error loading json file! ", e1);
        }
        return jsonString;
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    public static Date getFormattedDate(String hhMMSS) {

        DateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        try {
            return sdf.parse(hhMMSS);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static String cleanIt(String data) {
        return data.toLowerCase(Locale.ENGLISH).replaceAll(" ", "_").replaceAll("&", "and").replaceAll("/", "_");
    }

    public static String getMapImageUrl(double latitude, double longitude, String charForPointer) {
        if (TextUtils.isEmpty(charForPointer)) {
            charForPointer = "D";
        }
        return "https://maps.googleapis.com/maps/api/staticmap?center="
                + latitude + "," + longitude +
                "&zoom=15&size=520x260&markers=color:red%7Clabel:" + charForPointer + "%7C"
                + latitude + "," + longitude;
    }



    //method to validate you mobile number, if it is valid it will return true
    public static boolean isValidMobileValid(String mobileNo) {
    // mobile number should be 10 digit
        boolean is_mob_number=false;
        Pattern pattern = Pattern.compile("\\d{10}");
        Matcher matchr = pattern.matcher(mobileNo.trim());
        if (matchr.matches()) {
            is_mob_number = true;
        }
        return is_mob_number;
    }
}
