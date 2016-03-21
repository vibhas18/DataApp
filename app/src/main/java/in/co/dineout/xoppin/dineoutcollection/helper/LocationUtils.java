package in.co.dineout.xoppin.dineoutcollection.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by suraj on 17/01/16.
 */


public class LocationUtils implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    protected static final String TAG = LocationUtils.class.getSimpleName();

    private Context mContext;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private Handler mHandler;

    private static final int UPDATE_INTERVAL_IN_MILLISECONDS = 5 * 1000;
    private static final int EXPIRATION_DURAION_MILISECONDS = 5 * 60 * 1000; // 2
    // minutes

    private static LocationUtils instance = null;

    public static LocationUtils getInstance(Context context) {
        if (instance == null) {
            instance = new LocationUtils(context);
        }
        return instance;
    }

    private LocationUtils(Context context) {
        mContext = context;
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addApi(LocationServices.API).addConnectionCallbacks(this).addOnConnectionFailedListener(this)
                .build();
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setNumUpdates(1);
        mLocationRequest.setExpirationDuration(EXPIRATION_DURAION_MILISECONDS);
        mHandler = new Handler();
        connect();
    }

    private void connect() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(mContext).addApi(LocationServices.API).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).build();
        }
        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            mGoogleApiClient.disconnect();
        } catch (Exception e) {

        }
    }

    public Location getLastLocation() {
        mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        return mCurrentLocation;
    }

    public void getLastLocation(final LocationCallbacks locationCallbacks) {
        getLastLocation(false, null, locationCallbacks);
    }

    public void getLastLocation(final boolean openSetting, final Activity activity, final LocationCallbacks locationCallbacks) {
        if (locationCallbacks == null) {
            return;
        }

        try {

            if (mGoogleApiClient == null) {
                locationCallbacks.onError();
                if (openSetting) {
                    showLocationSettingsDialog(activity);
                }
            }

            if (mGoogleApiClient != null) {
                if (!mGoogleApiClient.isConnected()) {
                    try {
                        if (mGoogleApiClient.isConnecting()) {
                            locationCallbacks.onLocationReq();
                        } else {
                            locationCallbacks.onError();
                            if (openSetting) {
                                showLocationSettingsDialog(activity);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }

                mCurrentLocation = getLastLocation();
                if (mCurrentLocation != null) {
                    locationCallbacks.onLocationRec(mCurrentLocation);
                    return;
                }

                final LocationListener locListener = new LocationListener() {

                    @Override
                    public void onLocationChanged(Location location) {
                        locationCallbacks.onLocationRec(location);
                        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
                        mHandler.removeCallbacksAndMessages(null);
                        return;
                    }
                };
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, locListener);

                final Runnable failureCallback = new Runnable() {
                    @Override
                    public void run() {
                        Log.i(TAG, "Location Failed");
                        if (locationCallbacks != null) {
                            locationCallbacks.onError();
                            if (openSetting && activity != null && !activity.isFinishing()) {
                                showLocationSettingsDialog(activity);
                            }
                        }
                        // mLocationClient.removeLocationUpdates(locListener);
                        mHandler.removeCallbacks(this);
                    }
                };
                mHandler.postDelayed(failureCallback, 5000);

                final Runnable acknowledgementCallback = new Runnable() {
                    @Override
                    public void run() {
                        if (locationCallbacks != null) {
                            locationCallbacks.onLocationReq();
                        }
                    }
                };
                mHandler.postDelayed(acknowledgementCallback, 500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showLocationSettingsDialog(final Activity activity) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);

        alertDialogBuilder.setTitle("Location Disabled");
        alertDialogBuilder.setMessage("This app needs location for complete functionality. Enable Location for app.")
                .setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent viewIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        activity.startActivity(viewIntent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle args) {
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (Exception e) {
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
    }

    public interface LocationCallbacks {
        public void onLocationReq();

        public void onLocationRec(Location location);

        public void onError();
    }

    @Override
    protected void finalize() throws Throwable {
        disconnect();
        super.finalize();
    }

    @Override
    public void onConnectionSuspended(int arg0) {

    }

}
