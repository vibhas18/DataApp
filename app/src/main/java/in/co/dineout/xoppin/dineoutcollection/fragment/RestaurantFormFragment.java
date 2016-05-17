package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GenericTabsPagerAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.model.AbstractWizardModel;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.model.PageFragmentCallbacks;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.RestaurantWizardModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.pages.ModelCallbacks;
import in.co.dineout.xoppin.dineoutcollection.model.pages.Page;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;
import in.co.dineout.xoppin.dineoutcollection.views.StepPagerStrip;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class RestaurantFormFragment extends MasterDataFragment implements PageFragmentCallbacks,ModelCallbacks,ViewPager.OnPageChangeListener,View.OnClickListener,StepPagerStrip.OnPageSelectedListener {

    public static final String ACTION_NEW_RESTAURANT = "ACTION_NEW_RESTAURANT";
    public static final String ACTION_UPDATE_ASSIGNED_RESTAURANT = "ACTION_UPDATE_ASSIGNED_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT = "ACTION_UPDATE_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED = "ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED";

    public static final String KEY_RESTAURANT_DATA = "KEY_RESTAURANT_DATA";
    public static final String KEY_RESTAURANT_DETAILS_DATA_ID = "KEY_RESTAURANT_DETAILS_DATA_ID";


    private GenericTabsPagerAdapter mPagerAdapter;

    private RestaurantDetailsModel restaurantDetailsModel =null;

    private ViewPager mPager;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new RestaurantWizardModel(getActivity());

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;
    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;


    public static RestaurantFormFragment newInstance(RestaurantDetailsModel model){

        if(model == null)
            model = new RestaurantDetailsModel();


        RestaurantFormFragment fragment = new RestaurantFormFragment();
        fragment.restaurantDetailsModel = model;
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
        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        initializeView(getView());
        onPageTreeChanged();
        updateBottomBar();
    }

    private void initializeView(View v){

        mWizardModel.registerListener(this);

        mPagerAdapter = new GenericTabsPagerAdapter(getChildFragmentManager(),mWizardModel.getCurrentPageSequence());
        mPager = (ViewPager) v.findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) v.findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(this);

        mNextButton = (Button) v.findViewById(R.id.next_button);
        mPrevButton = (Button) v.findViewById(R.id.prev_button);
        mNextButton.setOnClickListener(this);
        mPrevButton.setOnClickListener(this);
    }

    @Override
    public void onPageDataChanged(Page page) {

        int previous = mPager.getCurrentItem();
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
//                mPagerAdapter.notifyDataSetChanged();
                mPager.setCurrentItem(previous);
                updateBottomBar();
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    @Override
    public void onPageTreeChanged() {

        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.updateData(mCurrentPageSequence);
        updateBottomBar();
    }



    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        mStepPagerStrip.setCurrentPage(position);

        if (mConsumePageSelectedEvent) {
            mConsumePageSelectedEvent = false;
            return;
        }

        mEditingAfterReview = false;
        updateBottomBar();
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
        if(v == mNextButton){
            handleNextAction();
        }else if(v == mPrevButton){
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void handleNextAction(){

        if (mPager.getCurrentItem() == mCurrentPageSequence.size() ) {

            DataDatabaseUtils utils = DataDatabaseUtils.getInstance(getActivity());
            if(utils.hasProfileImages(restaurantDetailsModel.getRestaurantId())
                    && utils.hasMenuImages(restaurantDetailsModel.getRestaurantId())){

                addMenuImages();
                addProfileImages();

                DialogFragment dg = new DialogFragment() {
                    @Override
                    public Dialog onCreateDialog(Bundle savedInstanceState) {
                        return new AlertDialog.Builder(getActivity())
                                .setMessage(R.string.submit_confirm_message)
                                .setPositiveButton(R.string.submit_confirm_button, new Dialog.OnClickListener(){

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        sendRestaurantForSyncing();
                                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                                    }
                                })
                                .setNegativeButton(android.R.string.cancel, null)
                                .create();
                    }
                };
                dg.show(getActivity().getSupportFragmentManager(), "sync_data_dialog");
            }else{
                Toast.makeText(getActivity(),"Please add profile and menu images", Toast.LENGTH_LONG).show();
            }
        } else {
            if (mEditingAfterReview) {
                mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
            } else {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }
        }
    }

    @Override
    public void onPageStripSelected(int position) {

        position = Math.min(mPagerAdapter.getCount() - 1, position);
        if (mPager.getCurrentItem() != position) {
            mPager.setCurrentItem(position);
        }
    }


    private void updateBottomBar() {
        int position = mPager.getCurrentItem();

        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(getActivity(), R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();

            getActivity().getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);

            mNextButton.setTextAppearance(getActivity(), v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    private boolean recalculateCutOffPage() {
        // Cut off the pager adapter at first required page that isn't completed
        int cutOffPage = mCurrentPageSequence.size() + 1;
        for (int i = 0; i < mCurrentPageSequence.size(); i++) {
            Page page = mCurrentPageSequence.get(i);
            if (page.isRequired() && !page.isCompleted()) {
                cutOffPage = i;
                break;
            }
        }

        if (mPagerAdapter.getCutOffPage() != cutOffPage) {
            mPagerAdapter.setCutOffPage(cutOffPage);
            return true;
        }

        return false;
    }

    public AbstractWizardModel onGetModel() {
        return mWizardModel;
    }


    public void onEditScreenAfterReview(String key) {
        for (int i = mCurrentPageSequence.size() - 1; i >= 0; i--) {
            if (mCurrentPageSequence.get(i).getKey().equals(key)) {
                mConsumePageSelectedEvent = true;
                mEditingAfterReview = true;
                mPager.setCurrentItem(i);
                updateBottomBar();
                break;
            }
        }
    }

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

        Utils.sendToSync(getActivity(),restaurantDetailsModel);
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
