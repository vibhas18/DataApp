package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.model.ChainModel;
import in.co.dineout.xoppin.dineoutcollection.model.FeatureModel;
import in.co.dineout.xoppin.dineoutcollection.model.HotelsModel;
import in.co.dineout.xoppin.dineoutcollection.model.TagModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

public class DetailFragment extends BaseStepFragment  {
    private static final String TAG = DetailFragment.class.getSimpleName();
    public static final String TAG2 = DetailFragment.class.getCanonicalName();


    private Spinner spn_restaurant;
    private Spinner spn_hotel;

    private EditText et_restaurant_description;
    private EditText et_cft;

    private CheckBox cb_smoking;
    private CheckBox cb_ac;
    private CheckBox cb_dj;
    private CheckBox cb_wifi;
    private CheckBox cb_valet;
    private CheckBox cb_home;
    private CheckBox cb_alcohol;

    private CheckBox cb_bar;
    private CheckBox cb_romantic;
    private CheckBox cb_five_star;
    private CheckBox cb_brekfast;
    private CheckBox cb_candle;
    private CheckBox cb_fine;
    private CheckBox cb_happy;
    private CheckBox cb_hookah;
    private CheckBox cb_music;
    private CheckBox cb_lounge;
    private CheckBox cb_brewery;
    private CheckBox cb_out;
    private CheckBox cb_dining;
    private CheckBox cb_pub;
    private CheckBox cb_screening;
    private CheckBox cb_seafood;
    private CheckBox cb_sunday_brunch;
    private CheckBox cb_buffet;
    private CheckBox cb_roof;
    private CheckBox cb_cafe;
    private CheckBox cb_kids;
    private CheckBox cb_luxury;
    private CheckBox cb_carlsberg;
    private CheckBox cb_dance;
    private CheckBox cb_dog;
    private CheckBox cb_karaoke;
    private CheckBox cb_pool;
    private CheckBox cb_sea_facing;
    private CheckBox cb_lunch_buffet;
    private CheckBox cb_dinner_buffet;
    private CheckBox cb_veg;
    private CheckBox cb_plus;



    //elements
    private ChainModel restaurantChain = null;
    private HotelsModel hotelsModel = null;



    public static DetailFragment create() {
        DetailFragment fragment = new DetailFragment();

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    @Override
    public void onStop() {
        super.onStop();

        saveDataForStep();
    }

    private void initView(View view) {
        spn_restaurant = (Spinner) view.findViewById(R.id.spn_restaurant);
        spn_hotel = (Spinner) view.findViewById(R.id.spn_hotel);

        et_restaurant_description = (EditText) view.findViewById(R.id.et_restaurant_description);
        et_cft = (EditText) view.findViewById(R.id.et_cft);

        //features
        cb_smoking = (CheckBox) view.findViewById(R.id.cb_smoking);
        cb_ac = (CheckBox) view.findViewById(R.id.cb_ac);
        cb_dj = (CheckBox) view.findViewById(R.id.cb_dj);
        cb_wifi = (CheckBox) view.findViewById(R.id.cb_wifi);
        cb_valet = (CheckBox) view.findViewById(R.id.cb_valet);
        cb_home = (CheckBox) view.findViewById(R.id.cb_home);
        cb_alcohol = (CheckBox) view.findViewById(R.id.cb_alcohol);


        //tags
        cb_bar = (CheckBox) view.findViewById(R.id.cb_bar);
        cb_romantic = (CheckBox) view.findViewById(R.id.cb_romantic);
        cb_five_star = (CheckBox) view.findViewById(R.id.cb_five_star);
        cb_brekfast = (CheckBox) view.findViewById(R.id.cb_brekfast);
        cb_candle = (CheckBox) view.findViewById(R.id.cb_candle);
        cb_fine = (CheckBox) view.findViewById(R.id.cb_fine);
        cb_happy = (CheckBox) view.findViewById(R.id.cb_happy);
        cb_hookah = (CheckBox) view.findViewById(R.id.cb_hookah);
        cb_music = (CheckBox) view.findViewById(R.id.cb_music);
        cb_lounge = (CheckBox) view.findViewById(R.id.cb_lounge);
        cb_brewery = (CheckBox) view.findViewById(R.id.cb_brewery);
        cb_out = (CheckBox) view.findViewById(R.id.cb_out);
        cb_dining = (CheckBox) view.findViewById(R.id.cb_dining);
        cb_pub = (CheckBox) view.findViewById(R.id.cb_pub);
        cb_screening = (CheckBox) view.findViewById(R.id.cb_screening);
        cb_seafood = (CheckBox) view.findViewById(R.id.cb_seafood);
        cb_sunday_brunch = (CheckBox) view.findViewById(R.id.cb_sunday_brunch);
        cb_buffet = (CheckBox) view.findViewById(R.id.cb_buffet);
        cb_roof = (CheckBox) view.findViewById(R.id.cb_roof);
        cb_cafe = (CheckBox) view.findViewById(R.id.cb_cafe);
        cb_kids = (CheckBox) view.findViewById(R.id.cb_kids);
        cb_luxury = (CheckBox) view.findViewById(R.id.cb_luxury);
        cb_carlsberg = (CheckBox) view.findViewById(R.id.cb_carlsberg);
        cb_dance = (CheckBox) view.findViewById(R.id.cb_dance);
        cb_dog = (CheckBox) view.findViewById(R.id.cb_dog);
        cb_karaoke = (CheckBox) view.findViewById(R.id.cb_karaoke);
        cb_pool = (CheckBox) view.findViewById(R.id.cb_pool);
        cb_sea_facing = (CheckBox) view.findViewById(R.id.cb_sea_facing);
        cb_lunch_buffet = (CheckBox) view.findViewById(R.id.cb_lunch_buffet);
        cb_dinner_buffet = (CheckBox) view.findViewById(R.id.cb_dinner_buffet);
        cb_veg = (CheckBox) view.findViewById(R.id.cb_veg);
        cb_plus = (CheckBox) view.findViewById(R.id.cb_plus);

        initRestaurantSpinner();
        initHotelSpinner();
        populateViewFromData();



    }


    private void initRestaurantSpinner() {
        spn_restaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(view != null && view.getTag() != null)
                restaurantChain = (ChainModel) view.getTag();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                restaurantChain = null;
            }
        });

        RestSpinnerAdapter restSpinnerAdapter = new RestSpinnerAdapter(getActivity(), StaticDataHandler.getInstance().getStaticDataModel().getChain());
        spn_restaurant.setAdapter(restSpinnerAdapter);
    }

    private void initHotelSpinner() {
        spn_hotel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hotelsModel = (HotelsModel) view.getTag();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                hotelsModel = null;
            }
        });

        HotelSpinnerAdapter hotelSpinnerAdapter = new HotelSpinnerAdapter(getActivity(), StaticDataHandler.getInstance().getStaticDataModel().getHotels());
        spn_hotel.setAdapter(hotelSpinnerAdapter);
    }

    private void setTags(List<TagModel> tagModels) {
        if (null == tagModels || tagModels.size() == 0) {
            return;
        }

        for (TagModel tagModel : tagModels) {
            if (tagModel.getTag_name().equalsIgnoreCase("Bar")) {
                cb_bar.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Romantic")) {
                cb_romantic.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("5 Star")) {
                cb_five_star.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Breakfast")) {
                cb_brekfast.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Candle Light Dinner")) {
                cb_candle.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Fine Dining")) {
                cb_fine.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Happy Hours")) {
                cb_happy.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Hookah")) {
                cb_hookah.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Live Music")) {
                cb_music.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Lounge")) {
                cb_lounge.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Microbrewery")) {
                cb_brewery.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Outdoor Seating")) {
                cb_out.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Private Dining Available")) {
                cb_dining.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Pub")) {
                cb_pub.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Screening")) {
                cb_screening.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Seafood")) {
                cb_seafood.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Sunday Brunch")) {
                cb_sunday_brunch.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Buffet")) {
                cb_buffet.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Roof Top")) {
                cb_roof.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Cafe")) {
                cb_cafe.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Kids Friendly")) {
                cb_kids.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Luxury Dining")) {
                cb_luxury.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Carlsberg")) {
                cb_carlsberg.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Dance Floor")) {
                cb_dance.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Dog Friendly")) {
                cb_dog.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Karaoke")) {
                cb_karaoke.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Poolside")) {
                cb_pool.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Sea Facing")) {
                cb_sea_facing.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Lunch Buffet")) {
                cb_lunch_buffet.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Dinner Buffet")) {
                cb_dinner_buffet.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Pure Veg")) {
                cb_veg.setChecked(true);
            }
            if (tagModel.getTag_name().equalsIgnoreCase("Dineout Plus")) {
                cb_plus.setChecked(true);
            }
        }

    }

    private ArrayList<TagModel> getTags() {
        ArrayList<TagModel> tagsList = new ArrayList<>();
        if (cb_bar.isChecked()) {
            tagsList.add(new TagModel("Bar"));
        }
        if (cb_romantic.isChecked()) {
            tagsList.add(new TagModel("Romantic"));
        }
        if (cb_five_star.isChecked()) {
            tagsList.add(new TagModel("5 Star"));
        }
        if (cb_brekfast.isChecked()) {
            tagsList.add(new TagModel("Breakfast"));
        }
        if (cb_candle.isChecked()) {
            tagsList.add(new TagModel("Candle Light Dinner"));
        }
        if (cb_fine.isChecked()) {
            tagsList.add(new TagModel("Fine Dining"));
        }
        if (cb_happy.isChecked()) {
            tagsList.add(new TagModel("Happy Hours"));
        }
        if (cb_hookah.isChecked()) {
            tagsList.add(new TagModel("Hookah"));
        }
        if (cb_music.isChecked()) {
            tagsList.add(new TagModel("Live Music"));
        }
        if (cb_lounge.isChecked()) {
            tagsList.add(new TagModel("Lounge"));
        }
        if (cb_brewery.isChecked()) {
            tagsList.add(new TagModel("Microbrewery"));
        }
        if (cb_out.isChecked()) {
            tagsList.add(new TagModel("Outdoor Seating"));
        }
        if (cb_dining.isChecked()) {
            tagsList.add(new TagModel("Private Dining Available"));
        }
        if (cb_pub.isChecked()) {
            tagsList.add(new TagModel("Pub"));
        }
        if (cb_screening.isChecked()) {
            tagsList.add(new TagModel("Screening"));
        }
        if (cb_seafood.isChecked()) {
            tagsList.add(new TagModel("Seafood"));
        }
        if (cb_sunday_brunch.isChecked()) {
            tagsList.add(new TagModel("Sunday Brunch"));
        }
        if (cb_buffet.isChecked()) {
            tagsList.add(new TagModel("Buffet"));
        }
        if (cb_roof.isChecked()) {
            tagsList.add(new TagModel("Roof Top"));
        }
        if (cb_cafe.isChecked()) {
            tagsList.add(new TagModel("Cafe"));
        }
        if (cb_kids.isChecked()) {
            tagsList.add(new TagModel("Kids Friendly"));
        }
        if (cb_luxury.isChecked()) {
            tagsList.add(new TagModel("Luxury Dining"));
        }
        if (cb_carlsberg.isChecked()) {
            tagsList.add(new TagModel("Carlsberg"));
        }
        if (cb_dance.isChecked()) {
            tagsList.add(new TagModel("Dance Floor"));
        }
        if (cb_dog.isChecked()) {
            tagsList.add(new TagModel("Dog Friendly"));
        }
        if (cb_karaoke.isChecked()) {
            tagsList.add(new TagModel("Karaoke"));
        }
        if (cb_pool.isChecked()) {
            tagsList.add(new TagModel("Poolside"));
        }
        if (cb_sea_facing.isChecked()) {
            tagsList.add(new TagModel("Sea Facing"));
        }
        if (cb_lunch_buffet.isChecked()) {
            tagsList.add(new TagModel("Lunch Buffet"));
        }
        if (cb_dinner_buffet.isChecked()) {
            tagsList.add(new TagModel("Dinner Buffet"));
        }
        if (cb_veg.isChecked()) {
            tagsList.add(new TagModel("Pure Veg"));
        }
        if (cb_plus.isChecked()) {
            tagsList.add(new TagModel("Dineout Plus"));
        }
        return tagsList;
    }

    private void setFeatures(List<FeatureModel> features) {
        if (features == null || features.size() == 0) {
            return;
        }
        for (FeatureModel feature : features) {
            if (feature.getName().equalsIgnoreCase("Smoking Area")) {
                cb_smoking.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("Air Conditioned")) {
                cb_ac.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("DJ")) {
                cb_dj.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("Wifi")) {
                cb_wifi.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("Valet available")) {
                cb_valet.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("Home Delivery")) {
                cb_home.setChecked(true);
            }
            if (feature.getName().equalsIgnoreCase("Serves Alcohol")) {
                cb_alcohol.setChecked(true);
            }
        }
    }

    private ArrayList<FeatureModel> getFeatures() {
        ArrayList<FeatureModel> featuresList = new ArrayList<>(5);
        if (cb_smoking.isChecked()) {
            featuresList.add(new FeatureModel("Smoking Area"));
        }
        if (cb_ac.isChecked()) {
            featuresList.add(new FeatureModel("Air Conditioned"));
        }
        if (cb_dj.isChecked()) {
            featuresList.add(new FeatureModel("DJ"));
        }
        if (cb_wifi.isChecked()) {
            featuresList.add(new FeatureModel("Wifi"));
        }
        if (cb_valet.isChecked()) {
            featuresList.add(new FeatureModel("Valet available"));
        }
        if (cb_home.isChecked()) {
            featuresList.add(new FeatureModel("Home Delivery"));
        }
        if (cb_alcohol.isChecked()) {
            featuresList.add(new FeatureModel("Serves Alcohol"));
        }
        return featuresList;
    }




    @Override
    public void saveDataForStep() {

        if(getActivity() == null)
            return;
        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();
        if (hotelsModel != null) {
            restaurant.setHotel_id(hotelsModel.getHotel_id());
        }else{
            restaurant.setHotel_id(-1);
        }
        if (restaurantChain != null) {
            restaurant.setRestaurant_chain_id(restaurantChain.getRestaurant_chain_id());
        }else{
                restaurant.setRestaurant_chain_id(-1);

        }
        if (!TextUtils.isEmpty(et_restaurant_description.getText().toString().trim())) {
            restaurant.setDescription(et_restaurant_description.getText().toString().trim());
        }else{
            restaurant.setDescription("");
        }
        if (!TextUtils.isEmpty(et_cft.getText().toString().trim())) {
            restaurant.setCost_for_2(Integer.parseInt(et_cft.getText().toString().trim()));
        }else{
                restaurant.setCost_for_2(0);

        }
        if(getTags() != null && getTags().size()>0 )
        restaurant.setTags(getTags());
        else
        restaurant.setTags(new ArrayList<TagModel>());

        if(getFeatures() != null && getFeatures().size()>0)
        restaurant.setFeatures(getFeatures());
        else
            restaurant.setFeatures(new ArrayList<FeatureModel>());
//        ((RestaurantFormActivity)getActivity()).saveRestaurantModel();
    }

    @Override
    public void populateViewFromData() {
        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();

        int hotelPosition = StaticDataHandler.getInstance().getHotelChainPositionForId(restaurant.getHotel_id());
        if (hotelPosition != -1) {
            spn_hotel.setSelection(hotelPosition);
        }

        int restPosition = StaticDataHandler.getInstance().getRestaurantChainPositionForId(restaurant.getRestaurant_chain_id());
        if (restPosition != -1) {
            spn_restaurant.setSelection(restPosition);
        }

        et_restaurant_description.setText(restaurant.getDescription());
        et_cft.setText("" + restaurant.getCost_for_2());

        setTags(restaurant.getTags());
        setFeatures(restaurant.getFeatures());
//        notifyChanges();


    }


    private class RestSpinnerAdapter extends ArrayAdapter<ChainModel> {
        public RestSpinnerAdapter(Context context, List<ChainModel> objects) {
            super(context, R.layout.row_generic_list_sigle_select, R.id.textView, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_generic_list_sigle_select, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.textView)).setText(getItem(position).getRestaurant_name());
            convertView.setTag(getItem(position));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_generic_list_sigle_select, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.textView)).setText(getItem(position).getRestaurant_name());
            convertView.setTag(getItem(position));
            return convertView;
        }
    }

    private class HotelSpinnerAdapter extends ArrayAdapter<HotelsModel> {
        public HotelSpinnerAdapter(Context context, List<HotelsModel> objects) {
            super(context, R.layout.row_generic_list_sigle_select, R.id.textView, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_generic_list_sigle_select, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.textView)).setText(getItem(position).getName());
            convertView.setTag(getItem(position));
            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_generic_list_sigle_select, parent, false);
            }
            ((TextView) convertView.findViewById(R.id.textView)).setText(getItem(position).getName());
            convertView.setTag(getItem(position));
            return convertView;
        }
    }
}
