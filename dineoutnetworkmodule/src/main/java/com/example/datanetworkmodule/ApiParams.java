package com.example.datanetworkmodule;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class ApiParams {

    private static String PARAM_TEMP = "f";
    private static String PARAM_AUTH_KEY = "ak";
    private static String PARAM_DINER_ID = "diner_id";
    private static String PARAM_LOGOUT_DINER_ID = "d_id";
    private static String PARAM_API_URL = "api_url";
    private static String PARAM_ERROR_MESSAGE = "error_msg";
    private static String DEVICE_TYPE = "android";
    private static String PARAM_REG_ID = "reg_id";
    private static String PARAM_DEVICE_TYPE = "device_type";
    private static String PARAM_BOOKING_TIME = "booking_time";
    private static String PARAM_BOOKING_DATE = "booking_date";
    private static String PARAM_UBER_SERVER_TOKEN = "server_token";
    private static String PARAM_UBER_START_LATITUDE = "start_latitude";
    private static String PARAM_UBER_START_LONGITUDE = "start_longitude";
    private static String PARAM_UBER_END_LATITUDE = "end_latitude";
    private static String PARAM_UBER_END_LONGITUDE = "end_longitude";
    private static String PARAM_RES_ID = "res_id";
    private static String PARAM_REST_ID = "rest_id";
    private static String PARAM_RESTAURANT_ID = "restaurant_id";
    private static String PARAM_ACTION = "action";
    private static String PARAM_DATE = "date";
    private static String PARAM_TIME = "time";
    private static String PARAM_KEY_TYPE = "key_type";
    private static String PARAM_UKEY = "ukey";
    private static String PARAM_PRODUCT_ID = "product_id";
    private static String PARAM_CODE = "code";
    private static String PARAM_ADDRESS1 = "address_1";
    private static String PARAM_ADDRESS2 = "address_2";
    private static String PARAM_CITY = "city";
    private static String PARAM_CITY_ID = "city_id";
    private static String PARAM_STATE = "state";
    private static String PARAM_PIN = "pincode";
    private static String PARAM_PROMO = "promocode";
    private static String PARAM_PROVIDER = "provider_id";
    private static String PARAM_PHONE = "phone";
    private static String PARAM_NAME = "name";
    private static String PARAM_FIRST_NAME = "first_name";
    private static String PARAM_LAST_NAME = "last_name";
    private static String PARAM_EMAIL = "email";
    private static String PARAM_MOBILE_NUMBER = "mobile_number";
    private static String PARAM_IMAGE = "image";
    private static String PARAM_UPDATE_IMAGE = "update_image";
    private static String PARAM_PAGE = "page";
    private static String PARAM_LAT = "lat";
    private static String PARAM_LONG = "long";
    private static String PARAM_LNG = "lng";
    private static String PARAM_BY_CITY = "by_city";
    private static String PARAM_ARR_LOCAREA = "arr_locarea";
    private static String PARAM_ARR_AREA = "arr_area";
    private static String PARAM_TIMESTAMP = "ts";
    private static String PARAM_SEARCH_NEEDLE = "search_needle";
    private static String PARAM_OTP = "otp";
    private static String PARAM_PARTY_DATE = "party_date";
    private static String PARAM_TIME_SLOT = "time_slot";
    private static String PARAM_DINER_COUNT = "mg";
    private static String PARAM_FROM_PRICE = "from_price";
    private static String PARAM_TO_PRICE = "to_price";
    private static String PARAM_ARR_PACKAGE_ID = "arr_pkg_ids[]";
    private static String PARAM_DINER_NAME = "diner_name";
    private static String PARAM_DINER_EMAIL = "diner_email";
    private static String PARAM_DINER_PHONE = "diner_phone";
    private static String PARAM_SPECIAL_REQUEST = "spcl_req";
    private static String PARAM_AMOUNT = "amt";
    private static String PARAM_USE_DINEOUT_WALLET = "use_dw";
    private static String PARAM_REF_CODE = "ref_code";
    private static String PARAM_REVIEW_TYPE = "review_type";
    private static String REVIEW_TYPE_GENERIC = "generic";
    private static String REVIEW_TYPE_AUTHENTIC = "authentic";
    private static String PARAM_B_ID = "b_id";
    private static String PARAM_RATING_FOOD = "rating_food";
    private static String PARAM_RATING_SERVICE = "rating_service";
    private static String PARAM_RATING_AMBIENCE = "rating_ambience";
    private static String PARAM_RATING_DESC = "rating_desc";
    private static String PARAM_CITY_NAME = "city_name";
    private static String PARAM_PASSWORD = "password";
    private static String PARAM_TYPE = "type";
    private static String PARAM_ACCESS_TOKEN = "access_token";
    private static String PARAM_LIMIT = "limit";
    private static String PARAM_START = "start";
    private static String PARAM_OFFER_ID = "offer_id";
    private static String PARAM_BOOKING_TYPE = "booking_type";
    private static String PARAM_PEOPLE = "people";
    private static String PARAM_DEVICE_REG_ID = "device_reg_id";
    private static String PARAM_USER_EMAIL = "user_email";
    private static String PARAM_USER_ID = "user_id";
    private static String PARAM_USER_NAME = "user_name";
    private static String PARAM_SORT_BY = "sortby";
    private static String PARAM_RATING_SLAB = "rating_slab";
    private static String PARAM_PRICE_RANGE = "price_range";
    private static String USER_BOOKING_SEGG_GET_PARAM_USER_ID = "user_id";
    private static String PARAM_SOLR_SEARCH_ARR_TAG = "arr_tag[]";
    private static String PARAM_VERIFY_OTP = "otp";
    private static String PARAM_BOOKIG_ID = "booking_id";
    private static String PARAM_GPS_ENABLED = "gps_enabled";

    public static String DOPLUS_TYPE = "doplus";
    public static String BOOKING_TYPE = "booking";
    public static int PAYMENT_TYPE_PAYU = 0;
    public static int PAYMENT_TYPE_MOBIKWIK = 2;
    public static int PAYMENT_TYPE_PAYTM = 1;

    /**
     * This function returns common parameters required to be passed in all requests
     */
    private static HashMap<String, String> getCommonParams() {
        HashMap<String, String> params = new HashMap<>();

        params.put(PARAM_TEMP, "1");

        return params;
    }

    /**
     * This function returns Get Diner Profile API params
     */
    public static HashMap<String, String> getDinerProfileParams(String dinerId, String ak) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        if (!TextUtils.isEmpty(ak))
            params.put(PARAM_AUTH_KEY, ak);

        return params;
    }

    /**
     * This function returns Logout API params
     */
    public static HashMap<String, String> getLogoutParams(String dinerId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_LOGOUT_DINER_ID, dinerId);

        return params;
    }

    /**
     * This function returns Api Error Tracker API params
     */
    public static HashMap<String, String> getApiErrorTrackerParams(String apiUrl, String errorMessage) {
        HashMap<String, String> params = new HashMap<>();

        params.put(PARAM_API_URL, apiUrl);
        params.put(PARAM_ERROR_MESSAGE, errorMessage);

        return params;
    }

    /**
     * This function returns Insert Mobile Id API params
     */
    public static HashMap<String, String> getInsertMobileIdParams(String gcmRegistrationId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_REG_ID, gcmRegistrationId);
        params.put(PARAM_DEVICE_TYPE, DEVICE_TYPE);

        return params;
    }

    /**
     * This function returns Check Paytm Balance API params
     */
    public static HashMap<String, String> getCheckPaytmBalanceParams(String dinerId, String ak) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        if (!TextUtils.isEmpty(ak))
            params.put(AppConstant.DEFAULT_HEADER_AUTH_KEY, ak);

        return params;
    }

    /**
     * This function returns Waiting Time API params
     */
    public static HashMap<String, String> getWaitingTimeParams(String restaurantId, String bookingTime,
                                                               String bookingDate) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_REST_ID, restaurantId);
        params.put(PARAM_BOOKING_TIME, bookingTime);
        params.put(PARAM_BOOKING_DATE, bookingDate);

        return params;
    }

    /**
     * This function returns Cancel Booking API params
     */
    public static HashMap<String, String> getCancelBookingParams(String bookingId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_B_ID, bookingId);

        return params;
    }

    /**
     * This function returns Fetch Uber Price API params
     */
    public static HashMap<String, String> getFetchUberPriceParams(String serverToken, String startLatitude,
                                                                  String startLongitude, String endLatitude,
                                                                  String endLongitude) {
        HashMap<String, String> params = new HashMap<>();

        params.put(PARAM_UBER_SERVER_TOKEN, serverToken);
        params.put(PARAM_UBER_START_LATITUDE, startLatitude);
        params.put(PARAM_UBER_START_LONGITUDE, startLongitude);
        params.put(PARAM_UBER_END_LATITUDE, endLatitude);
        params.put(PARAM_UBER_END_LONGITUDE, endLongitude);

        return params;
    }

    /**
     * This function returns Fetch Uber Cabs Arrival Time API params
     */
    public static HashMap<String, String> getFetchUberCabsArrivalTimeParams(String serverToken, String startLatitude,
                                                                            String startLongitude) {
        HashMap<String, String> params = new HashMap<>();

        params.put(PARAM_UBER_SERVER_TOKEN, serverToken);
        params.put(PARAM_UBER_START_LATITUDE, startLatitude);
        params.put(PARAM_UBER_START_LONGITUDE, startLongitude);

        return params;
    }

    /**
     * This function returns Get Booking Detail API params
     */
    public static HashMap<String, String> getBookingDetailParams(String bookingId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_B_ID, bookingId);

        return params;
    }

    /**
     * This function returns Set Unset Favorite Restaurant API params
     */
    public static HashMap<String, String> getSetUnsetFavoriteRestaurantParams(String restaurantId, String dinerId,
                                                                              String action) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_RESTAURANT_ID, restaurantId);
        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_ACTION, action);

        return params;
    }

    /**
     * This function returns Fetch City Data API params
     */
    public static HashMap<String, String> getFetchCityDataParams() {
        return getCommonParams();
    }

    /**
     * This function returns Active Offers API params
     */
    public static HashMap<String, String> getActiveOffersParams(String restaurantId, String date, String time) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_RES_ID, restaurantId);
        params.put(PARAM_DATE, date);
        params.put(PARAM_TIME, time);

        return params;
    }

    /**
     * This function returns Gen Search API params
     */
    public static HashMap<String, String> getGenSearchParams(String keyType) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_KEY_TYPE, keyType);

        return params;
    }

    /**
     * This function returns Set Is Viewed Notification Tracking API params
     */
    public static HashMap<String, String> getSetIsViewedNotificationTrackingParams(String uKey) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_UKEY, uKey);

        return params;
    }

    /**
     * This function returns Validate Dineout Plus Code API params
     */
    public static HashMap<String, String> getValidateDineoutPlusCodeParams(String productId, String code) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_PRODUCT_ID, productId);
        params.put(PARAM_CODE, code);

        return params;
    }

    /**
     * This function returns Payment Pg Init API params
     */
    public static HashMap<String, String> getPaymentPgInitParams(String dinerId, String address1,
                                                                 String address2, String city,
                                                                 String state, String pinCode,
                                                                 String phone, String productId,
                                                                 String providerId, String name,
                                                                 String promoCode) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_ADDRESS1, address1);
        params.put(PARAM_ADDRESS2, address2);
        params.put(PARAM_CITY, city);
        params.put(PARAM_STATE, state);
        params.put(PARAM_PIN, pinCode);
        params.put(PARAM_PHONE, phone);
        params.put(PARAM_PRODUCT_ID, productId);
        params.put(PARAM_PROVIDER, providerId);
        params.put(PARAM_NAME, name);
        params.put(PARAM_PROMO, promoCode);

        return params;
    }

    /**
     * This function returns Add Invite API params
     */
    public static HashMap<String, String> getAddInviteParams(String name, String mobileNumber, String email) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_NAME, name);
        params.put(PARAM_MOBILE_NUMBER, mobileNumber);
        params.put(PARAM_EMAIL, email);

        return params;
    }

    /**
     * This function returns Doplus Conversion Detail API params
     */
    public static HashMap<String, String> getDoplusConversionDetailParams(String cityId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_ID, cityId);

        return params;
    }

    /**
     * This function returns Update Diner Info API params
     */
    public static HashMap<String, String> getUpdateDinerInfoParams(String dinerId, String firstName, String lastName,
                                                                   String phone, boolean isProfileImageSelected,
                                                                   String image) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        if (firstName != null) {
            params.put(PARAM_FIRST_NAME, firstName);
        }

        if (lastName != null) {
            params.put(PARAM_LAST_NAME, lastName);
        }

        params.put(PARAM_PHONE, phone);

        if (isProfileImageSelected) {
            params.put(PARAM_IMAGE, image);
        }

        params.put(PARAM_UPDATE_IMAGE, String.valueOf(isProfileImageSelected));

        return params;
    }

    /**
     * This function returns Get Collection List API params
     */
    public static HashMap<String, String> getCollectionListParams(String cityId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_ID, cityId);

        return params;
    }

    /**
     * This function returns Forgot Password API params
     */
    public static HashMap<String, String> getForgotPasswordParams(String email) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_EMAIL, email);

        return params;
    }

    public static HashMap<String, String> getForceForgotPasswordParams(String dinerEmail) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_EMAIL, dinerEmail);

        return params;
    }

    /**
     * This function returns Get Diner Payment History API params
     */
    public static HashMap<String, String> getDinerPaymentHistoryParams(String dinerId, String currentPage) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_PAGE, currentPage);

        return params;
    }

    /**
     * This function returns Get App Home Essentials API params
     */
    public static HashMap<String, String> getAppHomeEssentialsParams(String cityId, String dinerId,
                                                                     String latitude, String longitude,
                                                                     String byCity, String locArea,
                                                                     String area) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_ID, cityId);
        params.put(PARAM_DINER_ID, dinerId);

        if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
            params.put(PARAM_LAT, latitude);
            params.put(PARAM_LONG, longitude);

        } else {
            params.put(PARAM_BY_CITY, byCity);
            params.put(PARAM_ARR_LOCAREA, locArea);
            params.put(PARAM_ARR_AREA, area);
        }

        return params;
    }

    /**
     * This function returns App Tag Cuisine API params
     */
    public static HashMap<String, String> getAppTagCuisineParams(String cityId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_ID, cityId);

        return params;
    }

    /**
     * This function returns Get City List API params
     */
    public static HashMap<String, String> getCityListParams() {
        return getCommonParams();
    }

    /**
     * This function returns Get Booking for Review Balance API params
     */
    public static HashMap<String, String> getBookingForReviewParams(String dinerId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        return params;
    }

    public static HashMap<String, String> getSimilarRestaurantParams(String
                                                                             restId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_REST_ID, restId);

        return params;
    }

    /**
     * This function returns Locality Area Tree API params
     */
    public static HashMap<String, String> getLocalityAreaTreeParams(String cityId, boolean isDeltaDataRequired,
                                                                    String timestamp) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_ID, cityId);

        if (isDeltaDataRequired) {
            params.put(PARAM_TIMESTAMP, timestamp);
        }

        return params;
    }

    /**
     * This function returns Location API params
     */
    public static HashMap<String, String> getLocationParams(String searchNeedle, String latitude,
                                                            String longitude) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_SEARCH_NEEDLE, searchNeedle);
        params.put(PARAM_LAT, latitude);
        params.put(PARAM_LNG, longitude);

        return params;
    }

    /**
     * This function returns Forgot Email API params
     */
    public static HashMap<String, String> getForgotEmailParams(String phone) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_PHONE, phone);

        return params;
    }

    /**
     * This function returns Send OTP API params
     */
    public static HashMap<String, String> getSendOtpParams(String dinerId, String phone) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_PHONE, phone);

        return params;
    }

    /**
     * This function returns Verify Diner API params
     */
    public static HashMap<String, String> getVerifyDinerParams(String dinerId, String otp, String phone) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_OTP, otp);
        params.put(PARAM_PHONE, phone);

        return params;
    }

    /**
     * This function returns Get Party Packages API params
     */
    public static HashMap<String, String> getPartyPackagesParams() {
        return getCommonParams();
    }

    /**
     * This function returns Add Party Enquiry API params
     */
    public static HashMap<String, String> getAddPartyEnquiryParams(String partyDate, String timeSlot,
                                                                   String cityId, String dinerCount,
                                                                   String fromPrice, String toPrice,
                                                                   String arrPackageIds, String dinerId,
                                                                   String dinerName, String dinerEmail,
                                                                   String dinerPhone, String specialRequest) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_PARTY_DATE, partyDate);
        params.put(PARAM_TIME_SLOT, timeSlot);
        params.put(PARAM_CITY_ID, cityId);
        params.put(PARAM_DINER_COUNT, dinerCount);
        params.put(PARAM_FROM_PRICE, fromPrice);
        params.put(PARAM_TO_PRICE, toPrice);
        params.put(PARAM_ARR_PACKAGE_ID, arrPackageIds);
        params.put(PARAM_DINER_ID, ((dinerId == null || dinerId.isEmpty()) ? "0" : dinerId));
        params.put(PARAM_DINER_NAME, dinerName);
        params.put(PARAM_DINER_EMAIL, dinerEmail);
        params.put(PARAM_DINER_PHONE, dinerPhone);
        params.put(PARAM_SPECIAL_REQUEST, specialRequest);

        return params;
    }

    /**
     * This function returns Link Paytm Wallet API params
     */
    public static HashMap<String, String> getLinkPaytmWalletParams(String dinerId, String dinerPhone) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_DINER_PHONE, dinerPhone);

        return params;
    }

    /**
     * This function returns Init Payment Paytm API params
     */
    public static HashMap<String, String> getInitPaymentPaytmParams(String bookingId, String amount,
                                                                    String useDineoutWallet) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_BOOKIG_ID, bookingId);
        params.put(PARAM_AMOUNT, amount);
        params.put(PARAM_USE_DINEOUT_WALLET, useDineoutWallet);

        return params;
    }

    /**
     * This function returns Verify Referral API params
     */
    public static HashMap<String, String> getVerifyReferralParams(String dinerId, String referralCode) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_REF_CODE, referralCode);

        return params;
    }

    /**
     * This function returns Restaurant Details API params
     */
    public static HashMap<String, String> getRestaurantDetailsParams(String dinerId, String restaurantId,
                                                                     String latitude, String longitude) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_RES_ID, restaurantId);
        params.put(PARAM_LAT, latitude);
        params.put(PARAM_LONG, longitude);

        return params;
    }

    /**
     * This function returns Verify Paytm Wallet API params
     */
    public static HashMap<String, String> getVerifyPaytmWalletParams(String dinerId, String otp) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);
        params.put(PARAM_VERIFY_OTP, otp);

        return params;
    }

    /**
     * This function returns Submit Booking Review API params
     */
    public static HashMap<String, String> getSubmitBookingReviewParams(String dinerId, String intentBookingId,
                                                                       String apiBookingId, String rating,
                                                                       String review, String restaurantId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        if (intentBookingId == null || intentBookingId.equalsIgnoreCase("") || intentBookingId.equalsIgnoreCase("-1")) {
            if (apiBookingId == null || apiBookingId.equalsIgnoreCase("") || apiBookingId.equalsIgnoreCase("-1")) {
                params.put(PARAM_REVIEW_TYPE, REVIEW_TYPE_GENERIC);
            } else {
                params.put(PARAM_REVIEW_TYPE, REVIEW_TYPE_AUTHENTIC);
                params.put(PARAM_B_ID, apiBookingId);
            }
        } else {
            params.put(PARAM_REVIEW_TYPE, REVIEW_TYPE_AUTHENTIC);
            params.put(PARAM_B_ID, intentBookingId);
        }

        params.put(PARAM_RATING_FOOD, rating);
        params.put(PARAM_RATING_SERVICE, rating);
        params.put(PARAM_RATING_AMBIENCE, rating);
        params.put(PARAM_RATING_DESC, review);

        if (!TextUtils.isEmpty(restaurantId)) {
            params.put(PARAM_REST_ID, restaurantId);
        }

        return params;
    }

    /**
     * This function returns Redeem Paytm API params
     */
    public static HashMap<String, String> getRedeemPaytmParams(String dinerId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        return params;
    }

    /**
     * This function returns Get Reward Schemes Data API params
     */
    public static HashMap<String, String> getRewardSchemesDataParams(String dinerId) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        return params;
    }

    /**
     * This function returns Get Restaurant Review API params
     */
    public static HashMap<String, String> getRestaurantReviewParams(String restaurantId, String limit) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_RES_ID, restaurantId);
        params.put(PARAM_LIMIT, limit);

        return params;
    }

    /**
     * This function returns SOLR Search Auto Suggest API params
     */
    public static HashMap<String, String> getSolrSearchAutoSuggestParams(String cityName,
                                                                         String searchNeedle,
                                                                         boolean isDoSpecific) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_NAME, cityName);
        params.put(PARAM_SEARCH_NEEDLE, searchNeedle);

        if (isDoSpecific)
            params.put(PARAM_SOLR_SEARCH_ARR_TAG, "Dineout Plus");

        return params;
    }

    /**
     * This function returns SOLR Search Full API params
     */
    public static HashMap<String, String> getSolrSearchFullParams(String cityName, String searchNeedle,
                                                                  String limit, String start) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_NAME, cityName);
        params.put(PARAM_SEARCH_NEEDLE, searchNeedle);
        params.put(PARAM_LIMIT, limit);
        params.put(PARAM_START, start);

        return params;
    }

    /**
     * This function returns Register API params
     */
    public static HashMap<String, String> getRegisterParams(String phone, String name, String email,
                                                            String password) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_PHONE, phone);
        params.put(PARAM_NAME, name);
        params.put(PARAM_EMAIL, email);
        params.put(PARAM_PASSWORD, password);

        return params;
    }

    /**
     * This function returns Login API params
     */
    public static HashMap<String, String> getLoginParams(String email, String password, String type,
                                                         String accessToken) {
        HashMap<String, String> params = getCommonParams();

        if (!TextUtils.isEmpty(email))
            params.put(PARAM_EMAIL, email);

        if (!TextUtils.isEmpty(password))
            params.put(PARAM_PASSWORD, password);

        if (!TextUtils.isEmpty(type))
            params.put(PARAM_TYPE, type);

        if (!TextUtils.isEmpty(accessToken))
            params.put(PARAM_ACCESS_TOKEN, accessToken);

        return params;
    }

    /**
     * This function returns Generate Booking API params
     */
    public static HashMap<String, String> getGenerateBookingParams(String offerId, String time, String date,
                                                                   String guestCount, String restaurantId,
                                                                   String email, String dinerId, String userName,
                                                                   String phone, String specialRequest,
                                                                   String bookingId, String bookingType,
                                                                   String deviceRegId) {
        HashMap<String, String> params = getCommonParams();

        if (!TextUtils.isEmpty(offerId)) {
            params.put(PARAM_OFFER_ID, offerId);
        }
        params.put(PARAM_B_ID, bookingId);
        params.put(PARAM_BOOKING_TYPE, bookingType);
        params.put(PARAM_TIME, time);
        params.put(PARAM_DATE, date);
        params.put(PARAM_PEOPLE, guestCount);
        params.put(PARAM_DEVICE_REG_ID, deviceRegId);
        params.put(PARAM_RES_ID, restaurantId);
        params.put(PARAM_USER_EMAIL, email);
        params.put(PARAM_USER_ID, dinerId);
        params.put(PARAM_USER_NAME, userName);
        params.put(PARAM_PHONE, phone);
        params.put(PARAM_SPECIAL_REQUEST, specialRequest);

        return params;
    }

    /**
     * This function returns User Booking Segg API params
     */
    public static HashMap<String, String> getUserBookingSeggParams(String userId, String ak) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_USER_ID, userId);
        if (!TextUtils.isEmpty(ak))
            params.put(AppConstant.DEFAULT_HEADER_AUTH_KEY, ak);
        return params;
    }

    /**
     * This function returns Get Diner Favorite Restaurant Data API params
     */
    public static HashMap<String, String> getDinerFavoriteRestaurantDataParams(String dinerId, String ak) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_DINER_ID, dinerId);

        if (!TextUtils.isEmpty(ak)) {
            params.put(AppConstant.DEFAULT_HEADER_AUTH_KEY, ak);
        }

        return params;
    }

    /**
     * This function returns SOLR Search Full API params
     */
    public static HashMap<String, String> getSolrSearchFullParams(String cityName, String searchNeedle,
                                                                  String limit, String start, boolean isBookingMode,
                                                                  boolean hasCurrentLocation, boolean isLocationModeAuto,
                                                                  String latitude, String longitude, boolean isGpsOn,
                                                                  boolean isFromNotification, boolean isFilterApplied,
                                                                  String sortBy, String ratingSlab, String priceRange,
                                                                  boolean isNeedleSearch) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_NAME, cityName);
        params.put(PARAM_LIMIT, limit);
        params.put(PARAM_START, start);

        if (isBookingMode) {
            if (hasCurrentLocation && isLocationModeAuto) {
                params.put(PARAM_LAT, latitude);
                params.put(PARAM_LONG, longitude);
            }
        } else {
            if (isGpsOn && !isFromNotification) {
                if (hasCurrentLocation && isLocationModeAuto) {
                    params.put(PARAM_LAT, latitude);
                    params.put(PARAM_LONG, longitude);
                }
            }
        }

        if (isFilterApplied) {
            if (sortBy != null) {
                params.put(PARAM_SORT_BY, sortBy);
            }

            if (ratingSlab != null) {
                params.put(PARAM_RATING_SLAB, ratingSlab);
            }

            if (priceRange != null) {
                params.put(PARAM_PRICE_RANGE, priceRange);
            }
        }

        if (isNeedleSearch) {
            params.put(PARAM_SEARCH_NEEDLE, searchNeedle);
        }

        return params;
    }

    public static HashMap<String, String> getDinerWalletSuggestionParam(String dinerId, String cityId) {

        HashMap<String, String> param = getCommonParams();

        param.put(ApiParams.PARAM_DINER_ID, dinerId);
        param.put(ApiParams.PARAM_CITY_ID, cityId);


        return param;

    }

    public static HashMap<String, String> getSolrSearchFullParams(String cityName, String limit, String start,
                                                                  String latitude, String longitude) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_CITY_NAME, cityName);
        params.put(PARAM_LIMIT, limit);
        params.put(PARAM_START, start);

        if (!TextUtils.isEmpty(latitude)) {
            params.put(PARAM_LAT, latitude);
        }

        if (!TextUtils.isEmpty(longitude)) {
            params.put(PARAM_LONG, longitude);
        }

        return params;
    }

    /**
     * API: home_page_card
     */
    public static HashMap<String, String> getHomePageCardParams(String byCity, String locArea,
                                                                String area, String gpsEnabled) {
        HashMap<String, String> params = getCommonParams();

        params.put(PARAM_BY_CITY, byCity);
        params.put(PARAM_ARR_LOCAREA, locArea);
        params.put(PARAM_ARR_AREA, area);
        params.put(PARAM_GPS_ENABLED, gpsEnabled);

        return params;
    }

    public static HashMap<String,String> getDOPromoCodeParams(String action,String code,String objId,String type,String dinerId){

        HashMap<String, String> param = getCommonParams();

        param.put("type", "doplus");
        param.put("diner_id",dinerId);
        param.put("obj_id","1");
        param.put("obj_type","doplus");
        param.put("action",action);
        param.put("promocode",code);
        return param;
    }

    public static HashMap<String,String> getBillingSummaryParams(String id,String objId,String type,boolean isWalletUsed){
        HashMap<String, String> param = getCommonParams();

        param.put("diner_id",id);
        param.put("obj_id",objId);
        param.put("obj_type",type);
        param.put("action","billing_summary");

        param.put("is_do_wallet_used",isWalletUsed?"1":"0");
        return param;
    }


    public static HashMap<String,String> getBillingSummaryParamsBooking(String id,String objId,
                                                                        String type,boolean isWalletUsed,String amount){
        HashMap<String, String> param = getBillingSummaryParams(id, objId, type, isWalletUsed);
        param.put("bill_amount",amount);
        return param;
    }

    public static HashMap<String,String> getDOPaymentOptions(String id){

        HashMap<String, String> param = getCommonParams();

        param.put("diner_id",id);
        return param;
    }

    public static Map<String,String> getPayFromWalletParam(String id,String dinerId,String doHash,boolean isDOWallet,String type,String amt){

        Map<String,String> param = getCommonParams();
        param.put("diner_id", dinerId);
        param.put("amt_to_pay",amt);
        param.put("do_hash",doHash);
        param.put("obj_id",id);
        param.put("obj_type",type);
        param.put("is_do_wallet_used", isDOWallet? "1" : "0");
        return param;
    }

    public static Map<String,String> getPayFromPromoCode(String id,String dinerId,String doHash,String type){

        Map<String,String> param = getCommonParams();
        param.put("diner_id", dinerId);
        param.put("do_hash",doHash);
        param.put("obj_id",id);
        param.put("obj_type",type);
        return param;
    }

    public static Map<String,String> getNotificationParams(String startIndex,String endIndex){

        Map<String,String> param = getCommonParams();

        param.put("start",startIndex);
        param.put("limit",endIndex);
        return param;
    }

    public static Map<String,String> getInitPaymentParams(String dinerId,String id,boolean isDOWallet,
                                                          String type,String paymentType){

        Map<String,String> param = getCommonParams();
        param.put("diner_id", dinerId);
        param.put("obj_id",id);
        param.put("is_do_wallet_used", isDOWallet ? "1" : "0");
        param.put("payment_type",paymentType);
        param.put("obj_type",type);
        return  param;
    }
}
