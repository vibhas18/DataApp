package in.co.dineout.xoppin.dineoutcollection.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by suraj on 04/02/16.
 */

public class Restaurant implements Serializable {
    private static final long serialVersionUID = 5811714197689706376L;

    private int r_id;
    private String profile_name;
    private String screen_name;
    private String screen_name_mobile;
    private String address;
    private String pincode;
    private String phone;
    private String landmark;
    private String description;
    private int cost_for_2;
    private String opening_time;
    private String closing_time;
    private String essential_information;
    private int type_of_place; //todo
    private String creation_dt;
    private int status; //todo
    private String latitude;
    private String longitude;
    private String fb_page_url;
    private String url;
    private String short_url;
    private String old_url;
    private String city_name;
    private String area_name;
    private String locality_name;
    private String city_id; //// TODO: 04/02/16
    private String area_id;
    private String locality_id;//// TODO: 04/02/16
    private int is_prepaid; //todo
    private int cost_per_cover;
    private int ww; //todo
    @SerializedName("3e3")
    private int a3e3; //todo
    private String payment_mode;
    private int company_id; //todo
    private int is_ff; //todo
    private String source_id; //todo
    private int whichtype_id; //todo
    private String alias; //todo
    private int restaurant_chain_id; //todo
    private int hotel_id; //todo
    private String email;
    private String website;
    private String mobile_number;
    private String tv_guid;//todo
    private String date_launched; //todo
    private OpenTimingDaysModel open_timing;
    private ArrayList<RestContactModel> contacts;
    private ArrayList<TagModel> tags;
    private ArrayList<FeatureModel> features;
    private ArrayList<CuisineModel> primary_cuisine;
    private ArrayList<CuisineModel> secondary_cuisine;
    private ArrayList<ImageModel> profile_image;
    private ArrayList<ImageModel> menu_image;

    public int getR_id() {
        return this.r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getScreen_name_mobile() {
        return screen_name_mobile;
    }

    public void setScreen_name_mobile(String screen_name_mobile) {
        this.screen_name_mobile = screen_name_mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCost_for_2() {
        return this.cost_for_2;
    }

    public void setCost_for_2(int cost_for_2) {
        this.cost_for_2 = cost_for_2;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public String getClosing_time() {
        return closing_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getEssential_information() {
        return essential_information;
    }

    public void setEssential_information(String essential_information) {
        this.essential_information = essential_information;
    }

    public int getType_of_place() {
        return this.type_of_place;
    }

    public void setType_of_place(int type_of_place) {
        this.type_of_place = type_of_place;
    }

    public String getCreation_dt() {
        return creation_dt;
    }

    public void setCreation_dt(String creation_dt) {
        this.creation_dt = creation_dt;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFb_page_url() {
        return fb_page_url;
    }

    public void setFb_page_url(String fb_page_url) {
        this.fb_page_url = fb_page_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShort_url() {
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public String getOld_url() {
        return old_url;
    }

    public void setOld_url(String old_url) {
        this.old_url = old_url;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getArea_name() {
        return area_name;
    }

    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }

    public String getLocality_name() {
        return locality_name;
    }

    public void setLocality_name(String locality_name) {
        this.locality_name = locality_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public int getIs_prepaid() {
        return this.is_prepaid;
    }

    public void setIs_prepaid(int is_prepaid) {
        this.is_prepaid = is_prepaid;
    }

    public int getCost_per_cover() {
        return this.cost_per_cover;
    }

    public void setCost_per_cover(int cost_per_cover) {
        this.cost_per_cover = cost_per_cover;
    }

    public int getWw() {
        return this.ww;
    }

    public void setWw(int ww) {
        this.ww = ww;
    }

    public int getA3e3() {
        return this.a3e3;
    }

    public void setA3e3(int a3e3) {
        this.a3e3 = a3e3;
    }

    public String getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(String payment_mode) {
        this.payment_mode = payment_mode;
    }

    public int getCompany_id() {
        return this.company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getIs_ff() {
        return this.is_ff;
    }

    public void setIs_ff(int is_ff) {
        this.is_ff = is_ff;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public int getWhichtype_id() {
        return this.whichtype_id;
    }

    public void setWhichtype_id(int whichtype_id) {
        this.whichtype_id = whichtype_id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getRestaurant_chain_id() {
        return this.restaurant_chain_id;
    }

    public void setRestaurant_chain_id(int restaurant_chain_id) {
        this.restaurant_chain_id = restaurant_chain_id;
    }

    public int getHotel_id() {
        return this.hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getTv_guid() {
        return tv_guid;
    }

    public void setTv_guid(String tv_guid) {
        this.tv_guid = tv_guid;
    }

    public String getDate_launched() {
        return date_launched;
    }

    public void setDate_launched(String date_launched) {
        this.date_launched = date_launched;
    }

    public OpenTimingDaysModel getOpen_timing() {
        return open_timing;
    }

    public void setOpen_timing(OpenTimingDaysModel open_timing) {
        this.open_timing = open_timing;
    }

    public ArrayList<RestContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<RestContactModel> contacts) {
        this.contacts = contacts;
    }

    public ArrayList<TagModel> getTags() {
        return tags;
    }

    public void setTags(ArrayList<TagModel> tags) {
        this.tags = tags;
    }

    public ArrayList<FeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<FeatureModel> features) {
        this.features = features;
    }

    public ArrayList<CuisineModel> getPrimary_cuisine() {
        return primary_cuisine;
    }

    public void setPrimary_cuisine(ArrayList<CuisineModel> primary_cuisine) {
        this.primary_cuisine = primary_cuisine;
    }

    public ArrayList<CuisineModel> getSecondary_cuisine() {
        return secondary_cuisine;
    }

    public void setSecondary_cuisine(ArrayList<CuisineModel> secondary_cuisine) {
        this.secondary_cuisine = secondary_cuisine;
    }

    public ArrayList<ImageModel> getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(ArrayList<ImageModel> profile_image) {
        this.profile_image = profile_image;
    }

    public ArrayList<ImageModel> getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(ArrayList<ImageModel> menu_image) {
        this.menu_image = menu_image;
    }

    public String getLocality_id() {
        return this.locality_id;
    }

    public void setLocality_id(String locality_id) {
        this.locality_id = locality_id;
    }
}
