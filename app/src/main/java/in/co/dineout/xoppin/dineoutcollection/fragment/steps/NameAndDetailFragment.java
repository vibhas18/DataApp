package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.content.DialogInterface;
import android.location.Location;
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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.RestaurantFormActivity;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.fragment.GenericListSingleSelectFragment;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.helper.LocationUtils;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.GenericModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.AreaModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.LocalityModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

public class NameAndDetailFragment extends BaseStepFragment {
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

    public NameAndDetailFragment() {
        // Required empty public constructor
    }

    public static NameAndDetailFragment newInstance() {
        NameAndDetailFragment fragment = new NameAndDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        initView(view);
        populateViewFromData();


    }

    private void refreshLocation() {
        LocationUtils.getInstance(getActivity()).getLastLocation(new LocationUtils.LocationCallbacks() {
            @Override
            public void onLocationReq() {

            }

            @Override
            public void onLocationRec(Location l) {
                if (null == getActivity() || isRemoving()) {
                    return;
                }
                Toast.makeText(getActivity(), "Location Recieved", Toast.LENGTH_SHORT).show();
                latitude = l.getLatitude();
                longitude = l.getLongitude();

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

            @Override
            public void onError() {

            }
        });
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
                                        try {
                                            latitude = Double.parseDouble(latText.getText().toString().trim());
                                            longitude = Double.parseDouble(lonText.getText().toString().trim());

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
                                        } catch (Exception e) {
                                            Toast.makeText(getActivity(), "Please enter only valit lat & long", Toast.LENGTH_SHORT).show();
                                        }
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

                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.newInstance((ArrayList) DatabaseManager.getInstance().getAreaForCityId(cityModel.getCity_id()), "Select Area");
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
                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.newInstance((ArrayList) DatabaseManager.getInstance().getLocalityForAreaId(areaModel.getId()), "Select Locality");
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

        tv_refresh_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshLocation();
            }
        });

    }

    @Override
    public void onStepChanged() {

    }

    @Override
    public void saveDataForStep() {
        if (TextUtils.isEmpty(et_profile_name.getText().toString())) {
            Toast.makeText(getActivity(), "To save the restaurant, you need to enter the name", Toast.LENGTH_LONG).show();
        } else {
            //TODO do save and tan-tada-dan
            Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();

            if (!TextUtils.isEmpty(et_profile_name.getText().toString().trim())) {
                restaurant.setProfile_name(et_profile_name.getText().toString().trim());
                ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel().setRestaurantName(et_profile_name.getText().toString().trim());
            }

            if (!TextUtils.isEmpty(et_screen_name.getText().toString().trim())) {
                restaurant.setScreen_name(et_screen_name.getText().toString().trim());
            }

            if (!TextUtils.isEmpty(et_screen_name_mobile.getText().toString().trim())) {
                restaurant.setScreen_name_mobile(et_screen_name_mobile.getText().toString().trim());
            }

            if (!TextUtils.isEmpty(et_restaurant_address.getText().toString().trim())) {
                restaurant.setAddress(et_restaurant_address.getText().toString().trim());
            }

            if (!TextUtils.isEmpty(et_restaurant_landmark.getText().toString().trim())) {
                restaurant.setLandmark(et_restaurant_landmark.getText().toString().trim());
            }

            if (!TextUtils.isEmpty(et_pincode.getText().toString().trim())) {
                restaurant.setPincode(et_pincode.getText().toString().trim());
            }

            if (null != cityModel) {
                restaurant.setCity_id("" + cityModel.getCity_id());
            }

            if (null != areaModel) {
                restaurant.setArea_id("" + areaModel.getId());
            }

            if (null != localityModel) {
                restaurant.setLocality_id("" + localityModel.getId());
            }

            restaurant.setLatitude("" + latitude);
            restaurant.setLongitude("" + longitude);

            ((RestaurantFormActivity) getActivity()).saveRestaurantModel();
        }
    }

    @Override
    public void populateViewFromData() {
        Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();

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
            areaModel = DatabaseManager.getInstance().getAreaForId(restaurant.getArea_id());
            if (null != areaModel) {
                tv_area.setText(areaModel.getName());
            }
        }

        if (!TextUtils.isEmpty(restaurant.getLocality_id())) {
            localityModel = DatabaseManager.getInstance().getLocalityForId(restaurant.getLocality_id());
            if (null != localityModel) {
                tv_locality.setText(localityModel.getName());
            }
        }

        if (TextUtils.isEmpty(restaurant.getLatitude()) && TextUtils.isEmpty(restaurant.getLatitude())) {
            refreshLocation();
        } else {
            latitude = Double.parseDouble(restaurant.getLatitude());
            longitude = Double.parseDouble(restaurant.getLongitude());

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

    }
}
