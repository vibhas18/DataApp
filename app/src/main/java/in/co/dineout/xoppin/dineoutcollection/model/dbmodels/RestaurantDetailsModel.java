package in.co.dineout.xoppin.dineoutcollection.model.dbmodels;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.helper.SaveToTextLog;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.CuisineModel;
import in.co.dineout.xoppin.dineoutcollection.model.FeatureModel;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.model.OpenTimingDaysModel;
import in.co.dineout.xoppin.dineoutcollection.model.RestContactModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.TagModel;

/**
 * Created by suraj on 18/02/16.
 */
@DatabaseTable
public class RestaurantDetailsModel implements Serializable {
    private static final long serialVersionUID = 5996688949184892297L;



    public RestaurantDetailsModel() {
        this.restaurant = new JSONObject();
        try {
            this.restaurantJSONString = new Gson().toJson(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.restaurantId = "new"+new Date().getTime();
        try {
            restaurant.put("r_id",restaurantId);
            restaurant.put("is_ff",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.sync_status = SYNC_STATUS.UPDATE;
        this.createdFetchedDate = new Date();
        this.mode = DataDatabaseUtils.PENDING;

    }

    public RestaurantDetailsModel(String restaurant) {
        try {
            this.restaurant = new JSONObject(restaurant);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.restaurantName = this.restaurant.optString("profile_name", "");
        this.restaurantId = this.restaurant.optString("r_id", "-1");
        this.sync_status = SYNC_STATUS.UPDATE;
        this.createdFetchedDate = new Date();
    }

    public enum SYNC_STATUS {
        NEW, UPDATE, IN_PROCESS, SYNCED
    }

    private int id;
    private String restaurantName;
    protected String restaurantId;
    @DatabaseField
    private String restaurantJSONString;

    private int mode;
    private JSONObject restaurant;
    @DatabaseField
    private RestaurantDetailsModel.SYNC_STATUS sync_status;
    @DatabaseField
    private Date createdFetchedDate;

    public String getRestaurantId(){
        return this.restaurantId+"";
    }

    public void setRestaurantId(String id){
        this.restaurantId = id;
    }

    public int getMode(){

        return this.mode;
    }

    public void setMode(int mode){
        this.mode = mode;
    }



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
        return this.restaurant.toString();
    }

    public void setRestaurantJSONString(String restaurantJSONString) {
        this.restaurantJSONString = restaurantJSONString;
        try{
            this.restaurant = new JSONObject(restaurantJSONString);}catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateProfileName(String name){
        try {
            this.restaurant.put("profile_name",name);
            this.setRestaurantName(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void updateScreenName(String name){
        try {
            this.restaurant.put("screen_name", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void updateScreenNameMobile(String name){
        try {
            this.restaurant.put("screen_name_mobile", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void updateAddress(String address){
        try {
            this.restaurant.put("address",address);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateLandmark(String landmark){
        try {
            this.restaurant.put("landmark",landmark);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updatePinCode(String code){
        try {
            this.restaurant.put("pincode", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateCityId(CityModel model){
        try {

                this.restaurant.put("city_id",model.getCity_id());


            this.restaurant.put("city_name",model.getName());


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateSubCityId(CityModel model){
        try{

            if(model != null)
                this.restaurant.put("sub_city_id",model.getCity_id());
            else
                this.restaurant.put("sub_city_id",-1);
        }catch (JSONException e){

        }
    }

    public void updateAreaId(AreaModel model){

        try {
            this.restaurant.put("area_id",model.getId());
            this.restaurant.put("area_name",model.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateLatitude(String lat){
        try {
            this.restaurant.put("latitude",lat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateLongitude(String lng){
        try {
            this.restaurant.put("longitude",lng);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void updateLocalityId(LocalityModel model){
        try {
            this.restaurant.put("locality_id",model.getId());
            this.restaurant.put("locality_name",model.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void saveRestaurantDetail(Context context) {

        if (TextUtils.isEmpty(restaurantName)) {
            return;
        }
        if (null == restaurant && TextUtils.isEmpty(restaurantJSONString)) {
            return;
        }
        try {
            if (null != restaurant ) {
                restaurantJSONString = restaurant.toString();
            }
            Log.i("RestaurantDetailModel", restaurantJSONString);


            DataDatabaseUtils.getInstance(context).
                    saveRestaurantForEditing(restaurantId + "", restaurantName, restaurantJSONString);
            SaveToTextLog.saveLogData(getRestaurantName(), restaurantJSONString, DineoutCollectApp.getInstance().getApplicationContext());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isSyncReady(Context context) {
//        Restaurant restaurant = getRestaurant();
//
//        if (TextUtils.isEmpty(restaurant.getProfile_name())) {
//            Toast.makeText(context, "Name cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getAddress())) {
//            Toast.makeText(context, "Address Cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getLocality_id())) {
//            Toast.makeText(context, "Locality cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if(TextUtils.isEmpty(restaurant.getPincode())){
//            Toast.makeText(context, "You need to provide pin code.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getCity_id())) {
//            Toast.makeText(context, "City cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getScreen_name())) {
//            Toast.makeText(context, "Screen Name cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (TextUtils.isEmpty(restaurant.getScreen_name_mobile())) {
//            Toast.makeText(context, "Informal name cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getArea_id())) {
//            Toast.makeText(context, "Area cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getLandmark())) {
//            Toast.makeText(context, "Landmark Cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (TextUtils.isEmpty(restaurant.getPhone())) {
//            Toast.makeText(context, "Phone number cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (restaurant.getCost_for_2() == 0) {
//            Toast.makeText(context, "Const for 2 Cannot be null", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (restaurant.getPrimary_cuisine() == null || restaurant.getPrimary_cuisine().size() == 0) {
//            Toast.makeText(context, "Select at least one primary cuisine", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (restaurant.getTags() == null || restaurant.getTags().size() == 0) {
//            Toast.makeText(context, "Select at least one Tags", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        if (restaurant.getOpen_timing() == null
//                || restaurant.getOpen_timing().getSunday() == null
//                || restaurant.getOpen_timing().getMonday() == null
//                || restaurant.getOpen_timing().getTuesday() == null
//                || restaurant.getOpen_timing().getThursday() == null
//                || restaurant.getOpen_timing().getFriday() == null
//                || restaurant.getOpen_timing().getSaturday() == null) {
//            Toast.makeText(context, "Set Timings", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        ArrayList<SyncStatusModel> menuImages = DatabaseManager.getInstance()
//                .getImagesForTypeAndRestaurant(id, SyncStatusModel.MENU);
//        if (menuImages.size() == 0) {
//            Toast.makeText(context, "Add menu Images", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        ArrayList<SyncStatusModel> profileImages = DatabaseManager.getInstance()
//                .getImagesForTypeAndRestaurant(id, SyncStatusModel.PROFILE);
//        if (profileImages.size() == 0) {
//            Toast.makeText(context, "Add profile Images", Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        for (SyncStatusModel syncStatusModel : menuImages) {
//            syncStatusModel.setSyncRequested(true);
//            DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
//        }
//
//        for (SyncStatusModel syncStatusModel : profileImages) {
//            syncStatusModel.setSyncRequested(true);
//            DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
//        }

        return true;
    }

    public boolean sendToSync(Context context) {

        if(mode == DataDatabaseUtils.PENDING ){

        }
        return false;
    }

    public Restaurant prepareRestaurantFoSyncing() {
//        Restaurant restaurant = getRestaurant();
//        ArrayList<SyncStatusModel> menuSyncStateModels = DatabaseManager.getInstance().getImagesForTypeAndRestaurant(id, SyncStatusModel.MENU);
//        ArrayList<ImageModel> menuImages = new ArrayList<>(5);
//        for (SyncStatusModel syncStatusModel : menuSyncStateModels) {
//            menuImages.add(syncStatusModel.getImageModel());
//        }
//        ArrayList<SyncStatusModel> profileSyncStateModels = DatabaseManager.getInstance().getImagesForTypeAndRestaurant(id, SyncStatusModel.PROFILE);
//        ArrayList<ImageModel> profileImages = new ArrayList<>(5);
//        for (SyncStatusModel syncStatusModel : profileSyncStateModels) {
//            profileImages.add(syncStatusModel.getImageModel());
//        }
//
//        restaurant.setProfile_image(profileImages);
//        restaurant.setMenu_image(menuImages);

        return null;
    }


    public String getProfile_name() {
        return restaurant.optString("profile_name","");
    }


    public String getScreen_name() {
        return restaurant.optString("screen_name","");
    }


    public String getScreen_name_mobile() {
        return restaurant.optString("screen_name_mobile","");
    }


    public String getAddress() {
        return restaurant.optString("address","");
    }


    public String getPincode() {
        return restaurant.optString("pincode","");
    }


    public String getPhone() {
        return restaurant.optString("phone");
    }

    public void setPhone(String phone) {
        try {
            this.restaurant.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getLandmark() {
        return restaurant.optString("landmark");
    }


    public String getDescription() {
        return restaurant.optString("description");
    }

    public void setDescription(String description){
        try {
            this.restaurant.put("description",description);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getCost_for_2() {
        return restaurant.optInt("cost_for_2", 0);
    }

    public void setCost_for_2(int cost_for_2) {

        try {
            restaurant.put("cost_for_2",cost_for_2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getOpening_time() {
        return restaurant.optString("opening_time");
    }

    public void setOpening_time(String opening_time) {

        try{
            restaurant.put("opening_time",opening_time);
        }catch (Exception e){

        }
    }

    public String getClosing_time() {
        return restaurant.optString("closing_time");
    }

    public void setClosing_time(String closing_time) {

        try{
            restaurant.put("closing_time", closing_time);
        }catch (Exception e){

        }

    }

    public String getEssential_information() {
        return restaurant.optString("essential_information");
    }

    public void setEssential_information(String essential_information) {
        try{
            restaurant.put("essential_information", essential_information);
        }catch (Exception e){

        }
    }

    public int getType_of_place() {
        return restaurant.optInt("type_of_place");
    }

    public void setType_of_place(int type_of_place) {
        try{
            restaurant.put("type_of_place", type_of_place);
        }catch (Exception e){

        }
    }

    public String getCreation_dt() {
        return restaurant.optString("creation_dt");
    }

    public void setCreation_dt(String creation_dt) {
        try{
            restaurant.put("creation_dt", creation_dt);
        }catch (Exception e){

        }
    }

    public int getStatus() {
        return restaurant.optInt("status");
    }

    public void setStatus(int status) {
        try{
            restaurant.put("status",status);
        }catch (Exception e){

        }
    }

    public double getLatitude() {
        return restaurant.optDouble("latitude");
    }

    public void setLatitude(String latitude) {

        try{
            restaurant.put("latitude",latitude);
        }catch (Exception e){

        }
    }

    public double getLongitude() {
        return restaurant.optDouble("longitude");
    }

    public void setLongitude(String longitude) {
        try{
            restaurant.put("longitude",longitude);
        }catch (Exception e){

        }
    }

    public String getFb_page_url() {
        return restaurant.optString("fb_page_url");
    }

    public void setFb_page_url(String fb_page_url) {
        try{
            restaurant.put("fb_page_url",fb_page_url);
        }catch (Exception e){

        }
    }

    public String getUrl() {
        return restaurant.optString("url");
    }

    public void setUrl(String url) {
        try{
            restaurant.put("url",url);
        }catch (Exception e){

        }
    }

    public String getShort_url() {
        return restaurant.optString("short_url");
    }

    public void setShort_url(String short_url) {

        try{
            restaurant.put("short_url",short_url);
        }catch (Exception e){

        }
    }

    public String getOld_url() {
        return restaurant.optString("old_url");
    }

    public void setOld_url(String old_url) {

        try{
            restaurant.put("old_url",old_url);
        }catch (Exception e){

        }
    }

    public String getCity_name() {
        return restaurant.optString("city_name");
    }

    public void setCity_name(String city_name) {

        try{
            restaurant.put("city_name",city_name);
        }catch (Exception e){

        }
    }

    public String getArea_name() {
        return restaurant.optString("area_name");
    }

    public void setArea_name(String area_name) {

        try{
            restaurant.put("area_name",area_name);
        }catch (Exception e){

        }
    }

    public String getLocality_name() {
        return restaurant.optString("locality_name");
    }

    public void setLocality_name(String locality_name) {
        try{
            restaurant.put("locality_name",locality_name);
        }catch (Exception e){

        }
    }

    public String getCity_id() {
        return restaurant.optString("city_id","").equalsIgnoreCase("-1")?"":restaurant.optString("city_id");
    }

    public void setCity_id(String city_id) {
        try{
            restaurant.put("city_id",city_id);

        }catch (Exception e){

        }
    }

    public String getArea_id() {
        return restaurant.optString("area_id","").equalsIgnoreCase("-1")?"":restaurant.optString("area_id");
    }

    public void setArea_id(String area_id) {

        try{
            restaurant.put("area_id",area_id);
        }catch (Exception e){

        }
    }


    public int getCost_per_cover() {
        return restaurant.optInt("cost_per_cover");
    }

    public void setCost_per_cover(int cost_per_cover) {
        try{
            restaurant.put("cost_per_cover",cost_per_cover);
        }catch (Exception e){

        }
    }



    public String getAlias() {
        return restaurant.optString("alias");
    }

    public void setAlias(String alias) {

        try{
            restaurant.put("alias",alias);
        }catch (Exception e){

        }
    }

    public int getRestaurant_chain_id() {
        return restaurant.optInt("restaurant_chain_id");
    }

    public void setRestaurant_chain_id(int restaurant_chain_id) {
        try{
            restaurant.put("restaurant_chain_id",restaurant_chain_id);
        }catch (Exception e){

        }
    }

    public int getHotel_id() {
        return restaurant.optInt("hotel_id");
    }

    public int getSubCityId() {
        return restaurant.optInt("sub_city_id");
    }

    public void setHotel_id(int hotel_id) {
        try{
            restaurant.put("hotel_id",hotel_id);
        }catch (Exception e){

        }
    }

    public String getEmail() {
        return restaurant.optString("email");
    }

    public void setEmail(String email) {

        try{
            restaurant.put("email",email);
        }catch (Exception e){

        }
    }

    public String getWebsite() {
        return restaurant.optString("website");
    }

    public void setWebsite(String website) {
        try{
            restaurant.put("website",website);
        }catch (Exception e){

        }
    }

    public String getMobile_number() {
        return restaurant.optString("mobile_number","");
    }

    public void setMobile_number(String mobile_number) {

        try{
            restaurant.put("mobile_number",mobile_number);
        }catch (Exception e){

        }
    }

    public OpenTimingDaysModel getOpen_timing() {

        try{
            OpenTimingDaysModel model = new Gson().fromJson(this.restaurant.optJSONObject("open_timing").toString(),
                    OpenTimingDaysModel.class);
            return model;
        }catch (Exception e){
            return null;
        }
    }

    public void setOpen_timing(OpenTimingDaysModel open_timing) {


        String json = new Gson().toJson(open_timing).toString();
        try {
            this.restaurant.putOpt("open_timing",new JSONObject(json));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public List<RestContactModel> getContacts() {

        try{
            Type listType = new TypeToken<List<RestContactModel>>() {}.getType();
            List<RestContactModel> model = new Gson().fromJson(this.restaurant.optJSONArray("contacts").toString(),
                    listType);
            return model;
        }catch (Exception e){

            return new ArrayList<>();
        }

    }

    public void setContacts(List<RestContactModel> contacts) {
        try {
            String json = new Gson().toJson(contacts).toString();

            JSONArray list = new JSONArray();


            for(RestContactModel models : contacts){

                if(models != null){
                    JSONObject object = new JSONObject();
                    object.put("phone_no",models.getPhone_no());
                    object.put("gccr_type",models.getGcrr_type());
                    object.put("au_email",models.getAu_email());
                    object.put("first_name",models.getFirst_name());
                    object.put("last_name",models.getLast_name());
                    list.put(object);
                }

            }

            this.restaurant.putOpt("contacts",list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<TagModel> getTags() {

        try{
            Type listType = new TypeToken<List<TagModel>>() {}.getType();

            List<TagModel> model = new Gson().fromJson(this.restaurant.optJSONArray("tags").toString(),
                    listType);
            return model;
        }
        catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void setTags(List<TagModel> tags) {
        try {


            JSONArray list = new JSONArray();
            for(TagModel models : tags){

                JSONObject object = new JSONObject();
                object.put("tag_name",models.getTag_name());
                list.put(object);
            }
            this.restaurant.putOpt("tags", list);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<FeatureModel> getFeatures() {

        Type listType = new TypeToken<List<FeatureModel>>() {}.getType();
        try{
            List<FeatureModel> model = new Gson().fromJson(this.restaurant.optJSONArray("features").toString(),
                    listType);
            return model;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public void setFeatures(List<FeatureModel> features) {

        try {
            String json = new Gson().toJson(features).toString();

            JSONArray list = new JSONArray();
            for(FeatureModel models : features){

                JSONObject object = new JSONObject();
                object.put("name",models.getName());
                list.put(object);
            }

            this.restaurant.putOpt("features", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CuisineModel> getPrimary_cuisine() {

        try{
            Type listType = new TypeToken<List<CuisineModel>>() {}.getType();
            List<CuisineModel> model = new Gson().fromJson(this.restaurant.optJSONArray("primary_cuisine").toString(),
                    listType);
            return model;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void setPrimary_cuisine(List<CuisineModel> primary_cuisine) {
        try {
            String json = new Gson().toJson(primary_cuisine).toString();

            JSONArray list = new JSONArray();
            for(CuisineModel models : primary_cuisine){

                JSONObject object = new JSONObject();
                object.put("cuisine_name",models.getCuisine_name());
                list.put(object);
            }
            this.restaurant.putOpt("primary_cuisine", list);
        }
        catch (Exception e){

        }
    }

    public List<CuisineModel> getSecondary_cuisine() {

        try{
            Type listType = new TypeToken<List<CuisineModel>>() {}.getType();
            List<CuisineModel> model = new Gson().fromJson(this.restaurant.optJSONArray("secondary_cuisine").toString(),
                    listType);
            return model;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void setSecondary_cuisine(List<CuisineModel> secondary_cuisine) {
        try {
            String json = new Gson().toJson(secondary_cuisine).toString();
            JSONArray list = new JSONArray();
            for(CuisineModel models : secondary_cuisine){

                JSONObject object = new JSONObject();
                object.put("cuisine_name",models.getCuisine_name());
                list.put(object);
            }
            this.restaurant.putOpt("secondary_cuisine", list);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //    public ArrayList<ImageModel> getProfile_image() {
//        return profile_image;
//    }
//
    public void setProfile_image(List<ImageStatusModel> profile_image) {

        try {
            String json = new Gson().toJson(profile_image).toString();

            JSONArray list = new JSONArray();
            for(ImageStatusModel models : profile_image){

                JSONObject object = new JSONObject();
                object.put("image_name",models.getRemotePath());
                object.put("image_state",models.getImage_state());
                object.put("image_caption",models.getCaption());
                object.put("gil_id",models.getGil_id());
                list.put(object);

            }

            this.restaurant.putOpt("profile_image", list);
        }
        catch (Exception e){

        }
    }
    //
//    public ArrayList<ImageModel> getMenu_image() {
//        return menu_image;
//    }
//
    public void setMenu_image(List<ImageStatusModel> menu_image) {

        try {
            String json = new Gson().toJson(menu_image).toString();

            JSONArray list = new JSONArray();
            for(ImageStatusModel models : menu_image){


                JSONObject object = new JSONObject();

                object.put("image_name",models.getRemotePath());
                object.put("image_state",models.getImage_state());
                object.put("image_caption",models.getCaption());
                object.put("gil_id",models.getGil_id());
                list.put(object);

            }

            this.restaurant.putOpt("menu_image", list);
        }
        catch (Exception e){

        }
    }

    public String getLocality_id() {
        return restaurant.optString("locality_id","")
                .equalsIgnoreCase("-1")?"":restaurant.optString("locality_id");
    }

    public void setLocality_id(String locality_id) {
        try{
            restaurant.put("locality_id", locality_id);
        }catch (Exception e){

        }
    }


    public int validateRestaurantWithoutToast(Context context){

        DataDatabaseUtils utils = DataDatabaseUtils.getInstance(context);

        if(!isBasicDetailValid()){
            return 0;
        }else if(!isDetailValid()){
            return 1;
        }
        else if(!isContactValid()){
            return 2;
        }else if(!isCuisineValid()){
            return 4;
        }else if( !(utils.hasProfileImages(this.getRestaurantId())
                && utils.hasMenuImages(this.getRestaurantId()))){
            return 5;
        }

        return -1;
    }
    public int validateRestaurant(Context context){

        DataDatabaseUtils utils = DataDatabaseUtils.getInstance(context);

        if(!isBasicDetailValid()){
            Toast.makeText(context, "Please provide all star mark field "
                    , Toast.LENGTH_SHORT).show();
            return 0;
        }else if(!isDetailValid()){
            Toast.makeText(context, "Please provide all star mark field and tags for restaurant."
                    , Toast.LENGTH_SHORT).show();
            return 1;
        }
        else if(!isContactValid()){
            Toast.makeText(context, "Please provide mobile number."
                    , Toast.LENGTH_SHORT).show();
            return 2;
        }else if(!isCuisineValid()){
            Toast.makeText(context, "Please provide primary cuisine. "
                    , Toast.LENGTH_SHORT).show();
            return 4;
        }else if( !(utils.hasProfileImages(this.getRestaurantId())
                && utils.hasMenuImages(this.getRestaurantId()))){
            Toast.makeText(context, "Please provide profile and menu images "
                    , Toast.LENGTH_SHORT).show();
            return 5;
        }
        addMenuImages(context);
        addProfileImages(context);
        return -1;
    }

    private boolean checkForImages(DataDatabaseUtils utils){

//        try {
//
//            if(this.restaurant.optJSONArray("profile_image").length() > 0 &&
//                    this.restaurant.optJSONArray("menu_image").length() > 0){
//                return true;
//            }else if(utils.getPendingImage(this.getRestaurantId(),ImageEntry.PROFILE_IMAGE).size()>0
//                    && utils.getPendingImage(this.getRestaurantId(),ImageEntry.MENU_IMAGE).size()>0)
//            return this.restaurant.optJSONArray("profile_image").length() > 0 &&
//                    this.restaurant.optJSONArray("menu_image").length() > 0;
//
//        }
//        catch (Exception e){
//
//            return false;
//        }

        return false;
    }


    private boolean isBasicDetailValid(){
        if (TextUtils.isEmpty(this.getProfile_name())) {
            return false;
        } else if (TextUtils.isEmpty(this.getScreen_name())) {
            return false;

        }else if (TextUtils.isEmpty(this.getScreen_name_mobile())) {
            return false;
        } else if (TextUtils.isEmpty(this.getAddress())) {
            return false;
        } else if (TextUtils.isEmpty(this.getLandmark())) {
            return false;
        } else if (TextUtils.isEmpty(this.getPincode())) {
            return false;
        } else if (TextUtils.isEmpty(this.getCity_id())) {
            return false;
        } else if (TextUtils.isEmpty(this.getArea_id())) {
            return false;
        } else if (TextUtils.isEmpty(this.getLocality_id())) {
            return false;
        } else if(this.getLatitude() == 0 || this.getLongitude() == 0 ){
            return false;
        }

        return true;
    }

    private boolean isDetailValid(){

        if (this.getTags().size() <= 0) {
            return false;
        }

        else if (this.getCost_for_2() <=0) {
            return false;
        }

        return true;
    }

    private boolean isContactValid(){

        if (TextUtils.isEmpty(this.getMobile_number()) ) {
            return false;
        }
//        else if(TextUtils.isEmpty(this.getPhone())){
//            return false;
//        }
        return true;
    }

    private boolean isCuisineValid(){
        if (this.getPrimary_cuisine().size() <= 0) {
            return false;
        }
//        else if (this.getSecondary_cuisine().size() <= 0) {
//            return false;
//        }
        return true;
    }

    private void addMenuImages(Context context){

        List<ImageStatusModel> mToUpload = new ArrayList<>();

        mToUpload.addAll(DataDatabaseUtils.getInstance(context).
                getSyncedImage(getRestaurantId(), ImageEntry.MENU_IMAGE));

        setMenu_image(mToUpload);
    }



    private void addProfileImages(Context context){

        List<ImageStatusModel> mToUpload = new ArrayList<>();

        mToUpload.addAll(DataDatabaseUtils.getInstance(context).
                getSyncedImage(getRestaurantId(), ImageEntry.PROFILE_IMAGE));
        setProfile_image(mToUpload);
    }



}
