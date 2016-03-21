package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class CuisineModel implements Serializable {
    private static final long serialVersionUID = 2946782172081028291L;

    private String cuisine_name;

    public String getCuisine_name() {
        return this.cuisine_name;
    }

    public void setCuisine_name(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }
}
