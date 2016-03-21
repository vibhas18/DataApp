package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 06/02/16.
 */
public class CityModel implements GenericModel {
    private static final long serialVersionUID = -1046555708171148788L;

    private int city_id;
    private String name;
    private int parent_id;

    public int getCity_id() {
        return this.city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }
}
