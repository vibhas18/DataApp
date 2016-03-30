package in.co.dineout.xoppin.dineoutcollection.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GenericTabsPagerAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.BaseStepFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ContactFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.CuisineFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.DetailFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ImageFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.NameAndDetailFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.TimingsFragment;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by suraj on 16/02/16.
 */
public class RestaurantFormActivity extends AppCompatActivity {
    private static final String TAG = RestaurantFormActivity.class.getSimpleName();

    public static final String ACTION_NEW_RESTAURANT = "ACTION_NEW_RESTAURANT";
    public static final String ACTION_UPDATE_ASSIGNED_RESTAURANT = "ACTION_UPDATE_ASSIGNED_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT = "ACTION_UPDATE_RESTAURANT";
    public static final String ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED = "ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED";

    public static final String KEY_RESTAURANT_DATA = "KEY_RESTAURANT_DATA";
    public static final String KEY_RESTAURANT_DETAILS_DATA_ID = "KEY_RESTAURANT_DETAILS_DATA_ID";

    private ActionMode actionMode;

    private GenericTabsPagerAdapter genericTabsPagerAdapter;

    private RestaurantDetailsModel restaurantDetailsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_form);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);


        PagerSlidingTabStrip tlTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        ViewPager vpFragmentPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(vpFragmentPager);
        tlTabs.setViewPager(vpFragmentPager);

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
    }

    private void setupViewPager(ViewPager viewPager) {
        genericTabsPagerAdapter = new GenericTabsPagerAdapter(getSupportFragmentManager());
        genericTabsPagerAdapter.addFrag(NameAndDetailFragment.newInstance(), "Basic Detail");
        genericTabsPagerAdapter.addFrag(DetailFragment.newInstance(), "Details");
        genericTabsPagerAdapter.addFrag(ContactFragment.newInstance(), "Contacts");
        genericTabsPagerAdapter.addFrag(TimingsFragment.newInstance(), "Timings");
        genericTabsPagerAdapter.addFrag(CuisineFragment.newInstance(), "Cuisine");
        genericTabsPagerAdapter.addFrag(ImageFragment.newInstance(), "Images");

        viewPager.setAdapter(genericTabsPagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ((BaseStepFragment) genericTabsPagerAdapter.getItem(position)).saveDataForStep();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
            saveRestaurantModel();
        }
        return super.onOptionsItemSelected(item);
    }

    public void saveRestaurantModel() {
        if (null != restaurantDetailsModel &&
                !TextUtils.isEmpty(restaurantDetailsModel.getRestaurantName())) {
            //save only if name is present
            restaurantDetailsModel.saveRestaurantDetail();
        }
    }

    @Override
    public void onBackPressed() {
        saveRestaurantModel();
        super.onBackPressed();
    }

    public Restaurant getRestaurant() {
        return restaurantDetailsModel.getRestaurant();
    }

    public RestaurantDetailsModel getRestaurantDetailsModel() {
        return restaurantDetailsModel;
    }

    public ActionMode getActionMode() {
        return actionMode;
    }

    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
    }

    public interface FragmentStateInformerCallback {
        void meInView();
    }
}
