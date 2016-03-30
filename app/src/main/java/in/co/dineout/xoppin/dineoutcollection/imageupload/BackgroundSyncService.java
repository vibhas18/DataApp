package in.co.dineout.xoppin.dineoutcollection.imageupload;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.helper.LoginHelper;
import in.co.dineout.xoppin.dineoutcollection.helper.ObjectMapperFactory;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.SyncStatusModel;
import in.co.dineout.xoppin.dineoutcollection.utils.DineoutPostRequest;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 14/03/16.
 */
public class BackgroundSyncService extends Service implements SyncStatusCallbacks {
    private static final String TAG = BackgroundSyncService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("test", "on start service");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        executeUpload();
        Log.i("test", "on start Command");
        return START_STICKY;
    }

    private void executeUpload() {
        SyncStatusModel syncStatusModel = DatabaseManager.getInstance().getNextSyncModel();
        if (null == syncStatusModel) {
            return;
        }

        if (!syncStatusModel.getType().equalsIgnoreCase(SyncStatusModel.DATA)) {
            AmazonS3Handler.getInstance().uploadImage(syncStatusModel, this);
        } else {
            //start for restaurant data post
            saveRestaurant(syncStatusModel);
        }

    }

    @Override
    public void onSyncCompleted() {
        executeUpload();
    }

    @Override
    public void onProgressUpdate() {

    }

    @Override
    public void onErrorReceived() {
//        executeUpload();
    }

    private void saveRestaurant(final SyncStatusModel syncStatusModel) {
        String TAG_POST_RETAURANT = "TAG_POST_RETAURANT";

        final RestaurantDetailsModel restaurantDetailsModel = DatabaseManager.getInstance().getRestaurantDetailModelForId(syncStatusModel.getRestaurantDetailId());

        String url = "http://laravel.dineoutdeals.in/index.php/api/save-resturant/";

        Response.Listener<JSONObject> respListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        };


        final JSONObject jsonObject = new JSONObject();
        Map<String,String> param = new HashMap<>();
//
        try {
            String data = ObjectMapperFactory.getObjectMapper().writeValueAsString(restaurantDetailsModel.prepareRestaurantFoSyncing());
           Log.d(TAG,"----> "+data);
            param.put("resturant", data);
            jsonObject.put("resturant", data);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }



        DineoutPostRequest stringRequest = new DineoutPostRequest(Request.Method.POST, url,param, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response != null){
                    try{
                        JSONObject resp = new JSONObject(response);
                        if (null != response && Utils.getStringVal(resp, "message").equalsIgnoreCase("Login successful")) {
                            //update db
                            syncStatusModel.setSync_status(RestaurantDetailsModel.SYNC_STATUS.SYNCED);
                            DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);

                            restaurantDetailsModel.setSync_status(RestaurantDetailsModel.SYNC_STATUS.SYNCED);
                            DatabaseManager.getInstance().createOrUpdateRestaurantDetailsModel(restaurantDetailsModel);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        },errorListener){


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> param = new HashMap<>();
                param.put("resturant",jsonObject.optString("resturant"));
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                return LoginHelper.getDefaultHeaders(getBaseContext());
            }
        };

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, url, jsonObject, respListener, errorListener) {


        };

        // Adding request to request queue
        DineoutCollectApp.getInstance().addToRequestQueue(stringRequest, TAG_POST_RETAURANT);
    }

}
