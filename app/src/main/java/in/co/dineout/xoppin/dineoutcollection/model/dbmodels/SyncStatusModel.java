package in.co.dineout.xoppin.dineoutcollection.model.dbmodels;

import android.util.Log;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import in.co.dineout.xoppin.dineoutcollection.model.ImageModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel.SYNC_STATUS;

/**
 * Created by suraj on 13/03/16.
 */
@DatabaseTable
public class SyncStatusModel implements Serializable {
    private static final long serialVersionUID = 955644960080319512L;

    public static final String MENU = "MENU";
    public static final String PROFILE = "PROFILE";
    public static final String DATA = "DATA";

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int restaurantDetailId;
    @DatabaseField
    private String type; //one of menu/profile/data
    @DatabaseField
    private RestaurantDetailsModel.SYNC_STATUS sync_status;
    @DatabaseField
    private String imageLocalPath;
    @DatabaseField
    private String imageKey;
    @DatabaseField
    private int gilId;
    @DatabaseField
    private String imageState;
    @DatabaseField
    private String imageCaption;
    @DatabaseField
    private String syncedImageUrl;
    @DatabaseField
    private boolean isPreSynced;
    @DatabaseField
    private boolean deleted;
    @DatabaseField
    private boolean syncRequested = false;


    public SyncStatusModel() {
    }

    public SyncStatusModel(int restaurantDetailId, ImageModel imageModel, String type) {
        //used when we translate saved imaged from "Updating restaurants"
        this.setRestaurantDetailId(restaurantDetailId);
        this.setIsPreSynced(true);
        this.setType(type);

        this.setSyncedImageUrl(imageModel.getImage_name());
        this.setImageCaption(imageModel.getImage_caption());
        this.setImageState(imageModel.getImage_state());
        this.setGilId(imageModel.getGil_id());
        this.setSync_status(SYNC_STATUS.SYNCED);

    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRestaurantDetailId() {
        return this.restaurantDetailId;
    }

    public void setRestaurantDetailId(int restaurantDetailId) {
        this.restaurantDetailId = restaurantDetailId;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public SYNC_STATUS getSync_status() {
        return this.sync_status;
    }

    public void setSync_status(SYNC_STATUS sync_status) {
        this.sync_status = sync_status;
    }

    public String getImageLocalPath() {
        return this.imageLocalPath;
    }

    public void setImageLocalPath(String imageLocalPath) {
        this.imageLocalPath = imageLocalPath;
    }

    public String getImageKey() {
        return this.imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }

    public int getGilId() {
        return this.gilId;
    }

    public void setGilId(int gilId) {
        this.gilId = gilId;
    }

    public String getImageState() {
        return this.imageState;
    }

    public void setImageState(String imageState) {
        this.imageState = imageState;
    }

    public String getImageCaption() {
        return this.imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getSyncedImageUrl() {
        return this.syncedImageUrl;
    }

    public void setSyncedImageUrl(String syncedImageUrl) {
        this.syncedImageUrl = syncedImageUrl;
    }

    public boolean isPreSynced() {
        return this.isPreSynced;
    }

    public void setIsPreSynced(boolean isPreSynced) {
        this.isPreSynced = isPreSynced;
    }

    public boolean isDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isSyncRequested() {
        return this.syncRequested;
    }

    public void setSyncRequested(boolean syncRequested) {
        this.syncRequested = syncRequested;
    }

    public ImageModel getImageModel() {
        if (type.equalsIgnoreCase(DATA)) {
            Log.w("SynscStatusModel", "data object in this field not image");
            return null;
        }

        ImageModel imageModel = new ImageModel();

        if (isPreSynced) {
            imageModel.setGil_id(gilId);
            imageModel.setImage_caption(imageCaption == null ? "" : imageCaption);
            imageModel.setImage_name(syncedImageUrl);
            imageModel.setImage_state(imageState);
            return imageModel;
        }

        imageModel.setGil_id(0);
        imageModel.setImage_caption(imageCaption == null ? "" : imageCaption);
        imageModel.setImage_name("http://d2932g54ef2vds.cloudfront.net" + "/" + imageKey );
        imageModel.setImage_state("new");
        return imageModel;
    }
}
