package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 06/02/16.
 */
public class ChainModel implements Serializable {
    private static final long serialVersionUID = 3883984775591639171L;

    private int city_id;
    private String restaurant_name;
    private int restaurant_chain_id;

    public int getCity_id() {
        return this.city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getRestaurant_name() {
        return this.restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public int getRestaurant_chain_id() {
        return this.restaurant_chain_id;
    }

    public void setRestaurant_chain_id(int restaurant_chain_id) {
        this.restaurant_chain_id = restaurant_chain_id;
    }
}
