package com.example.datanetworkmodule;

import android.net.Uri;

public class AppConstant {

    public static final String BASE_URL = "https://qc1.dineout.co.in/app_api/mobile_app_api_v9/";
    //public static final String BASE_URL = "http://crm.dineout.co.in/app_api/mobile_app_api_v7/";
    //public static final String BASE_URL = "https://staging.dineout.co.in/app_api/mobile_app_api_v8/";
    //public static final String BASE_URL = "https://api.dineout.co.in/app_api/mobile_app_api_v8/";
    //public static final String BASE_URL = "https://staging.dineout.co.in/app_api/mobile_app_api_v9/";
//    public static final String BASE_URL = "https://api.dineout.co.in/app_api/mobile_app_api_v9/";
//    public static final String BASE_URL = "http://dev1.dineoutdeals.in/app_api/mobile_app_api_v9/";


    public static final Uri WEB_URL = Uri.parse("http://dineout.co.in/");
    public static final String UBER_BASE_URL = "https://api.uber.com/v1/estimates/";
    public static final String BASE_URL_TNC = "http://www.dineout.co.in/mobile/mobile_new/";
    public static final String DINEOUT_PRIVACY_POLICY = BASE_URL_TNC + "app_privacy_policy";
    public static final String DINEOUT_TNC = BASE_URL_TNC + "app_tnc";
    public static final String DINEOUT_PLUS_TNC_URL = "http://www.dineout.co.in/plus/termsandconditions";
    //public static final String DINEOUT_SMART_PAY_URL = "http://dev1.dineoutdeals.in/smartpay";
    public static final String DINEOUT_SMART_PAY_URL = "http://www.dineout.co.in/smartpay";

    public static String URL_GET_APP_CONFIG = "get_app_config";
    public static String URL_APP_TAG_URL = "app_tag_cuisine";
    public static String URL_HOME_ESSENTIALS_URL = "get_app_home_essentials";
    public static String URL_LOCALITY_AREA_TREE = "locality_area_tree";
    public static String URL_REST_DETAILS = "restaurant_details";
    public static String URL_REST_REVIEWS = "get_restaurant_review";
    public static String URL_GENERATE_BOOKING = "generate_booking";
    public static String URL_SOLR_SEARCH_FULL = "solr_search_full";
    public static String URL_SOLR_SEARCH_AUTO_SUGGEST = "solr_search_auto_suggest";
    public static String URL_REGISTER = "register";
    public static String URL_LOGIN = "login";
    public static String URL_FORGOT_PASS = "forget_password";
    public static String URL_GEN_SEARCH = "gen_search";
    public static String URL_UPDATE_DINER_INFO = "update_diner_info";
    public static String URL_GET_DINER_PROFILE = "get_diner_profile";
    public static String URL_DINER_FAV_LIST = "get_diner_fav_rest_data";
    public static String URL_SET_UNSET_FAV_REST = "set_unset_fav_rest";
    public static String URL_USER_BOOKING_SEGG = "user_booking_segg";
    public static String URL_GET_BOOKING_DETAIL = "get_booking_detail";
    public static String URL_CANCEL_BOOKING = "cancel_booking";
    public static String URL_ACTIVE_OFFERS = "active_offers";
    public static String URL_SUBMIT_BOOKING_REVIEW = "submit_booking_review";
    public static String URL_SEND_REG_ID = "insert_mobile_id";
    public static String URL_NOTIFICATION_TRACKING = "set_is_viewed_notification_tracking";
    public static String URL_VERIFY_REFERRAL = "verify_referral";
    public static String URL_REDEEM_PAYTM = "redeem_paytm";
    public static String URL_COLLECTION_API = "get_collection_list";
    public static String URL_PARTY_ENQUIRY = "add_party_enquiry";
    public static String URL_GET_PARTY_PACKAGES = "get_party_packages";
    public static String URL_VERIFY_OTP = "verify_diner";
    public static String URL_SEND_OTP = "send_otp";
    public static String URL_FORGET_EMAIL = "forget_email";
    public static String URL_FORCE_FORGET_PASSWORD = "force_forget_password";
    public static String URL_LOGOUT = "logout";
    public static String URL_PAYMENT_HISTORY = "get_diner_payment_history";
    public static String URL_INIT_PAYMENT = "init_payment";
    public static String URL_PAYMENT_OPTION = "get_payment_option";
    public static String URL_REQUEST_INVITE = "doplus_query";
    public static String URL_VERIFY_PLUS_CODE = "validate_dineout_plus_code_android";
    public static String URL_CHECK_PAYTM_BALANCE = "check_paytm_balance";
    public static String URL_LINK_PAYTM_WALLET = "link_paytm_wallet";
    public static String URL_VERIFY_PAYTM_WALLET = "verify_paytm_wallet";
    public static String URL_INIT_PAYMENT_PAYTM = "init_payment_paytm";
    public static String URL_DOPLUS_CONVERSION_DETAIL = "doplus_conversion_detail";
    public static String URL_REGISTER_MEMBER = "payment_pg_init";
    public static String URL_REFER_EARN = "get_referral_data";
    public static String URL_LOCATION_SEARCH = "location_api";
    public static String URL_WAIT_TIME = "waiting_time";
    public static String URL_BILL_PAYMENT = "bill_payment";
    public static String URL_LINK_WALLET = "link_wallet";
    public static String URL_VERIFY_WALLET = "verify_wallet";
    public static String URL_PAY_FROM_DOWALLET = "pay_from_diner_wallet";
    public static String URL_WALLET_SUMMARY = "wallet_summary";
    public static String URL_TRANS_FROM_WALLET = "trans_from_do_wallet";
    public static String URL_WALLET_SUGGESTION = "get_wallet_suggestion";
    public static String URL_INIT_PG = "init_pg_payment";
    public static String URL_GET_PAYMENT_INVOICE = "get_payment_invoice";
    public static String URL_GET_PAYMENT_STATUS = "get_payment_status";
    public static String URL_GET_SIMILAR_RESTAURANTS="get_similar_restaurant";
    public static String URL_EDIT_BOOKING = "edit_booking";
    public static String URL_PROMO = "promocode";
    public static String URL_NOTIFICATION = "get_diner_notification";

    //From v9
    public static String URL_HOME_PAGE_CARD = "home_page_card";


    //Integer Constants
    public static final int MAX_TAG_SELECTION_COUNT = 4;
    public static final int USER_CANCELLED_OPERATION = 100;
    public static final int PAYNOW_BILL_PAYMENT_REQUEST_CODE = 101;
    public static final int EDIT_BOOKING_REQUEST_CODE = 102;

    //Permission Request Codes
    public static final int REQUEST_PERMISSION_CALL_PHONE = 0x01;
    public static final int REQUEST_PERMISSION_RECEIVE_SMS = 0x02;
    public static final int REQUEST_PERMISSION_READ_PHONE_STATE = 0x03;
    public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 0x04;
    public static final int REQUEST_PERMISSION_LOCATION = 0x05;
    public static final int REQUEST_PERMISSION_ACCOUNTS = 0x06;

    //Server Type Constants
    public static final String SERVER_TYPE_DEV = "dev";

    //Payment Options
    public static final String PAYMENT_OPTION_PAYTM = "paytm";

    //Diner Status
    public static final String DINER_STATUS_UNVERIFIED = "unverified";

    //Bundle Constants
    public static final String BUNDLE_DO_PLUS_SEARCH = "BUNDLE_DO_PLUS_SEARCH";
//    public static final String BUNDLE_GUEST_DETAILS = "BUNDLE_GUEST_DETAILS";
    public static final String BUNDLE_DATETIME_NOW = "BUNDLE_DATETIME_NOW";
    public static final String BUNDLE_DATES_LIST = "BUNDLE_DATES_LIST";
    public static final String BUNDLE_DATES_FORMATTED_LIST = "BUNDLE_DATES_FORMATTED_LIST";
    public static final String BUNDLE_TIMES_LIST = "BUNDLE_TIMES_LIST";
    public static final String BUNDLE_POS_EVENING_TIME = "BUNDLE_POS_EVENING_TIME";
    public static final String BUNDLE_IS_NO_TIME_SLOT_FOR_TODAY = "BUNDLE_IS_NO_TIME_SLOT_FOR_TODAY";
    public static final String BUNDLE_HAS_SET_BOOKING_DATE_TIME = "BUNDLE_HAS_SET_BOOKING_DATE_TIME";
    public static final String BUNDLE_GUEST_COUNT = "BUNDLE_GUEST_COUNT";
    public static final String BUNDLE_DATE_INDEX = "BUNDLE_DATE_INDEX";
    public static final String BUNDLE_TIME_INDEX = "BUNDLE_TIME_INDEX";
    public static final String BUNDLE_AMPM_INDEX = "BUNDLE_AMPM_INDEX";
    public static final String BUNDLE_HIDE_CANCEL = "BUNDLE_HIDE_CANCEL";
    public static final String BUNDLE_BOOKING_ID = "BUNDLE_BOOKING_ID";
    public static final String BUNDLE_DISPLAY_BOOKING_ID = "BUNDLE_DISPLAY_BOOKING_ID";
    public static final String BUNDLE_BILL_AMOUNT = "BUNDLE_BILL_AMOUNT";
    public static final String BUNDLE_PAYTM_WALLET_AMOUNT = "BUNDLE_PAYTM_WALLET_AMOUNT";
    public static final String BUNDLE_DINEOUT_WALLET_AMOUNT = "BUNDLE_DINEOUT_WALLET_AMOUNT";
    public static final String BUNDLE_SOURCE_LATITUDE = "BUNDLE_SOURCE_LATITUDE";
    public static final String BUNDLE_SOURCE_LONGITUDE = "BUNDLE_SOURCE_LONGITUDE";
    public static final String BUNDLE_DESTINATION_LATITUDE = "BUNDLE_DESTINATION_LATITUDE";
    public static final String BUNDLE_DESTINATION_LONGITUDE = "BUNDLE_DESTINATION_LONGITUDE";
    public static final String BUNDLE_RESTAURANT_ID = "BUNDLE_RESTAURANT_ID";
    public static final String BUNDLE_RESTAURANT_NAME = "BUNDLE_RESTAURANT_NAME";
    public static final String BUNDLE_RESTAURANT_LOCATION = "BUNDLE_RESTAURANT_LOCATION";
    public static final String BUNDLE_DEEP_LINKING_URI = "uri";
    public static final String BUNDLE_USER_LOGGED_IN = "BUNDLE_USER_LOGGED_IN";
    public static final String BUNDLE_PHONE_NUMBER = "BUNDLE_PHONE_NUMBER";
    public static final String BUNDLE_OTP_MESSAGE = "BUNDLE_OTP_MESSAGE";
    public static final String BUNDLE_VERIFY_OTP_STATUS = "BUNDLE_OTP_STATUS";
    public static final String BUNDLE_VERIFY_OTP_MESSAGE = "BUNDLE_OTP_API_ERROR";
    public static final String BUNDLE_DIALOG_TITLE = "BUNDLE_DIALOG_TITLE";
    public static final String BUNDLE_DIALOG_HEADER = "BUNDLE_DIALOG_HEADER";
    public static final String BUNDLE_DIALOG_DESCRIPTION = "BUNDLE_DIALOG_DESCRIPTION";
    public static final String BUNDLE_DIALOG_POSITIVE_BUTTON_TEXT = "BUNDLE_DIALOG_POSITIVE_BUTTON_TEXT";
    public static final String BUNDLE_DIALOG_NEGATIVE_BUTTON_TEXT = "BUNDLE_DIALOG_NEGATIVE_BUTTON_TEXT";
    public static final String BUNDLE_DIALOG_CANCELLABLE = "BUNDLE_DIALOG_CANCELLABLE";
    public static final String BUNDLE_DINEOUT_PLUS_CODE = "BUNDLE_DINEOUT_PLUS_CODE";
    public static final String BUNDLE_PAYABLE_AMOUNT = "BUNDLE_PAYABLE_AMOUNT";
    public static final String BUNDLE_IS_DINEOUT_PLUS_SUCCESS = "BUNDLE_IS_DINEOUT_PLUS_SUCCESS";
    public static final String BUNDLE_COMING_FROM_DINEOUT_PLUS = "BUNDLE_COMING_FROM_DINEOUT_PLUS";
    public static final String BUNDLE_SHOW_DATE_PICKER = "BUNDLE_SHOW_DATE_PICKER";
    public static final String BUNDLE_FROM_SEARCH = "search_action";
    public static final String BUNDLE_IS_EDITING_BOOKING = "IS_EDITING_BOOKING";
    public static final String BUNDLE_COMING_FROM_WAIT_TIME = "BUNDLE_COMING_FROM_WAIT_TIME";
    public static final String BUNDLE_HAS_USER_SET_BOOKING_DATE_TIME = "BUNDLE_HAS_USER_SET_BOOKING_DATE_TIME";

    // Share Message Constants
    public static final String DINER_NAME_PLACE_HOLDER = "#diner_name#";
    public static final String DINER_FIRST_NAME_PLACE_HOLDER = "#diner_first_name#";
    public static final String RESTAURANT_NAME_PLACE_HOLDER = "#restaurant_name#";
    public static final String TINY_URL_PLACE_HOLDER = "#tiny_url#";
    public static final String BOOKING_ID_PLACE_HOLDER = "#booking_id#";
    public static final String BOOKING_DATE_PLACE_HOLDER = "#booking_date#";
    public static final String BOOKING_TIME_PLACE_HOLDER = "#booking_time#";
    public static final String DINER_COUNT_PLACE_HOLDER = "#diner_count#";
    public static final String RESTAURANT_LOCALITY_PLACE_HOLDER = "#locality_name#";


    //Card Type Constants
    public static final String CARD_TYPE_BOOKING = "booking";
    public static final String CARD_TYPE_MY_SAVING = "my_saving";
    public static final String CARD_TYPE_STATIC = "static";
    public static final String CARD_TYPE_INFO = "info";
    public static final String CARD_TYPE_MAGIC = "magic";
    public static final String CARD_TYPE_SEARCH = "search";


    //General Constants
    public static final String ERROR_MESSAGE = "error_msg";
    public static final String ERROR_CODE = "error_code";
    public static final String ERROR_TYPE = "error_type";
    public static final String STATUS_CODE = "status_code";
    public static final String OUTPUT_PARAMS = "output_params";
    public static final String B_ID = "b_id";
    public static final String R_ID = "r_id";
    public static final String IMAGE_DATA = "image_data";
    public static final String FORCE_UPDATE = "fu";
    public static final String SEARCH_TERM = "st";
    public static final String SEARCH_FILTER = "sf";
    public static final String SEARCH_FILTER_BY_CITY = "by_city";
    public static final String SEARCH_FILTER_ARR_LOCAREA = "arr_locarea";
    public static final String SEARCH_FILTER_ARR_AREA = "arr_area";

    //API Constants
    public static final String CARLSBERG_DATA = "carlsberg_data";
    public static final String OFFER_ID = "offer_id";
    public static final String RESTAURANT_ID = "restaurant_id";
    public static final String RES_ID = "res_id";
    public static final String REST_NAME = "rest_name";
    public static final String COUNT_COVERS = "cnt_covers";
    public static final String AVERAGE_RATING = "avg_rating";
    public static final String DINING_DATE_TIME = "dining_dt_time";
    public static final String DINER_NAME = "diner_name";
    public static final String CAN_CANCEL = "can_cancel";
    public static final String BOOKING_STATUS = "booking_status";
    public static final String BOOKING_TYPE = "booking_type";
    public static final String BASE_URL_CONSTANT = "base_url";
    public static final String IS_REVIEWED = "is_reviewed";
    public static final String DISP_ID = "disp_id";
    public static final String IS_FF = "is_ff";
    public static final String IS_FAV = "is_fav";
    public static final String RES_AUTH = "res_auth";
    public static final String UPCOMING_BOOKINGS = "upcoming_bookings";
    public static final String HISTORY_BOOKINGS = "history_bookings";
    public static final String DINING_DATE = "dining_dt";
    public static final String DINING_TIME = "dining_time";
    public static final String BK_TYPE = "bk_type";
    public static final String IS_HEADER = "is_header";
    public static final String IS_CHECKED_IN = "is_checked_in";
    public static final String PAYMENT_OPTIONS = "payment_options";
    public static final String CURR_TS = "curr_ts";
    public static final String RES_UPDATE = "res_update";
    public static final String C_ID = "c_id";
    public static final String OTP_REQUIRED = "otp_required";
    public static final String USER_RID = "user_rid";
    public static final String PROFILE_NAME = "profile_name";
    public static final String SCREEN_NAME = "screen_name";
    public static final String SCREEN_NAME_MOBILE = "screen_name_mobile";
    public static final String LOCALITY_ID = "locality_id";
    public static final String COST_FOR_2 = "cost_for_2";
    public static final String OPEN_TIMINGS = "open_timings";
    public static final String CLOSING_TIME = "closing_time";
    public static final String ESSENTIAL_INFORMATION = "essential_information";
    public static final String TYPE_OF_PLACE = "type_of_place";
    public static final String CREATION_DT = "creation_dt";
    public static final String FB_PAGE_URL = "fb_page_url";
    public static final String CITY_NAME = "city_name";
    public static final String AREA_NAME = "area_name";
    public static final String LOCALITY_NAME = "locality_name";
    public static final String CITY_ID = "city_id";
    public static final String AREA_ID = "area_id";
    public static final String IS_PREPAID = "is_prepaid";
    public static final String COST_PER_COVER = "cost_per_cover";
    public static final String PAYMENT_MODE = "payment_mode";
    public static final String COMPANY_ID = "company_id";
    public static final String SOURCE_ID = "source_id";
    public static final String WHICH_TYPE_ID = "whichtype_id";
    public static final String RESTAURANT_CHAIN_ID = "restaurant_chain_id";
    public static final String HOTEL_ID = "hotel_id";
    public static final String MOBILE_NUMBER = "mobile_number";
    public static final String MOBILE_NO = "mobile_no";
    public static final String MERC_UNQ_REF = "MERC_UNQ_REF";
    public static final String SKIP_PG = "skip_pg";
    public static final String TC_GUID = "tc_guid";
    public static final String VIEW_COUNT = "viewCount";
    public static final String DATE_LAUNCHED = "date_launched";
    public static final String AVG_RATING = "avg_rating";
    public static final String NEARBY_DISTANCE = "nearby_distance";
    public static final String LEAVE_REVIEW = "leave_review";
    public static final String CUISINE_DATA = "cuisine_data";
    public static final String OFFER_DATA = "offer_data";
    public static final String ARRAY_TAG = "arr_tag";
    public static final String ARRAY_CUISINE = "arr_cuisine";
    public static final String IS_TAG = "is_tag";
    public static final String DINER_ID = "diner_id";
    public static final String CARD_TYPE = "card_type";
    public static final String CARD_Data = "card_data";
    public static final String IMAGE_URL = "image_url";
    public static final String ICON_URL = "icon_url";
    public static final String HREF_TITLE = "href_title";
    public static final String IS_WEB = "is_web";
    public static final String TNC_HREF = "tnc_href";
    public static final String SHOW_ON_LOGIN = "show_on_login";
    public static final String SHOW_ON_WITHOUT_LOGIN = "show_on_without_login";
    public static final String OFFER_TEXT = "offer_text";
    public static final String COUNT_KIDS = "cnt_kids";
    public static final String RES_URL = "rest_url";
    public static final String CAN_CHECKIN = "can_checkin";
    public static final String CAN_REVIEW = "can_review";
    public static final String DISPLAY_DATE = "display_date";
    public static final String DISPLAY_TIME = "display_time";
    public static final String DISPLAY_DAY = "display_day";
    public static final String COUNT_OFFER = "n_offers";
    public static final String OFFER_TITLE = "offer_title";
    public static final String BOOKING_COUNT = "booking_count";
    public static final String EVENT_DESCRIPTION = "event_desc";
    public static final String LAT_LONG = "lat_lng";
    public static final String SECONDARY_CUISINE = "secondary_cuisine";
    public static final String GEO_DISTANCE = "geo_distance";
    public static final String RESTAURANT_NAME = "restaurant_name";
    public static final String PAYMENT_BALANCE = "paytm_balance";
    public static final String DINEOUT_WALLET = "dineout_wallet";
    public static final String DINER_STATUS = "diner_status";
    public static final String PHONE_NUMBER = "phone_number";
    public static final String TPW_ID = "tpw_id";
    public static final String CHANNEL_ID = "channel_id";
    public static final String INDUSTRY_TYPE_ID = "industry_type_id";
    public static final String ORDER_ID = "order_id";
    public static final String CUSTOMER_ID = "cust_id";
    public static final String ALREADY_PAID = "already_paid";
    public static final String SHORT_URL = "short_url";
    public static final String IS_ACCEPT_PAYMENT = "is_accept_payment";
    public static final String REF_CODE = "ref_code";
    public static final String AMOUNT = "amount";
    public static final String CODE = "code";
    public static final String WELCOME_MSG = "welcome_msg";
    public static final String LINE1 = "line1";
    public static final String LINE2 = "line2";
    public static final String DOPLUS_CARD = "doplus_card";
    public static final String IS_DO_PLUS_MEMBER = "is_do_plus_member";
    public static final String FACEBOOK_EVENT_PARAM_CONTENT_TYPE_NAME = "product";
    public static final String FACEBOOK_EVENT_PARAM_DESCRIPTION = "facebook";
    public static final String IS_EDITABLE="is_editable";

    public static final int PAYMENT_SUCCESS = 1;
    public static final int PAYMENT_FAILURE = 0;
    public static final int PAYMENT_PENDING = 2;

    /**
     * Code for specifying IOException
     */
    public static final String PARAM_TEMP = "f";
    public static final String DEVICE_TYPE = "android";
    public static final String DEFAULT_HEADER_DEVICE_ID = "d_id";
    public static final String DEFAULT_HEADER_DEVICE_ID_1 = "d_id_1";
    public static final String DEFAULT_HEADER_DEVICE_ID_2 = "d_id_2";
    public static final String DEFAULT_HEADER_DEVICE_TOKEN = "d_token";
    public static final String DEFAULT_HEADER_DEVICE_TYPE = "d_type";
    public static final String DEFAULT_HEADER_CITY_ID = "city_id";
    public static final String DEFAULT_HEADER_DINER_ID = "diner_id";
    public static final String DEFAULT_HEADER_APP_VERSION = "app_version";
    public static final String DEFAULT_HEADER_AUTH_KEY = "ak";
    public static final String DEFAULT_HEADER_DEVICE_LATITUDE = "d_latitude";
    public static final String DEFAULT_HEADER_DEVICE_LONGITUDE = "d_longitude";
    public static final String DEFAULT_HEADER_ENTITY_LATITUDE = "elat";
    public static final String DEFAULT_HEADER_ENTITY_LONGITUDE = "elng";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String CITY_LIST_URL = "city_list";
    public static final String PARAM_SOLR_SEARCH_CITY_NAME = "city_name";
    public static final String PARAM_SOLR_SEARCH_NEEDLE = "search_needle";
    public static final String PARAM_LOCALITY_ID = "l_id";
    public static final String PARAM_SOLR_SEARCH_ARR_AREA = "arr_area[]";
    public static final String PARAM_SOLR_SEARCH_ARR_LOCALITY = "arr_locality[]";
    public static final String PARAM_SOLR_SEARCH_ARR_CUISINE = "arr_cuisine[]";
    public static final String PARAM_SOLR_SEARCH_ARR_TAG = "arr_tag[]";
    public static final String PARAM_SOLR_SEARCH_RATING_SLAB = "rating_slab";
    public static final String PARAM_SOLR_SEARCH_PRICE_RANGE = "price_range";
    public static final String PARAM_SOLR_SEARCH_SORT_BY = "sortby";
    public static final String PARAM_SOLR_SEARCH_LATITUDE = "lat";
    public static final String PARAM_SOLR_SEARCH_LONGITUDE = "long";
    public static final String PARAM_SOLR_SEARCH_LIMIT = "limit";
    public static final String PARAM_SOLR_SEARCH_START = "start";
    public static final String REGISTER_GET_PARAM_PHONE = "phone";
    public static final String PARAM_UPDATE_DINER_INFO_ID = "diner_id";
    public static final String PARAM_UPDATE_DINER_INFO_FIRST_NAME = "first_name";
    public static final String PARAM_UPDATE_DINER_INFO_LAST_NAME = "last_name";
    public static final String PARAM_UPDATE_DINER_INFO_PHONE = "phone";
    public static final String PARAM_UPDATE_DINER_INFO_IMAGE = "image";
    public static final String PARAM_UPDATE_DINER_INFO_UPDATE_IMAGE = "update_image";
    public static final String UBER_PRICE_URL = UBER_BASE_URL + "price";
    public static final String UBER_TIME_URL = UBER_BASE_URL + "time";
    public static final String GET_BOOKING_FOR_REVIEW = "get_booking_for_review";
    public static final String PARAM_GET_ARR_LOCALITY = "arr_locality[]";
    public static final String PARAM_GET_ARR_AREA = "arr_area[]";
    public static final String IS_INVITE_ONLY = "is_invite_only";
    public static final String CONVERSION_LABEL = "conversion_label";
    public static final String DINING_DESTINATIONS_LABEL = "dining_destinations_label";
    public static final String COST_LABEL = "cost_label";
    public static final String DINING_DESTINIATION_IMAGES = "dining_destination_images";
    public static final String HOW_IT_WORKS = "how_it_works";
    public static final String PARAM_DO_MEMBER_REG_ADDRESS1 = "address_1";
    public static final String PARAM_DO_MEMBER_REG_ADDRESS2 = "address_2";
    public static final String PARAM_DO_MEMBER_REG_CITY = "city";
    public static final String PARAM_DO_MEMBER_REG_STATE = "state";
    public static final String PARAM_DO_MEMBER_REG_PIN = "pincode";
    public static final String PARAM_DO_MEMBER_REG_PRODUCT_ID = "product_id";
    public static final String PARAM_DO_MEMBER_REG_PROMO = "promocode";
    public static final String PARAM_DO_MEMBER_REG_PROVIDER = "provider_id";
    public static final String PARAM_IS_DOPLUS_CITY = "is_doplus_city";
    public static final String URL_PROMO_CODE = "pay_from_promocode";

    public static final String PARAMS_SEPARATOR = "|";
}
