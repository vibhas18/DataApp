package in.co.dineout.xoppin.dineoutcollection.helper;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.model.MreModel;

/**
 * Created by suraj on 03/02/16.
 */
public class LoginHelper {
    private static final String TAG = LoginHelper.class.getSimpleName();

    private static final String KEY_MRE_DETAILS = "KEY_MRE_DETAILS";
    private static final String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";

    public static MreModel parseUser(Context context, String userJsonString) {
        try {
            return ObjectMapperFactory.getObjectMapper()
                    .readValue(userJsonString, MreModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveLoginState(Context context, boolean loginState) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(KEY_IS_LOGGED_IN, loginState).commit();
    }

    public static boolean isLoggedIn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void saveUser(Context context, String userString) {
        saveUser(context, parseUser(context, userString));
    }


    public static void saveUser(Context context, MreModel mreModel) {
        try {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putString(KEY_MRE_DETAILS, ObjectMapperFactory.getObjectMapper().writeValueAsString(mreModel)).commit();

            saveLoginState(context, true);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void logout(Context context) {
        saveLoginState(context, false);
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(KEY_MRE_DETAILS).commit();
    }

    public static MreModel getSavedUser(Context context) {

        if (!isLoggedIn(context)) {
            Log.i(TAG, "user is not logged in");
            return null;
        }

        try {
            return ObjectMapperFactory.getObjectMapper()
                    .readValue(
                            PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_MRE_DETAILS, ""),
                            MreModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static MreModel user = null;

    public static MreModel getUser(Context context) {
        if (null == user && isLoggedIn(context)) {
            user = getSavedUser(context);
        }
        return user;
    }

    public static Map<String, String> getDefaultHeaders(Context context) {
        if (!isLoggedIn(context)) {
            return null;
        }

        Map<String, String> map = new HashMap<>(2);
        map.put("authID", String.valueOf(getUser(context).getUserId()));//2554dab34e88844c9b11f51ea8a3141d
        map.put("authToken", getUser(context).getUserToken());
//        map.put("authID", "1");
//        map.put("authToken", "2554dab34e88844c9b11f51ea8a3141d");
        return map;
    }
}
