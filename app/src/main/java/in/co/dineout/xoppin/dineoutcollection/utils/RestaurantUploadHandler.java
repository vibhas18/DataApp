package in.co.dineout.xoppin.dineoutcollection.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dineout.android.volley.Request;
import com.dineout.android.volley.Response;
import com.dineout.android.volley.VolleyError;
import com.example.datanetworkmodule.DineoutNetworkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.helper.SaveToTextLog;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class RestaurantUploadHandler implements ImageUploadCallback  {


    private String mRestID;
    private int mUploadedCount;
    private int mToUploadCount;
    private Context mContext;
    private ImageUploadHandler imageUploadHandler;
    private RestaurantDetailsModel mRestModel;
    private DineoutNetworkManager mManager;
    public RestaurantUploadHandler(Context context,String id,DineoutNetworkManager manager){

        mContext = context;
        mRestID = id;
        imageUploadHandler = new ImageUploadHandler(context,id,this);
        mManager = manager;

    }

    public synchronized void initialize(){


        imageUploadHandler.initialize();
//        saveRestaurant(mRestID);
    }

    private synchronized void saveRestaurant(String id){

        if(TextUtils.isEmpty(id) )
            return;

        if(!Utils.isConnected(mContext))
            return;
        String jsonRest = getSavedRestaurant(id);
        if(TextUtils.isEmpty(jsonRest))
            return;
        mRestModel = new RestaurantDetailsModel(jsonRest);
        mRestModel.setRestaurantId(mRestID);

            if(mRestModel.validateRestaurantWithoutToast(mContext) != -1)
                return;
            else
                mRestModel.validateRestaurant(mContext);

            Map<String,String> param = new HashMap<>();
            param.put("resturant",jsonRest);
            mManager.stringRequestPost(900,
                    "save-resturant",param,mRespListener,mErroListener,false);

    }

    private String getSavedRestaurant(String id){

        RestaurantDetailsModel model =  DataDatabaseUtils.getInstance(mContext)
                .getRestaurantModelForId(id);
        if(model != null)
            return model.getRestaurantJSONString();

        return null;
    }

    private Response.Listener<String> mRespListener = new Response.Listener<String>() {
        @Override
        public void onResponse(Request<String> request, String responseObject, Response<String> response) {

            if(responseObject != null){
                try {
                    JSONObject resp = new JSONObject(responseObject);
                    if(resp.optInt("status") == 1){

                        SaveToTextLog.saveLogData(mRestModel.getRestaurantName(),mRestModel.getRestaurantJSONString(),mContext);
                        DataDatabaseUtils.getInstance(mContext).markRestaurantSynced(mRestID);
//                        DataDatabaseUtils.getInstance(mContext).deleteSyncedImagesForRestaurant(mRestID);
                    }else{
                        DataDatabaseUtils.getInstance(mContext).markRestaurantPending(mRestID);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    } ;

    private Response.ErrorListener mErroListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(Request request, VolleyError error) {

            if(mRestModel.validateRestaurantWithoutToast(mContext) == -1)
                DataDatabaseUtils.getInstance(mContext).markRestaurantUnsynced(mRestID);
            else
                DataDatabaseUtils.getInstance(mContext).markRestaurantPending(mRestID);
        }
    };


    @Override
    public void initiateSaveRestaurant() {

        String jsonRest = getSavedRestaurant(mRestID);
        if(TextUtils.isEmpty(jsonRest))
            return;

        mRestModel = new RestaurantDetailsModel(jsonRest);
        mRestModel.setRestaurantId(mRestID);
        if(mRestModel.validateRestaurantWithoutToast(mContext) != -1)
            return;
        else{
            mRestModel.validateRestaurant(mContext);
            DataDatabaseUtils.getInstance(mContext).
                    saveRestaurantForSyncing(mRestID, mRestModel.getRestaurantName(), mRestModel.getRestaurantJSONString());

        }

        saveRestaurant(mRestID);
    }
}
