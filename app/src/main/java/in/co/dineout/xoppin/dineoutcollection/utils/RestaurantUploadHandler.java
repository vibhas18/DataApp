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
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class RestaurantUploadHandler  {


    private String mRestID;
    private int mUploadedCount;
    private int mToUploadCount;
    private Context mContext;
    private ImageUploadHandler imageUploadHandler;
    public RestaurantUploadHandler(Context context,String id){

        mContext = context;
        mRestID = id;
        imageUploadHandler = new ImageUploadHandler(context,id);
    }

    public void initialize(){

        saveRestaurant(mRestID);
        imageUploadHandler.initialize();
    }

    private void saveRestaurant(String id){

        if(TextUtils.isEmpty(id) )
            return;

        if(!Utils.isConnected(mContext))
            return;

        String jsonRest = getSavedRestaurant(id);
        if(TextUtils.isEmpty(jsonRest))
            return;
        DineoutNetworkManager manager = DineoutNetworkManager.newInstance(mContext, "");
        Map<String,String> param = new HashMap<>();
        param.put("resturant",jsonRest);
        manager.stringRequestPost(101,"save-resturant",param,mRespListener,mErroListener,false);
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

                        DataDatabaseUtils.getInstance(mContext).markRestaurantSynced(mRestID);
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

        }
    };


}
