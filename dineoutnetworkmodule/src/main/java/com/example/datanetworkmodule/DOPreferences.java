package com.example.datanetworkmodule;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import org.json.JSONObject;

import java.util.HashMap;

public class DOPreferences {
    public static final String PARAM_DINER_EMAIL = "diner_email";
    public static final String FAVORITE_COUNT = "favorite_count";

    public static final String IS_LAUNCHER_FRESH_LAUNCH = "fresh_launch";
    public static final String PARAM_DINER_IS_DO_PLUS_MEMBER = "diner_ref_code";

    private static final String PREFS_NAME = "DineOut_Shared_Preference";

    private static String CURRENT_LAT = "current_lat";
    private static String CURRENT_LNG = "current_lng";
    private static String E_LAT = "e_lat";
    private static String E_LNG = "e_lng";
    private static String IS_AUTO_MODE = "is_auto_mode";
    private static String SUGGESTION = "suggestion";
    private static String SEARCH_FILTER_BY_CITY = "by_city";
    private static String SEARCH_FILTER_ARR_LOCAREA = "arr_locarea";
    private static String SEARCH_FILTER_ARR_AREA = "arr_area";
    private static String IS_FIRST_START = "is_first_start";
    private static String PLAY_STORE_RATE_REVIEW = "play_store_rate_review";
    private static String CITY_ID = "dineout_city_id";
    private static String CITY_NAME = "dineout_city_name";
    private static String AREA_NAME = "dineout_area_name";
    private static String LOCALITY_NAME = "dineout_locality_name";
    private static String REVIEWED_BOOKING_ID = "dineout_reviewed_booking_id";
    private static String PAYTM_LINKED_NUMBER = "paytm_linked_number";
    private static String PAYTM_AMOUNT = "paytm_amount";
    private static String AUTH_KEY = "dineout_auth_key";
    private static String DEVICE_ID = "device_id";
    private static String IMEI_ID="imei";
    private static String GOOGLE_AD_ID = "google_ad_id";
    private static String IS_DISCLAIMER_ACCEPTED = "is_disclaimer_accepted";
    private static String RECENT_SEARCH_LOCATION = "recent_search_location";
    private static String DO_APP_VERSION = "do_app_version";
    private static String PARAM_DINER_ID = "diner_id";
    private static String PARAM_DINER_FIRST_NAME = "diner_first_name";
    private static String PARAM_DINER_LAST_NAME = "diner_last_name";
    private static String PARAM_DINER_PHONE = "diner_phone";
    private static String PARAM_DINER_REF_CODE = "diner_ref_code";
    private static String PARAM_DINER_PROFILE_IMG = "diner_profile_image";
    private static String PARAM_DINER_IS_NEW_USER = "diner_new_user";
    private static String PARAM_DINER_DATE_OF_JOINING = "diner_d_o_j";
    private static String PARAM_DINER_TOTAL_SAVINGS = "diner_tot_savings";
    private static String PARAM_BOOKING_FLAG = "booking_flag";
    private static String PARAM_VIEW_PAYMENT_POPUP = "diner_popup";

    private static String SENT_TOKEN_TO_SERVER = "sent_token_to_server";
    private static String GCM_REGISTRATION_TOKEN = "GCM_REGISTRATION_TOKEN";

    private static String BOOKING_SLOT_INTERVAL = "booking_slot_interval";
    private static String BOOKING_SHARE_MESSAGE = "booking_share_message";
    private static String FORCE_UPDATE_TITLE = "force_update_title";
    private static String FORCE_UPDATE_SUB_TITLE = "force_update_sub_title";
    private static String DOPLUS_CARD_BACKGROUND = "doplus_card_background";
    private static String PAYMENT_BOOKING_PENDING_MESSAGE = "booking_pending_message";
    private static String PAYMENT_NOT_AVAILABLE_MESSAGE = "payment_not_available";

    /*private static String IS_REGISTRAR_ID_SEND = "is_reg_id_send";
    private static String GCM_REGISTRAR_ID = "gcm_registrar_id";*/

    /*public static String getGCMRegistrarId(Context context) {
        return getSharedPreferences(context).getString(GCM_REGISTRAR_ID, "");
    }

    public static void setGCMRegistrationId(Context context, String regId) {
        getSharedPreferences(context).edit().putString(GCM_REGISTRAR_ID, regId).commit();
    }

    public static boolean isRegistrarIdSend(Context context) {
        return getSharedPreferences(context).getBoolean(IS_REGISTRAR_ID_SEND, false);
    }

    public static void setIsRegistrarIdSend(Context context, Boolean isRegIdSend) {
        getSharedPreferences(context).edit().putBoolean(IS_REGISTRAR_ID_SEND, isRegIdSend).commit();
    }*/

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setSentTokenToServer(Context context, boolean hasSent) {
        getSharedPreferences(context).edit().putBoolean(SENT_TOKEN_TO_SERVER, hasSent).apply();
    }

    public static boolean getSentTokenToServer(Context context) {
        return getSharedPreferences(context).getBoolean(SENT_TOKEN_TO_SERVER, false);
    }

    public static void setGcmRegistrationToken(Context context, String registrationToken) {
        getSharedPreferences(context).edit().putString(GCM_REGISTRATION_TOKEN, registrationToken).apply();
    }

    public static String getGcmRegistrationToken(Context context) {
        return getSharedPreferences(context).getString(GCM_REGISTRATION_TOKEN, "");
    }

    public static void setFirstLaunch(Context context, boolean isFirstLaunching) {
        getSharedPreferences(context).edit().putBoolean(IS_FIRST_START, isFirstLaunching).commit();
    }

    public static boolean checkFirstLaunch(Context context) {
        return getSharedPreferences(context).getBoolean(IS_FIRST_START, true);
    }

    public static String getDeviceId(Context context) {
        return getSharedPreferences(context).getString(DEVICE_ID, "");
    }

    public static void setDeviceId(Context context, String deviceId) {
        getSharedPreferences(context).edit().putString(DEVICE_ID, deviceId).commit();
    }

    public static String getImei(Context context) {
        return getSharedPreferences(context).getString(IMEI_ID, "");
    }

    public static void setImei(Context context, String imei) {
        getSharedPreferences(context).edit().putString(IMEI_ID, imei).commit();
    }

    public static String getGoogleAdId(Context context) {
        return getSharedPreferences(context).getString(GOOGLE_AD_ID, "");
    }

    public static void setGoogleAdId(Context context, String googleAdId) {
        getSharedPreferences(context).edit().putString(GOOGLE_AD_ID, googleAdId).commit();
    }

    public static void setReviewedBookingId(Context context, String bookingId) {
        getSharedPreferences(context).edit().putString(REVIEWED_BOOKING_ID, bookingId).commit();
    }

    public static String getReviewedBookingId(Context context) {
        return getSharedPreferences(context).getString(REVIEWED_BOOKING_ID, "-1");
    }

    public static String getDOVersion(Context context) {
        return getSharedPreferences(context).getString(DO_APP_VERSION, null);
    }

    public static void setDOVersion(Context context, String timesCityVersion) {
        getSharedPreferences(context).edit().putString(DO_APP_VERSION, timesCityVersion).commit();
    }

    public static void setAuthKey(Context context, String authkey) {
        getSharedPreferences(context).edit().putString(AUTH_KEY, authkey).commit();
    }

    public static String getAuthKey(Context context) {
        return getSharedPreferences(context).getString(AUTH_KEY, "");
    }

    public static boolean showPlayStoreDialog(Context context) {
        return getSharedPreferences(context).getBoolean(PLAY_STORE_RATE_REVIEW, true);
    }

    public static void setPlayStoreDialogStatus(Context context, boolean status) {
        getSharedPreferences(context).edit().putBoolean(PLAY_STORE_RATE_REVIEW, status).commit();
    }

    public static void setPayTmDetail(Context context, String number, String amt) {

        SharedPreferences.Editor editor = getSharedPreferences(context).edit();

        if (!TextUtils.isEmpty(number)) {
            editor.putString(PAYTM_LINKED_NUMBER, number);
        }

        if (!TextUtils.isEmpty(amt)) {
            editor.putString(PAYTM_AMOUNT, amt);
        }

        editor.commit();
    }

    public static String getPaytmLinkedNumber(Context context) {
        return getSharedPreferences(context).getString(PAYTM_LINKED_NUMBER, "");
    }

    public static String getPaytmAmount(Context context) {
        return getSharedPreferences(context).getString(PAYTM_AMOUNT, "0");
    }

    public static void setDisclaimerAccepted(Context context, boolean isDisclaimerAccepted) {
        getSharedPreferences(context).edit().putBoolean(IS_DISCLAIMER_ACCEPTED, isDisclaimerAccepted).commit();
    }

    public static boolean isDisclaimerAccepted(Context context) {
        return getSharedPreferences(context).getBoolean(IS_DISCLAIMER_ACCEPTED, false);
    }

    public static void setBookingFlag(Context context, boolean isBookingSuccessful) {
        getSharedPreferences(context).edit().putBoolean(PARAM_BOOKING_FLAG, isBookingSuccessful).commit();
    }

    public static boolean getBookingFlag(Context context) {
        return getSharedPreferences(context).getBoolean(PARAM_BOOKING_FLAG, false);
    }

    public static void setELatitude(Context context, String eLatitude) {
        getSharedPreferences(context).edit().putString(E_LAT, eLatitude).commit();
    }

    public static String getELatitude(Context context) {
        return getSharedPreferences(context).getString(E_LAT, null);
    }

    public static void setELongitude(Context context, String eLongitude) {
        getSharedPreferences(context).edit().putString(E_LNG, eLongitude).commit();
    }

    public static String getELongitude(Context context) {
        return getSharedPreferences(context).getString(E_LNG, null);
    }

    public static void saveLatAndLong(Context context, String latitude, String longitude) {
        getSharedPreferences(context).edit().putString(CURRENT_LAT, latitude).commit();
        getSharedPreferences(context).edit().putString(CURRENT_LNG, longitude).commit();
    }

    public static void putSearchedLocation(Context context, String recent) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(RECENT_SEARCH_LOCATION, recent);
        editor.commit();
    }

    public static String getRecentLocation(Context context) {
        return getSharedPreferences(context).getString(RECENT_SEARCH_LOCATION, null);
    }

    public static void saveStringKeyValue(Context context, String key, String value) {
        getSharedPreferences(context).edit().putString(key, value).commit();
    }

    public static String getStringValue(Context context, String key) {
        if (context == null)
            return "";
        return getSharedPreferences(context).getString(key, "");
    }

    public static void saveDinerCredentials(Context context, JSONObject userJsonObject) {
        if (userJsonObject != null && context != null) {
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();

            edit.putString(PARAM_DINER_ID, userJsonObject.optString("d_id"));
            edit.putString(PARAM_DINER_FIRST_NAME, userJsonObject.optString("d_first_name"));
            edit.putString(PARAM_DINER_LAST_NAME, userJsonObject.optString("d_last_name"));
            edit.putString(PARAM_DINER_EMAIL, userJsonObject.optString("d_email"));
            edit.putString(PARAM_DINER_PHONE, userJsonObject.optString("d_phone"));
            edit.putString(PARAM_DINER_REF_CODE, userJsonObject.optString("ref_code"));
            edit.putString(PARAM_DINER_IS_DO_PLUS_MEMBER, userJsonObject.optString("is_do_plus_member"));
            edit.putString(PARAM_DINER_PROFILE_IMG, userJsonObject.optString
                    ("profile_image"));
            edit.putString(PARAM_DINER_IS_NEW_USER, userJsonObject.optString
                    ("new_user"));
            edit.putString(PARAM_DINER_DATE_OF_JOINING, userJsonObject
                    .optString("d_dt"));
            edit.putString(PARAM_DINER_TOTAL_SAVINGS, userJsonObject
                    .optString("total_savings"));

            edit.commit();
        }
    }

    public static String getDinerId(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_ID, "");
    }

    public static String getDinerProfileImage(Context context) {
        return getSharedPreferences(context).getString
                (PARAM_DINER_PROFILE_IMG, null);
    }

    public static String getDinerFirstName(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_FIRST_NAME, "");
    }

    public static String getDinerLastName(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_LAST_NAME, "");
    }

    public static String getDinerTotalSavings(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_TOTAL_SAVINGS, "0");
    }

    public static String getDinerDateOfJoining(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_DATE_OF_JOINING, "");
    }

    public static String getDinerEmail(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_EMAIL, "");
    }

    public static String getDinerPhone(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_PHONE, "");
    }

    public static String getDinerRefCode(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_REF_CODE, "");
    }

    public static String isDinerDoPlusMember(Context context) {
        return getSharedPreferences(context).getString(PARAM_DINER_IS_DO_PLUS_MEMBER, "0");
    }

    public static boolean isDinerNewUser(Context context) {
        String strValue = getSharedPreferences(context).getString(PARAM_DINER_IS_NEW_USER, "");

        return ("1".equals(strValue));
    }

    public static boolean isPaymentPopUpViewed(Context context) {
        return getSharedPreferences(context).getBoolean(PARAM_VIEW_PAYMENT_POPUP, false);
    }

    public static void setPaymentPopUpViewedFlag(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putBoolean(PARAM_VIEW_PAYMENT_POPUP, true);
        edit.commit();
    }

    public static void setDinerNewUser(Context context, String value) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(PARAM_DINER_IS_NEW_USER, value);
        edit.commit();
    }

    public static void setIsDinerDoPlusMember(Context context, String isDOPlusMember) {
        SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString(PARAM_DINER_IS_DO_PLUS_MEMBER, isDOPlusMember);
        editor.commit();
    }

    public static void deleteDinerCredentials(Context context) {
        SharedPreferences.Editor edit = getSharedPreferences(context).edit();
        edit.putString(PARAM_DINER_ID, "");
        edit.putString(PARAM_DINER_FIRST_NAME, "");
        edit.putString(PARAM_DINER_LAST_NAME, "");
        edit.putString(PARAM_DINER_EMAIL, "");
        edit.putString(PARAM_DINER_PHONE, "");
        edit.putString(PARAM_DINER_REF_CODE, "");
        edit.putString(PARAM_DINER_IS_DO_PLUS_MEMBER, "0");
        edit.putString(PARAM_DINER_PROFILE_IMG, "");
        edit.putBoolean(PARAM_DINER_IS_NEW_USER, false);
        edit.putString(PARAM_DINER_DATE_OF_JOINING, "");
        edit.putString(PARAM_DINER_TOTAL_SAVINGS, "");
        edit.commit();
    }

    public static void saveLocationDetails(Context context, JSONObject jsonObject, boolean isAutoMode) {
        if (jsonObject != null && context != null) {
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();

            edit.putBoolean(IS_AUTO_MODE, isAutoMode);
            edit.putString(E_LAT, jsonObject.optString("lat"));
            edit.putString(E_LNG, jsonObject.optString("lng"));
            edit.putString(CITY_ID, jsonObject.optString("city_id"));
            edit.putString(CITY_NAME, jsonObject.optString("city_name"));
            edit.putString(AREA_NAME, jsonObject.optString("area_name"));
            edit.putString(LOCALITY_NAME, jsonObject.optString("location_name"));
            edit.putString(SUGGESTION, jsonObject.optString("suggestion"));

            JSONObject searchFilterJsonObject = jsonObject.optJSONObject("sf");

            if (searchFilterJsonObject != null) {
                edit.putString(SEARCH_FILTER_BY_CITY, searchFilterJsonObject.optString("by_city"));
                edit.putString(SEARCH_FILTER_ARR_LOCAREA, searchFilterJsonObject.optString("arr_locarea"));
                edit.putString(SEARCH_FILTER_ARR_AREA, searchFilterJsonObject.optString("arr_area"));
            }

            edit.commit();
        }
    }

    public static boolean isAutoMode(Context context) {
        return getSharedPreferences(context).getBoolean(IS_AUTO_MODE, false);
    }

    public static void setAutoMode(Context context, boolean status) {
        getSharedPreferences(context).edit().putBoolean(IS_AUTO_MODE, status).commit();
    }

    public static String getCurrentLatitude(Context context) {
        return DOPreferences.getSharedPreferences(context).getString(CURRENT_LAT, null);
    }

    public static void setCurrentLatitude(Context context, String latitude) {
        getSharedPreferences(context).edit().putString(CURRENT_LAT, latitude).commit();
    }

    public static String getCurrentLongitude(Context context) {
        return DOPreferences.getSharedPreferences(context).getString(CURRENT_LNG, null);
    }

    public static void setCurrentLongitude(Context context, String longitude) {
        getSharedPreferences(context).edit().putString(CURRENT_LNG, longitude).commit();
    }

    public static void setCityId(Context context, String cityId) {
        getSharedPreferences(context).edit().putString(CITY_ID, cityId).commit();
    }

    public static String getCityId(Context context) {
        return getSharedPreferences(context).getString(CITY_ID, "");
    }

    public static void setCityName(Context context, String cityName) {
        getSharedPreferences(context).edit().putString(CITY_NAME, cityName).commit();
    }

    public static String getCityName(Context context) {
        return getSharedPreferences(context).getString(CITY_NAME, "");
    }

    public static void setAreaName(Context context, String areaName) {
        getSharedPreferences(context).edit().putString(AREA_NAME, areaName).commit();
    }

    public static String getAreaName(Context context) {
        return getSharedPreferences(context).getString(AREA_NAME, "");
    }

    public static void setLocalityName(Context context, String localityName) {
        getSharedPreferences(context).edit().putString(LOCALITY_NAME, localityName).commit();
    }

    public static String getLocalityName(Context context) {
        return getSharedPreferences(context).getString(LOCALITY_NAME, "");
    }

    public static void setSuggestion(Context context, String suggestion) {
        getSharedPreferences(context).edit().putString(SUGGESTION, suggestion).commit();
    }

    public static String getSuggestion(Context context) {
        return getSharedPreferences(context).getString(SUGGESTION, "");
    }

    public static void setSfByCity(Context context, String byCity) {
        getSharedPreferences(context).edit().putString(SEARCH_FILTER_BY_CITY, byCity).commit();
    }

    public static String getSfByCity(Context context) {
        return getSharedPreferences(context).getString(SEARCH_FILTER_BY_CITY, "");
    }

    public static void setSfArrLocarea(Context context, String arrLocarea) {
        getSharedPreferences(context).edit().putString(SEARCH_FILTER_ARR_LOCAREA, arrLocarea).commit();
    }

    public static String getSfArrLocarea(Context context) {
        return getSharedPreferences(context).getString(SEARCH_FILTER_ARR_LOCAREA, "");
    }

    public static void setSfArrArea(Context context, String arrArea) {
        getSharedPreferences(context).edit().putString(SEARCH_FILTER_ARR_AREA, arrArea).commit();
    }

    public static String getSfArrArea(Context context) {
        return getSharedPreferences(context).getString(SEARCH_FILTER_ARR_AREA, "");
    }

    public static HashMap<String, Object> getUserDataJSONObject(Context context) {
        HashMap<String, Object> props = new HashMap<>();

        if (context != null &&
                !TextUtils.isEmpty(DOPreferences.getDinerEmail(context.getApplicationContext()))) {

            props.put("email", DOPreferences.getDinerEmail(context.getApplicationContext()));
            props.put("name", DOPreferences.getDinerFirstName(context) + " " +
                    DOPreferences.getDinerLastName(context));
            props.put("avatarUrl", DOPreferences.getDinerProfileImage(context));
            props.put("phone", DOPreferences.getDinerPhone(context));
            props.put("isDineoutPlusMember", DOPreferences.isDinerDoPlusMember(context));
            props.put("dinnerId", DOPreferences.getDinerId(context));
            props.put("cityId", DOPreferences.getCityId(context));
        }

        return props;
    }

    public static void setFavoriteCount(Context context, int count) {
        int totalCount = getSharedPreferences(context).getInt(FAVORITE_COUNT, 0) + count;
        getSharedPreferences(context).edit().putInt(FAVORITE_COUNT, totalCount).commit();
    }

    public static void saveAppConfig(Context context, JSONObject appConfigJsonObject) {
        if (context != null && appConfigJsonObject != null) {
            SharedPreferences.Editor edit = getSharedPreferences(context).edit();

            if (appConfigJsonObject.optJSONObject("booking_config") != null) {
                edit.putString(BOOKING_SLOT_INTERVAL,
                        appConfigJsonObject.optJSONObject("booking_config").optString("booking_slot_interval"));
                edit.putString(BOOKING_SHARE_MESSAGE,
                        appConfigJsonObject.optJSONObject("booking_config").optString("booking_share_message"));
            }

            if (appConfigJsonObject.optJSONObject("force_update_config") != null) {
                edit.putString(FORCE_UPDATE_TITLE,
                        appConfigJsonObject.optJSONObject("force_update_config").optString("title"));
                edit.putString(FORCE_UPDATE_SUB_TITLE,
                        appConfigJsonObject.optJSONObject("force_update_config").optString("sub_title"));
            }

            if (appConfigJsonObject.optJSONObject("doplus_config") != null) {
                edit.putString(DOPLUS_CARD_BACKGROUND,
                        appConfigJsonObject.optJSONObject("doplus_config").optString("doplus_card_background"));
            }

            if (appConfigJsonObject.optJSONObject("payment_config") != null) {
                edit.putString(PAYMENT_BOOKING_PENDING_MESSAGE,
                        appConfigJsonObject.optJSONObject("payment_config").optString("booking_pending_message"));
                edit.putString(PAYMENT_NOT_AVAILABLE_MESSAGE,
                        appConfigJsonObject.optJSONObject("payment_config").optString("payment_not_available"));
            }

            edit.commit();
        }
    }

    public static String getBookingSlotInterval(Context context) {
        return getSharedPreferences(context).getString(BOOKING_SLOT_INTERVAL, "0");
    }

    public static String getBookingShareMessage(Context context) {
        return getSharedPreferences(context).getString(BOOKING_SHARE_MESSAGE, "");
    }

    public static void setLauncherFreshLaunch(Context context, boolean isFreshLaunch) {
        SharedPreferences.Editor pref = getSharedPreferences(context).edit();
        pref.putBoolean(IS_LAUNCHER_FRESH_LAUNCH, isFreshLaunch);
        pref.commit();
    }

    public static boolean isLauncherFreshLaunch(Context context) {
        return getSharedPreferences(context).getBoolean(IS_LAUNCHER_FRESH_LAUNCH, false);
    }

    public static String getForceUpdateTitle(Context context) {
        return getSharedPreferences(context).getString(FORCE_UPDATE_TITLE, "Important app update.");

    }

    public static String getForceUpdateSubTitle(Context context) {
        return getSharedPreferences(context).getString(FORCE_UPDATE_SUB_TITLE, "A new app version is available for update. Please update now to continue using the app!");
    }

    public static String getDoPlusCardBackground(Context context) {
        return getSharedPreferences(context).getString(DOPLUS_CARD_BACKGROUND, "");
    }

    public static String getPaymentBookingPendingMessage(Context context) {
        return getSharedPreferences(context).getString(PAYMENT_BOOKING_PENDING_MESSAGE, "You can pay on a confirmed booking only.");
    }

    public static String getPaymentNotAvailableMessage(Context context) {
        return getSharedPreferences(context).getString(PAYMENT_NOT_AVAILABLE_MESSAGE, "smart pay options is not applicable for this restro");
    }
}



