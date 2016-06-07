package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.datanetworkmodule.DataPreferences;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.RootActivity;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.fragment.GenericListSingleSelectFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.GenericModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.AreaModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.LocalityModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

public class NameAndDetailFragment extends BaseStepFragment  {
    private static final String TAG = NameAndDetailFragment.class.getSimpleName();

    private EditText et_profile_name;
    private EditText et_screen_name;
    private EditText et_screen_name_mobile;
    private EditText et_restaurant_address;
    private EditText et_restaurant_landmark;
    private EditText et_pincode;

    private TextView tv_city;
    private TextView tv_area;
    private TextView tv_locality;

    private TextView tv_refresh_address;

    private ImageView iv_map;

    //Data Elements
    private CityModel cityModel;
    private AreaModel areaModel;
    private LocalityModel localityModel;

    private double latitude;
    private double longitude;






    public static NameAndDetailFragment create() {
        NameAndDetailFragment fragment = new NameAndDetailFragment();

        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_name_and_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       ;


    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void refreshLocation() {

        ((RootActivity)getActivity()).fetchUserCurrentLocation();
        double lat = Double.parseDouble(DataPreferences.getCurrentLocationLat(getContext()));
        double lng = Double.parseDouble(DataPreferences.getCurrentLocationLong(getContext()));

        if(lat > 0 && lng > 0 ){
            latitude = lat;
            longitude = lng;
        }

    }

    private void initView(View view) {
        et_profile_name = (EditText) view.findViewById(R.id.et_profile_name);
        et_screen_name = (EditText) view.findViewById(R.id.et_screen_name);
        et_screen_name_mobile = (EditText) view.findViewById(R.id.et_screen_name_mobile);
        et_restaurant_address = (EditText) view.findViewById(R.id.et_restaurant_address);
        et_restaurant_landmark = (EditText) view.findViewById(R.id.et_restaurant_landmark);
        et_pincode = (EditText) view.findViewById(R.id.et_pincode);


        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_area = (TextView) view.findViewById(R.id.tv_area);
        tv_locality = (TextView) view.findViewById(R.id.tv_locality);

        tv_refresh_address = (TextView) view.findViewById(R.id.tv_refresh_address);
        iv_map = (ImageView) view.findViewById(R.id.iv_map);

        TextView edit = (TextView) view.findViewById(R.id.tv_edit_lat_lon);
        edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                View checkBoxView = View.inflate(getActivity(), R.layout.lat_lon_edit, null);
                final EditText latText = (EditText) checkBoxView.findViewById(R.id.edit_latitude);
                final EditText lonText = (EditText) checkBoxView.findViewById(R.id.edit_longitude);

                latText.setText(latitude + "");
                lonText.setText("" + longitude);

                new AlertDialog.Builder(getActivity())
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Lat/Lon edit Hotel")
                        .setView(checkBoxView)
                        .setMessage(
                                "Enter some manual latitude and longitude")
                        .setPositiveButton("Save",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {

                                            latitude = Double.parseDouble(latText.getText().toString().trim());
                                            longitude = Double.parseDouble(lonText.getText().toString().trim());
                                            updateMap();

                                    }

                                }).setNegativeButton("Cancel", null).show();


            }
        });

        et_profile_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                et_screen_name.setText(et_profile_name.getText().toString().trim());
            }
        });

        tv_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.newInstance((ArrayList) StaticDataHandler.getInstance().getStaticDataModel().getCity(), "Select City");
                fragment.setCallbacks(new GenericListSingleSelectFragment.Callbacks() {
                    @Override
                    public void onItemClicked(GenericModel object) {

                        if (null != object) {
                            cityModel = (CityModel) object;
                            tv_city.setText(cityModel.getName());
                        } else {
                            Toast.makeText(getActivity(), "City Not Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment, GenericListSingleSelectFragment.TAG2)
                        .addToBackStack(GenericListSingleSelectFragment.TAG2)
                        .commitAllowingStateLoss();
            }
        });

        tv_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == cityModel) {
                    Toast.makeText(getActivity(), "Select City First", Toast.LENGTH_SHORT).show();
                    return;
                }

                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.
                        newInstance((ArrayList) DataDatabaseUtils.getInstance(getContext()).getAreaForCityId(cityModel.getCity_id() + ""), "Select Area");
                fragment.setCallbacks(new GenericListSingleSelectFragment.Callbacks() {
                    @Override
                    public void onItemClicked(GenericModel object) {
                        if (null != object) {
                            areaModel = (AreaModel) object;
                            tv_area.setText(areaModel.getName());
                        } else {
                            Toast.makeText(getActivity(), "Area Not Selected", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment, GenericListSingleSelectFragment.TAG2)
                        .addToBackStack(GenericListSingleSelectFragment.TAG2)
                        .commitAllowingStateLoss();
            }
        });

        tv_locality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == areaModel) {
                    Toast.makeText(getActivity(), "Select Area First", Toast.LENGTH_SHORT).show();
                    return;
                }
                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.newInstance((ArrayList) DataDatabaseUtils.getInstance(getContext()).getLocalityForAreaId(areaModel.getId() + ""), "Select Locality");
                fragment.setCallbacks(new GenericListSingleSelectFragment.Callbacks() {
                    @Override
                    public void onItemClicked(GenericModel object) {
                        if (null != object) {
                            localityModel = (LocalityModel) object;
                            tv_locality.setText(localityModel.getName());
                        } else {
                            Toast.makeText(getActivity(), "Locality Not Selected", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment, GenericListSingleSelectFragment.TAG2)
                        .addToBackStack(GenericListSingleSelectFragment.TAG2)
                        .commitAllowingStateLoss();
            }
        });

        populateViewFromData();
        tv_refresh_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation();
            }
        });

//        registerListener();


    }

    private void updateMap(){


        Picasso.with(getActivity()).load(Utils.getMapImageUrl(latitude, longitude, "")).fit().into(iv_map, new Callback() {
            @Override
            public void onSuccess() {
                Log.i(TAG, "address Image Loaded");
            }

            @Override
            public void onError() {
                Log.e(TAG, "address image not loaded");
            }
        });
    }
//
//    private void registerListener(){
//        et_profile_name.addTextChangedListener(this);
//        et_screen_name_mobile.addTextChangedListener(this);
//        et_restaurant_address.addTextChangedListener(this);
//        et_restaurant_landmark.addTextChangedListener(this);
//        et_pincode.addTextChangedListener(this);
//        et_screen_name.addTextChangedListener(this);
//    }



    @Override
    public void saveDataForStep() {

        if(getActivity() == null){
            return;
        }
        if (TextUtils.isEmpty(et_profile_name.getText().toString())) {
            return;
        } else {
            //TODO do save and tan-tada-dan
            RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();

            if (!TextUtils.isEmpty(et_profile_name.getText().toString().trim())) {
                restaurant.updateProfileName(et_profile_name.getText().toString().trim());
//                ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel().setRestaurantName(et_profile_name.getText().toString().trim());
            }else{
                restaurant.updateProfileName("");
            }

            if (!TextUtils.isEmpty(et_screen_name.getText().toString().trim())) {
                restaurant.updateScreenName(et_screen_name.getText().toString().trim());
            }else{
                restaurant.updateScreenName("");
            }

            if (!TextUtils.isEmpty(et_screen_name_mobile.getText().toString().trim())) {
                restaurant.updateScreenNameMobile(et_screen_name_mobile.getText().toString().trim());
            }else{
                restaurant.updateScreenNameMobile("");
            }

            if (!TextUtils.isEmpty(et_restaurant_address.getText().toString().trim())) {
                restaurant.updateAddress(et_restaurant_address.getText().toString().trim());
            }else{
                restaurant.updateAddress("");
            }

            if (!TextUtils.isEmpty(et_restaurant_landmark.getText().toString().trim())) {
                restaurant.updateLandmark(et_restaurant_landmark.getText().toString().trim());
            }else{
                restaurant.updateLandmark("");
            }

            if (!TextUtils.isEmpty(et_pincode.getText().toString().trim())) {
                restaurant.updatePinCode(et_pincode.getText().toString().trim());
            }else{
                restaurant.updatePinCode("");
            }

            if (null != cityModel) {
                restaurant.updateCityId("" + cityModel.getCity_id());
            }else{
                restaurant.updateCityId("");
            }

            if (null != areaModel) {
                restaurant.updateAreaId("" + areaModel.getId());
            }else{
                restaurant.updateAreaId("");
            }

            if (null != localityModel) {
                restaurant.updateLocalityId("" + localityModel.getId());
            }else{restaurant.updateAreaId("");}

            restaurant.updateLatitude("" + latitude);
            restaurant.updateLongitude("" + longitude);

//            ((RestaurantFormActivity) getActivity()).saveRestaurantModel();
        }
    }

    @Override
    public void populateViewFromData() {
        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();

        if (!TextUtils.isEmpty(restaurant.getProfile_name())) {
            et_profile_name.setText(restaurant.getProfile_name());
        }

        if (!TextUtils.isEmpty(restaurant.getScreen_name())) {
            et_screen_name.setText(restaurant.getScreen_name());
        }

        if (!TextUtils.isEmpty(restaurant.getScreen_name_mobile())) {
            et_screen_name_mobile.setText(restaurant.getScreen_name_mobile());
        }

        if (!TextUtils.isEmpty(restaurant.getAddress())) {
            et_restaurant_address.setText(restaurant.getAddress());
        }

        if (!TextUtils.isEmpty(restaurant.getLandmark())) {
            et_restaurant_landmark.setText(restaurant.getLandmark());
        }

        if (!TextUtils.isEmpty(restaurant.getPincode())) {
            et_pincode.setText(restaurant.getPincode());
        }


        if (!TextUtils.isEmpty(restaurant.getCity_id())) {
            cityModel = StaticDataHandler.getInstance().getCityModelForId(Integer.parseInt(restaurant.getCity_id()));
            if (null != cityModel) {
                tv_city.setText(cityModel.getName());
            }
        }

        if (!TextUtils.isEmpty(restaurant.getArea_id())) {
            areaModel = DataDatabaseUtils.getInstance(getContext()).getAreaForId(restaurant.getArea_id());
            if (null != areaModel) {
                tv_area.setText(areaModel.getName());
            }
        }

        if (!TextUtils.isEmpty(restaurant.getLocality_id())) {
            localityModel = DataDatabaseUtils.getInstance(getContext()).getLocalityForId(restaurant.getLocality_id());
            if (null != localityModel) {
                tv_locality.setText(localityModel.getName());
            }
        }

        if (restaurant.getLatitude() == 0 ||restaurant.getLatitude() == 0) {
            refreshLocation();
        } else {
            latitude = restaurant.getLatitude();
            longitude = restaurant.getLongitude();

            Picasso.with(getActivity()).load(Utils.getMapImageUrl(latitude, longitude, "")).fit().into(iv_map, new Callback() {
                @Override
                public void onSuccess() {
                    Log.i(TAG, "address Image Loaded");
                }

                @Override
                public void onError() {
                    Log.e(TAG, "address image not loaded");
                }
            });
        }

//        notifyChanges();

    }



//    @Override
//    public void onFocusChange(View v, boolean hasFocus) {
//
////        saveDataForStep();
//    }
//
//    @Override
//    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//    }
//
//    @Override
//    public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//    }
//
//    @Override
//    public void afterTextChanged(Editable s) {
//
////        saveDataForStep();
//    }
}
