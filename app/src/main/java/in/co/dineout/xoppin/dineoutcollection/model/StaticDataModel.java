package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by suraj on 06/02/16.
 */
public class StaticDataModel implements Serializable {
    private static final long serialVersionUID = -5850969715090582154L;

    private ArrayList<CityModel> city;
    private ArrayList<GcrrTypeModel> gcrr_type;
    private ArrayList<TagModel> tags;
    private ArrayList<FeatureModel> features;
    private ArrayList<CuisineModel> cuisine;
    private ArrayList<HotelsModel> hotels;
    private ArrayList<ChainModel> chain;

    public ArrayList<CityModel> getCity() {
        return this.city;
    }

    public void setCity(ArrayList<CityModel> city) {
        this.city = city;
    }

    public ArrayList<GcrrTypeModel> getGcrr_type() {
        return this.gcrr_type;
    }

    public void setGcrr_type(ArrayList<GcrrTypeModel> gcrr_type) {
        this.gcrr_type = gcrr_type;
    }

    public ArrayList<TagModel> getTags() {
        return this.tags;
    }

    public void setTags(ArrayList<TagModel> tags) {
        this.tags = tags;
    }

    public ArrayList<FeatureModel> getFeatures() {
        return this.features;
    }

    public void setFeatures(ArrayList<FeatureModel> features) {
        this.features = features;
    }

    public ArrayList<CuisineModel> getCuisine() {
        return this.cuisine;
    }

    public void setCuisine(ArrayList<CuisineModel> cuisine) {
        this.cuisine = cuisine;
    }

    public ArrayList<HotelsModel> getHotels() {
        return this.hotels;
    }

    public void setHotels(ArrayList<HotelsModel> hotels) {
        this.hotels = hotels;
    }

    public ArrayList<ChainModel> getChain() {
        return this.chain;
    }

    public void setChain(ArrayList<ChainModel> chain) {
        this.chain = chain;
    }



}
