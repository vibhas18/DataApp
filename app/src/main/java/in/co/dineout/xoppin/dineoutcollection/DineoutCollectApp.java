package in.co.dineout.xoppin.dineoutcollection;

import android.app.Application;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.helper.LocationUtils;
import in.co.dineout.xoppin.dineoutcollection.imageupload.AmazonS3Handler;

/**
 * Created by suraj on 03/02/16.
 */
public class DineoutCollectApp extends Application {

    public static final String TAG = DineoutCollectApp.class
            .getSimpleName();

    private RequestQueue mRequestQueue;

    private static DineoutCollectApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        StaticDataHandler.init(this);
        DatabaseManager.init(this);
        AmazonS3Handler.init(this);

        LocationUtils.getInstance(getApplicationContext()).getLastLocation(new LocationUtils.LocationCallbacks() {
            @Override
            public void onLocationReq() {
                Log.i(TAG, "location requested");
            }

            @Override
            public void onLocationRec(Location location) {
                Log.i(TAG, "location received");
            }

            @Override
            public void onError() {
                Log.e(TAG, "location error");
            }
        });

    }

    public static synchronized DineoutCollectApp getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
}
