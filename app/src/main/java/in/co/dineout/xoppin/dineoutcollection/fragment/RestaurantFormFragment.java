package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GenericTabsPagerAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class RestaurantFormFragment extends MasterDataFragment implements ViewPager.OnPageChangeListener,View.OnClickListener {

    private GenericTabsPagerAdapter mPagerAdapter;

    private RestaurantDetailsModel restaurantDetailsModel =null;

    private ViewPager mPager;

    private boolean mEditingAfterReview;


    private boolean mConsumePageSelectedEvent;


    private PagerTitleStrip mStepPagerStrip;
    private int mCurrentCount = -1;

    public static RestaurantFormFragment newInstance(RestaurantDetailsModel model){

        if(model == null)
            model = new RestaurantDetailsModel();


        RestaurantFormFragment fragment = new RestaurantFormFragment();
        fragment.restaurantDetailsModel = model;
        return fragment;
    }


    public static RestaurantFormFragment newInstance(RestaurantDetailsModel model,int index){

        if(model == null)
            model = new RestaurantDetailsModel();


        RestaurantFormFragment fragment = new RestaurantFormFragment();
        fragment.restaurantDetailsModel = model;
        fragment.mCurrentCount = index;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_restaurant_form,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setFragmentTitle("Restaurant Form");
        initializeView(getView());

    }

    private void initializeView(View v){


        mPagerAdapter = new GenericTabsPagerAdapter(getChildFragmentManager());
        mPager = (ViewPager) v.findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (PagerTitleStrip) v.findViewById(R.id.strip);

        if(mCurrentCount > 0){

            mPager.setCurrentItem(mCurrentCount);
        }

//        mNextButton = (Button) v.findViewById(R.id.next_button);
//        mPrevButton = (Button) v.findViewById(R.id.prev_button);
//        mNextButton.setOnClickListener(this);
//        mPrevButton.setOnClickListener(this);
    }




    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {



    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStop() {
        super.onStop();

        saveRestaurantForEditing();
    }

    @Override
    public void onClick(View v) {
//        if(v == mNextButton){
//            handleNextAction();
//        }else if(v == mPrevButton){
//            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
//        }
    }

//    private void handleNextAction(){
//
//    }


    public Restaurant getRestaurant() {
        return null;
    }


    public void saveRestaurantForEditing(){

        if(restaurantDetailsModel.getMode() == DataDatabaseUtils.PENDING
                && !TextUtils.isEmpty(restaurantDetailsModel.getRestaurantName()))
            DataDatabaseUtils.getInstance(getActivity()).saveRestaurantForEditing(restaurantDetailsModel.getRestaurantId()+"",
                    restaurantDetailsModel.getRestaurantName(),restaurantDetailsModel.getRestaurantJSONString());
    }


    public void sendRestaurantForSyncing(){

        int index = restaurantDetailsModel.validateRestaurant(getActivity());
        if(index == -1){

        Utils.sendToSync(getActivity(),restaurantDetailsModel);
            popToHome(getActivity());
        }
        else
            mPager.setCurrentItem(index);
    }



    public RestaurantDetailsModel getRestaurantDetailsModel() {
        return restaurantDetailsModel;
    }

    private void addMenuImages(){

        List<ImageStatusModel> mToUpload = new ArrayList<>();
        mToUpload.addAll(DataDatabaseUtils.getInstance(getActivity()).
                getPendingImage(restaurantDetailsModel.getRestaurantId(), ImageEntry.MENU_IMAGE));
        mToUpload.addAll(DataDatabaseUtils.getInstance(getActivity()).
                getSyncedImage(restaurantDetailsModel.getRestaurantId(), ImageEntry.MENU_IMAGE));

        restaurantDetailsModel.setMenu_image(mToUpload);
    }

    private void addProfileImages(){

        List<ImageStatusModel> mToUpload = new ArrayList<>();
        mToUpload.addAll(DataDatabaseUtils.getInstance(getActivity()).
                getPendingImage(restaurantDetailsModel.getRestaurantId(), ImageEntry.PROFILE_IMAGE));
        mToUpload.addAll(DataDatabaseUtils.getInstance(getActivity()).
                getSyncedImage(restaurantDetailsModel.getRestaurantId(), ImageEntry.PROFILE_IMAGE));

        restaurantDetailsModel.setProfile_image(mToUpload);
    }

}
