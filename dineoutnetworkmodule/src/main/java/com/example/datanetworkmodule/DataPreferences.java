package com.example.datanetworkmodule;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.preference.PreferenceManager;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by suraj on 03/02/16.
 */
public class DataPreferences {

    private static String PARAM_CRE_ID = "cre_id";
    private static String PARAM_CRE_FIRST_NAME = "cre_first_name";
    private static String PARAM_CRE_LAST_NAME = "cre_last_name";
    private static String PARAM_CRE_PHONE = "cre_phone";
    private static String PARAM_CRE_EMAIL = "cre_email";
    private static String PARAM_AUTH_TOKEN = "auth_token";
    private static String PARAM_ASSIGNED_CITY = "assigned_city";
    private static String PARAM_LAT = "lat";
    private static String PARAM_LNG = "lng";
    private static final String TAG = DataPreferences.class.getSimpleName();

    private static final String KEY_MRE_DETAILS = "KEY_MRE_DETAILS";
    private static final String KEY_IS_LOGGED_IN = "KEY_IS_LOGGED_IN";



    private static SharedPreferences getPreference(Context context){
       return PreferenceManager.getDefaultSharedPreferences(context);
    }
    public static void saveLoginState(Context context, boolean loginState) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(KEY_IS_LOGGED_IN, loginState).commit();
    }

    public static boolean isLoggedIn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public static void saveUser(Context context, String userString) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(KEY_MRE_DETAILS,userString).commit();

        saveLoginState(context, true);

    }



    public static void logout(Context context) {
        saveLoginState(context, false);
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(KEY_MRE_DETAILS).commit();
    }
//
//    "user_id": 13,
//            "first_name": "a21",
//            "last_name": "",
//            "email": "a@100.com",
//            "contact_email": "a@100.com",
//            "assigned_city": "Mumbai",
//            "contact_number": "9122312121",

    public static String getUserId(Context context){
        return  getPreference(context).getString(PARAM_CRE_ID,null);
    }

    public static void saveUser(Context context,JSONObject userJsonObject){

        if (userJsonObject != null && context != null) {
            SharedPreferences.Editor edit = getPreference(context).edit();

            edit.putString(PARAM_CRE_ID, userJsonObject.optString("user_id"));
            edit.putString(PARAM_CRE_FIRST_NAME, userJsonObject.optString("first_name"));
            edit.putString(PARAM_CRE_LAST_NAME, userJsonObject.optString("last_name"));
            edit.putString(PARAM_CRE_EMAIL, userJsonObject.optString("email"));
            edit.putString(PARAM_CRE_PHONE, userJsonObject.optString("contact_number"));
            edit.putString(PARAM_AUTH_TOKEN, userJsonObject.optString("user_token"));


            edit.commit();
        }
    }

    public static String getFirstName(Context context){
        return  getPreference(context).getString(PARAM_CRE_FIRST_NAME,"");
    }

    public static String getLastName(Context context){
        return  getPreference(context).getString(PARAM_CRE_LAST_NAME,"");
    }

    public static String getEmail(Context context){
        return  getPreference(context).getString(PARAM_CRE_EMAIL,"");
    }

    public static String getPhone(Context context){
        return  getPreference(context).getString(PARAM_CRE_PHONE,"");
    }

    public static String getToken(Context context){
        return  getPreference(context).getString(PARAM_AUTH_TOKEN,"");
    }


    public static HashMap<String, String> getDefaultHeaders(Context context) {
        if (getUserId(context) == null) {
            return new HashMap<String,String>();
        }

        HashMap<String, String> map = new HashMap<>(2);
        map.put("authID", getUserId(context));//2554dab34e88844c9b11f51ea8a3141d
        map.put("authToken",getToken(context));
//        map.put("authID", "1");
//        map.put("authToken", "2554dab34e88844c9b11f51ea8a3141d");
        return map;
    }

    public static String getAssignedCity(Context context){
        return getPreference(context).getString(PARAM_ASSIGNED_CITY,"");
    }

    public static void updateCurrentLocation(Context context,Location current){

        if(context != null && current != null){
            SharedPreferences.Editor editor = getPreference(context).edit();
            editor.putString(PARAM_LAT,current.getLatitude()+"");
            editor.putString(PARAM_LNG,current.getLongitude()+"");
            editor.commit();
        }
    }

    public static String getCurrentLocationLat(Context context){

        if(context != null)
            return getPreference(context).getString(PARAM_LAT,"0.0");

        return "0.0";
    }

    public static String getCurrentLocationLong(Context context){

        if(context != null)
            return getPreference(context).getString(PARAM_LNG,"0.0");

        return "0.0";
    }
}
