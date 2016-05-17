package in.co.dineout.xoppin.dineoutcollection.handler;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.model.ChainModel;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.HotelsModel;
import in.co.dineout.xoppin.dineoutcollection.model.StaticDataModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 03/02/16.
 */
public class StaticDataHandler {
    private static final String TAG = StaticDataHandler.class.getSimpleName();

    private static final String KEY_STATIC_DATA = "KEY_STATIC_DATA";

    private static StaticDataHandler staticDataHandler;
    private StaticDataModel staticDataModel;

    public static void init(Context context) {
        staticDataHandler = new StaticDataHandler(context);
    }

    private StaticDataHandler(Context context) {
        String staticJsonString = PreferenceManager.getDefaultSharedPreferences(context).getString(KEY_STATIC_DATA, "");
        if (TextUtils.isEmpty(staticJsonString)) {
            //this means that the application is started for the first time
            //update the category with raw file
            staticJsonString = Utils.getStringFromRawFile(context, R.raw.dineout_static);
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(KEY_STATIC_DATA, staticJsonString).commit();
        }
        try {
            JSONObject staticJson = new JSONObject(staticJsonString);
            JSONObject dataJson = Utils.getJsonObject(staticJson, "data");
            staticDataModel = new Gson().fromJson(dataJson.toString(),StaticDataModel.class);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static StaticDataHandler getInstance() {
        if (null == staticDataHandler) {
            Log.e(TAG, "this should not have happened. Failed to initialize StaticDataHandler");
            return null;
        }
        return staticDataHandler;
    }

    public StaticDataModel getStaticDataModel() {
        return this.staticDataModel;
    }

    public void updateStaticData(final Context context) {
//        // Tag used to cancel the request
//        String TAG_REQUEST_STATIC_DATA = "TAG_REQUEST_STATIC_DATA";
//
//        String url = "http://laravel.dineoutdeals.in/index.php/api/default-enum";
//
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
//                url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
//
//                        if (!TextUtils.isEmpty(response.toString())) {
//                            PreferenceManager.getDefaultSharedPreferences(context)
//                                    .edit().putString(KEY_STATIC_DATA, response.toString()).commit();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d(TAG, "Error: " + error.getMessage());
//            }
//        }) {
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = com.example.datanetworkmodule.DataPreferences.getDefaultHeaders(context);
//                headers.putAll(super.getHeaders());
//                return headers;
//            }
//        };
//
//        DineoutCollectApp.getInstance().addToRequestQueue(jsonObjReq, TAG_REQUEST_STATIC_DATA);
    }

    public CityModel getCityModelForId(int id) {
        for (CityModel cityModel : getStaticDataModel().getCity()) {
            if (cityModel.getCity_id() == id) {
                return cityModel;
            }
        }
        return null;
    }

    public ChainModel getRestaurantChainForId(int id) {
        for (ChainModel cityModel : getStaticDataModel().getChain()) {
            if (cityModel.getRestaurant_chain_id() == id) {
                return cityModel;
            }
        }
        return null;
    }

    public HotelsModel getHotelChainForId(int id) {
        for (HotelsModel cityModel : getStaticDataModel().getHotels()) {
            if (cityModel.getHotel_id() == id) {
                return cityModel;
            }
        }
        return null;
    }

    public int getRestaurantChainPositionForId(int id) {
        for (int i = 0; i < getStaticDataModel().getChain().size(); i++) {
            ChainModel cityModel = getStaticDataModel().getChain().get(i);
            if (cityModel.getRestaurant_chain_id() == id) {
                return i;
            }
        }
        return -1;
    }

    public int getHotelChainPositionForId(int id) {
        for (int i = 0; i < getStaticDataModel().getHotels().size(); i++) {
            HotelsModel cityModel = getStaticDataModel().getHotels().get(i);
            if (cityModel.getHotel_id() == id) {
                return i;
            }
        }
        return -1;
    }

}