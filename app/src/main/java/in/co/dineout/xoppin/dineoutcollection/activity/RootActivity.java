package in.co.dineout.xoppin.dineoutcollection.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.datanetworkmodule.DataPreferences;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.DashboardFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.LoginFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.MasterDataFragment;
import in.co.dineout.xoppin.dineoutcollection.utils.MasterFragmentTransactionHelper;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class RootActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,LocationListener {


    private GoogleApiClient googleApiClient;
    private static final int REQUEST_PERMISSION_LOCATION = 0x02;
    public static final int REQUEST_CHECK_SETTINGS = 2000;
    private static final int REQUEST_RESOLVE_ERROR = 0x010;
    private Location mCurrentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_root);
        MasterDataFragment fragment = null;
        if(DataPreferences.getUserId(this) != null){
            fragment = DashboardFragment.newInstance();
            initializeGoogleApiClient();
        }else{
            fragment = LoginFragment.newInstance();
        }

        if(fragment != null)
            MasterFragmentTransactionHelper.replace(getSupportFragmentManager(),
                    R.id.fragment_base_container, fragment, 0, 0, 0, 0, true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectClient();
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(googleApiClient != null && googleApiClient.isConnected()){
            googleApiClient.disconnect();
        }
        googleApiClient = null;
    }

    public void connectClient(){
        if(googleApiClient == null)
            initializeGoogleApiClient();

        if(!googleApiClient.isConnected()){
            googleApiClient.connect();
        }else{
            getLastKnownLocation();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == REQUEST_PERMISSION_LOCATION){
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                  getLastKnownLocation();

                } else {

                    Toast.makeText(RootActivity.this,"Could not refresh location",Toast.LENGTH_LONG);
                }
                return;
            }
    }

    //Create Location Request
    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000); // 10 secs
        locationRequest.setFastestInterval(5000); // 5 secs
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationRequest.setNumUpdates(1);

        return locationRequest;
    }


    public void fetchUserCurrentLocation() {
        final LocationRequest locRequest = createLocationRequest();

        LocationSettingsRequest.Builder locationSettingsRequestBuilder =
                new LocationSettingsRequest.Builder();
        locationSettingsRequestBuilder.addLocationRequest(locRequest);

        // Hide Never Button
        locationSettingsRequestBuilder.setAlwaysShow(true);

        // Check Settings for Location Request
        PendingResult<LocationSettingsResult> pendingResult =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient,
                        locationSettingsRequestBuilder.build());

        // Set Location Settings Callback
        pendingResult.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                // Get Status
                Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:

                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locRequest,
                                RootActivity.this);
                        break;

                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(
                                    RootActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }

                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }


    private boolean grantLocationPermission(final Activity context) {

        Bundle bundle = new Bundle();
        bundle.putString("title", "Permission Request");
        bundle.putString("description",
                "We need location permission to detect your current location");
        bundle.putString("button", "OK");

        if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            showDialog(bundle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();

                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION},
                            REQUEST_PERMISSION_LOCATION);
                }
            });

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.

            showDialog(bundle, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION
                            },
                            REQUEST_PERMISSION_LOCATION);
                }
            });

        } else if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            showDialog(bundle,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            ActivityCompat.requestPermissions(context,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                                    },
                                    REQUEST_PERMISSION_LOCATION);
                        }
                    });

        } else {

            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSION_LOCATION);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

        return true;
    }

    private void showDialog(Bundle bundle, DialogInterface.OnClickListener listener){


        AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
        builder.setTitle(bundle.getString("title"));
        builder.setMessage(bundle.getString("description"));
        builder.setPositiveButton(bundle.getString("button"), listener);

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static boolean checkLocationPermission(Context context) {

        return ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    public void getLastKnownLocation() {
        if (checkLocationPermission(getApplicationContext())) {
            if (googleApiClient != null) {
                Location lastKnownLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                if (lastKnownLocation != null) {

                    mCurrentLocation = lastKnownLocation;
                    DataPreferences.updateCurrentLocation(this,lastKnownLocation);
                }
//                fetchUserCurrentLocation();
            }
            else {
                grantLocationPermission(this);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(!onPopBackStack()){
            finish();
        }
    }


    private void initializeGoogleApiClient(){
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    protected boolean onPopBackStack() {

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentManager.executePendingTransactions()) {
            return true;
        }

        MasterDataFragment fragment = (MasterDataFragment) fragmentManager
                .findFragmentById(R.id.fragment_base_container);

        boolean isEventHandledByFragment = fragment != null
                && fragment.onPopBackStack();

        if (fragmentManager.getBackStackEntryCount() > 1
                && !isEventHandledByFragment) {

            fragmentManager.popBackStack();
            fragmentManager.beginTransaction().remove(fragment).commit();

            return true;
        }

        return isEventHandledByFragment;
    }

    @Override
    public void onConnected(Bundle bundle) {

        getLastKnownLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

        connectClient();
    }

    @Override
    public void onLocationChanged(Location location) {

        mCurrentLocation = location;
        DataPreferences.updateCurrentLocation(this,location);
    }

    private boolean mResolvingError;
    @Override
    public void onConnectionFailed(ConnectionResult result) {

        if (mResolvingError) {
            // Already attempting to resolve an error.
            return;
        } else if (result.hasResolution()) {
            try {
                mResolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch (IntentSender.SendIntentException e) {
                // There was an error with the resolution intent. Try again.

                connectClient();
            }
        } else {
            // Show dialog using GooglePlayServicesUtil.getErrorDialog()
//            showErrorDialog(result.getErrorCode());
            mResolvingError = true;
        }
    }
}
