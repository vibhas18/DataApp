package in.co.dineout.xoppin.dineoutcollection.model.dbmodels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.helper.ObjectMapperFactory;
import in.co.dineout.xoppin.dineoutcollection.helper.SaveToTextLog;
import in.co.dineout.xoppin.dineoutcollection.model.ImageModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by suraj on 18/02/16.
 */
@DatabaseTable
public class RestaurantDetailsModel implements Serializable {
    private static final long serialVersionUID = 5996688949184892297L;

    public RestaurantDetailsModel() {
    }

    public RestaurantDetailsModel(String test) {
        //test is dummy string to leave the default constructor alone
        this.restaurant = new Restaurant();
        try {
            this.restaurantJSONString = ObjectMapperFactory.getObjectMapper().writeValueAsString(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.sync_status = SYNC_STATUS.UPDATE;
        this.createdFetchedDate = new Date();

        saveRestaurantDetail();
    }

    public RestaurantDetailsModel(Restaurant restaurant) {
        this.restaurant = restaurant;
        try {
            this.restaurantJSONString = ObjectMapperFactory.getObjectMapper().writeValueAsString(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.restaurantName = restaurant.getProfile_name();
        this.restaurantId = restaurant.getR_id();
        this.sync_status = SYNC_STATUS.UPDATE;
        this.createdFetchedDate = new Date();

        saveRestaurantDetail();
    }

    public enum SYNC_STATUS {
        NEW, UPDATE, IN_PROCESS, SYNCED
    }

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private String restaurantName;
    @DatabaseField
    protected int restaurantId;
    @DatabaseField
    private String restaurantJSONString;

    private Restaurant restaurant;
    @DatabaseField
    private RestaurantDetailsModel.SYNC_STATUS sync_status;
    @DatabaseField
    private Date createdFetchedDate;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRestaurantName() {
        return this.restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantJSONString() {
        return this.restaurantJSONString;
    }

    public void setRestaurantJSONString(String restaurantJSONString) {
        this.restaurantJSONString = restaurantJSONString;
    }

    public Restaurant getRestaurant() {

        if (null == restaurant && TextUtils.isEmpty(getRestaurantJSONString())) {
            Log.i("RestaurantDetailModel", "null restaurant String");
            return null;
        }

        if (null == restaurant) {
            try {
                restaurant = ObjectMapperFactory.getObjectMapper().readValue(getRestaurantJSONString(), Restaurant.class);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public RestaurantDetailsModel.SYNC_STATUS getSync_status() {
        return this.sync_status;
    }

    public void setSync_status(RestaurantDetailsModel.SYNC_STATUS sync_status) {
        this.sync_status = sync_status;
    }

    public Date getCreatedFetchedDate() {
        return this.createdFetchedDate;
    }

    public void setCreatedFetchedDate(Date createdFetchedDate) {
        this.createdFetchedDate = createdFetchedDate;
    }

    public void saveRestaurantDetail() {
        if (sync_status == SYNC_STATUS.IN_PROCESS || sync_status == SYNC_STATUS.SYNCED) {
            return;
//            Toast.makeText(DineoutCollectApp.getInstance().getApplicationContext(), "Data cannot be updated once it is in sync process", Toast.LENGTH_SHORT).show();
        }

        if (TextUtils.isEmpty(restaurantName)) {
            return;
        }
        if (null == restaurant && TextUtils.isEmpty(restaurantJSONString)) {
            return;
        }
        try {
            if (null != restaurant ) {
                restaurantJSONString = ObjectMapperFactory.getObjectMapper().writeValueAsString(restaurant);
            }
            Log.i("RestaurantDetailModel", restaurantJSONString);

            if (sync_status == SYNC_STATUS.IN_PROCESS || sync_status == SYNC_STATUS.SYNCED) {
                Toast.makeText(DineoutCollectApp.getInstance().getApplicationContext(),
                        "Restaurant already in sync process or synced, cannot be edited now", Toast.LENGTH_LONG).show();
                return;
            }

            DatabaseManager.getInstance().createOrUpdateRestaurantDetailsModel(this);

            SaveToTextLog.saveLogData(getRestaurantName(), restaurantJSONString, DineoutCollectApp.getInstance().getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSyncReady(Context context) {
        Restaurant restaurant = getRestaurant();

        if (TextUtils.isEmpty(restaurant.getProfile_name())) {
            Toast.makeText(context, "Name cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getAddress())) {
            Toast.makeText(context, "Address Cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getLocality_id())) {
            Toast.makeText(context, "Locality cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(TextUtils.isEmpty(restaurant.getPincode())){
            Toast.makeText(context, "You need to provide pin code.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getCity_id())) {
            Toast.makeText(context, "City cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getScreen_name())) {
            Toast.makeText(context, "Screen Name cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (TextUtils.isEmpty(restaurant.getScreen_name_mobile())) {
            Toast.makeText(context, "Informal name cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getArea_id())) {
            Toast.makeText(context, "Area cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getLandmark())) {
            Toast.makeText(context, "Landmark Cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(restaurant.getPhone())) {
            Toast.makeText(context, "Phone number cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if(TextUtils.isEmpty(restaurant.getMobile_number())){
//            Toast.makeText(context, "Mobile no cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }

//        if(!TextUtils.isEmpty(restaurant.getMobile_number())){
//            if(Utils.isValidMobileValid(restaurant.getMobile_number())){
//                Toast.makeText(context, "Invalid mobile no, please enter a valid mobile no", Toast.LENGTH_SHORT).show();
//            }
//        }


        if (restaurant.getCost_for_2() == 0) {
            Toast.makeText(context, "Const for 2 Cannot be null", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (restaurant.getPrimary_cuisine() == null || restaurant.getPrimary_cuisine().size() == 0) {
            Toast.makeText(context, "Select at least one primary cuisine", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (restaurant.getTags() == null || restaurant.getTags().size() == 0) {
            Toast.makeText(context, "Select at least one Tags", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (restaurant.getOpen_timing() == null
                || restaurant.getOpen_timing().getSunday() == null
                || restaurant.getOpen_timing().getMonday() == null
                || restaurant.getOpen_timing().getTuesday() == null
                || restaurant.getOpen_timing().getThursday() == null
                || restaurant.getOpen_timing().getFriday() == null
                || restaurant.getOpen_timing().getSaturday() == null) {
            Toast.makeText(context, "Set Timings", Toast.LENGTH_SHORT).show();
            return false;
        }

        ArrayList<SyncStatusModel> menuImages = DatabaseManager.getInstance()
                .getImagesForTypeAndRestaurant(id, SyncStatusModel.MENU);
        if (menuImages.size() == 0) {
            Toast.makeText(context, "Add menu Images", Toast.LENGTH_SHORT).show();
            return false;
        }

        ArrayList<SyncStatusModel> profileImages = DatabaseManager.getInstance()
                .getImagesForTypeAndRestaurant(id, SyncStatusModel.PROFILE);
        if (profileImages.size() == 0) {
            Toast.makeText(context, "Add profile Images", Toast.LENGTH_SHORT).show();
            return false;
        }

        for (SyncStatusModel syncStatusModel : menuImages) {
            syncStatusModel.setSyncRequested(true);
            DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
        }

        for (SyncStatusModel syncStatusModel : profileImages) {
            syncStatusModel.setSyncRequested(true);
            DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
        }

        return true;
    }

    public boolean sendToSync(Context context) {

        if (sync_status == SYNC_STATUS.IN_PROCESS || sync_status == SYNC_STATUS.SYNCED) {
            Toast.makeText(context, "Already in sync process or synced", Toast.LENGTH_SHORT).show();
            return true;

        }

        if (isSyncReady(context)) {
            SyncStatusModel syncStatusModel = new SyncStatusModel();
            syncStatusModel.setRestaurantDetailId(id);
            syncStatusModel.setType(SyncStatusModel.DATA);
            syncStatusModel.setSyncRequested(true);
            syncStatusModel.setSync_status(SYNC_STATUS.IN_PROCESS);
            DatabaseManager.getInstance().createSyncStatusModel(syncStatusModel);

            sync_status = SYNC_STATUS.IN_PROCESS;
            DatabaseManager.getInstance().createOrUpdateRestaurantDetailsModel(this);

            Toast.makeText(context, "Data sent for Syncing", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public Restaurant prepareRestaurantFoSyncing() {
        Restaurant restaurant = getRestaurant();
        ArrayList<SyncStatusModel> menuSyncStateModels = DatabaseManager.getInstance().getImagesForTypeAndRestaurant(id, SyncStatusModel.MENU);
        ArrayList<ImageModel> menuImages = new ArrayList<>(5);
        for (SyncStatusModel syncStatusModel : menuSyncStateModels) {
            menuImages.add(syncStatusModel.getImageModel());
        }
        ArrayList<SyncStatusModel> profileSyncStateModels = DatabaseManager.getInstance().getImagesForTypeAndRestaurant(id, SyncStatusModel.PROFILE);
        ArrayList<ImageModel> profileImages = new ArrayList<>(5);
        for (SyncStatusModel syncStatusModel : profileSyncStateModels) {
            profileImages.add(syncStatusModel.getImageModel());
        }

        restaurant.setProfile_image(profileImages);
        restaurant.setMenu_image(menuImages);

        return restaurant;
    }
}
