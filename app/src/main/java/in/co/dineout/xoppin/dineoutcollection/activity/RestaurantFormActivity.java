package in.co.dineout.xoppin.dineoutcollection.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GenericTabsPagerAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.model.AbstractWizardModel;
import in.co.dineout.xoppin.dineoutcollection.model.PageFragmentCallbacks;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.RestaurantWizardModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.pages.ModelCallbacks;
import in.co.dineout.xoppin.dineoutcollection.model.pages.Page;
import in.co.dineout.xoppin.dineoutcollection.views.StepPagerStrip;

/**
 * Created by suraj on 16/02/16.
 */
public class RestaurantFormActivity extends AppCompatActivity implements PageFragmentCallbacks,ModelCallbacks {
    private static final String TAG = RestaurantFormActivity.class.getSimpleName();

    public static final String ACTION_NEW_RESTAURANT = "ACTION_NEW_RESTAURANT";
    public static final String ACTION_UPDATE_ASSIGNED_RESTAURANT = "ACTION_UPDATE_ASSIGNED_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT = "ACTION_UPDATE_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED = "ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED";

    public static final String KEY_RESTAURANT_DATA = "KEY_RESTAURANT_DATA";
    public static final String KEY_RESTAURANT_DETAILS_DATA_ID = "KEY_RESTAURANT_DETAILS_DATA_ID";

    private ActionMode actionMode;

    private GenericTabsPagerAdapter mPagerAdapter;

    private RestaurantDetailsModel restaurantDetailsModel;

    private ViewPager mPager;

    private boolean mEditingAfterReview;

    private AbstractWizardModel mWizardModel = new RestaurantWizardModel(this);

    private boolean mConsumePageSelectedEvent;

    private Button mNextButton;
    private Button mPrevButton;

    private List<Page> mCurrentPageSequence;
    private StepPagerStrip mStepPagerStrip;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);


        if (getIntent().getAction().equalsIgnoreCase(ACTION_NEW_RESTAURANT)) {
            restaurantDetailsModel = new RestaurantDetailsModel("test");
        } else if (getIntent().getAction().equalsIgnoreCase(ACTION_UPDATE_ASSIGNED_RESTAURANT)) {
            Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra(KEY_RESTAURANT_DATA);
            restaurantDetailsModel = new RestaurantDetailsModel(restaurant);
        } else if (getIntent().getAction().equalsIgnoreCase(ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED)) {
            int restaurantId = getIntent().getIntExtra(KEY_RESTAURANT_DETAILS_DATA_ID, -1);
            restaurantDetailsModel = DatabaseManager.getInstance().getRestaurantDetailsModelForRestaurantId(restaurantId);
        } else {
            int restaurantDetailsId = getIntent().getIntExtra(KEY_RESTAURANT_DETAILS_DATA_ID, -1);
            restaurantDetailsModel = DatabaseManager.getInstance().getRestaurantDetailModelForId(restaurantDetailsId);
        }

        if (savedInstanceState != null) {
            mWizardModel.load(savedInstanceState.getBundle("model"));
        }

        mWizardModel.registerListener(this);

        mPagerAdapter = new GenericTabsPagerAdapter(getSupportFragmentManager(),mWizardModel.getCurrentPageSequence());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mPagerAdapter);
        mStepPagerStrip = (StepPagerStrip) findViewById(R.id.strip);
        mStepPagerStrip.setOnPageSelectedListener(new StepPagerStrip.OnPageSelectedListener() {
            @Override
            public void onPageStripSelected(int position) {
                position = Math.min(mPagerAdapter.getCount() - 1, position);
                if (mPager.getCurrentItem() != position) {
                    mPager.setCurrentItem(position);
                }
            }
        });

        mNextButton = (Button) findViewById(R.id.next_button);
        mPrevButton = (Button) findViewById(R.id.prev_button);

        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPager.getCurrentItem() == mCurrentPageSequence.size()) {
                    DialogFragment dg = new DialogFragment() {
                        @Override
                        public Dialog onCreateDialog(Bundle savedInstanceState) {
                            return new AlertDialog.Builder(getActivity())
                                    .setMessage(R.string.submit_confirm_message)
                                    .setPositiveButton(R.string.submit_confirm_button, null)
                                    .setNegativeButton(android.R.string.cancel, null)
                                    .create();
                        }
                    };
                    dg.show(getSupportFragmentManager(), "place_order_dialog");
                } else {
                    if (mEditingAfterReview) {
                        mPager.setCurrentItem(mPagerAdapter.getCount() - 1);
                    } else {
                        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
                    }
                }
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
            }
        });

        onPageTreeChanged();
        updateBottomBar();
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        super.onActionModeStarted(mode);
        Log.i(TAG, "onActionModeStarted");
        this.actionMode = mode;
    }

    @Override
    public void onActionModeFinished(ActionMode mode) {
        super.onActionModeFinished(mode);
        Log.i(TAG, "onActionModeFinished");
        setActionMode(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "Save");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            //TODO call save here
//            saveRestaurantModel();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageTreeChanged() {
        mCurrentPageSequence = mWizardModel.getCurrentPageSequence();
        recalculateCutOffPage();
        mStepPagerStrip.setPageCount(mCurrentPageSequence.size() + 1); // + 1 = review step
        mPagerAdapter.updateData(mCurrentPageSequence);
        updateBottomBar();
    }

    private void updateBottomBar() {
        int position = mPager.getCurrentItem();

        if (position == mCurrentPageSequence.size()) {
            mNextButton.setText(R.string.finish);
            mNextButton.setBackgroundResource(R.drawable.finish_background);
            mNextButton.setTextAppearance(this, R.style.TextAppearanceFinish);
        } else {
            mNextButton.setText(mEditingAfterReview
                    ? R.string.review
                    : R.string.next);
            mNextButton.setBackgroundResource(R.drawable.selectable_item_background);
            TypedValue v = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.textAppearanceMedium, v, true);

            mNextButton.setTextAppearance(this, v.resourceId);
            mNextButton.setEnabled(position != mPagerAdapter.getCutOffPage());
        }

        mPrevButton.setVisibility(position <= 0 ? View.INVISIBLE : View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWizardModel.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("model", mWizardModel.save());
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

    @Override
    public void onPageDataChanged(Page page) {
        if (page.isRequired()) {
            if (recalculateCutOffPage()) {
                mPagerAdapter.notifyDataSetChanged();
              updateBottomBar();
                if(page.isCompleted()){
                    mNextButton.setEnabled(true);
                }
            }
        }
    }

    @Override
    public Page onGetPage(String key) {
        return mWizardModel.findByKey(key);
    }

    public void saveRestaurantModel() {
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

    public Restaurant getRestaurant() {
        return restaurantDetailsModel.getRestaurant();
    }

    public RestaurantDetailsModel getRestaurantDetailsModel() {
        return restaurantDetailsModel;
    }



}
