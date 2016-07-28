package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 06/02/16.
 */
public class HotelsModel implements Serializable {
    private static final long serialVersionUID = -2557135785006587467L;

    private int city_id = -1;
    private String name = "No Hotel";
    private int hotel_id = -1;

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

    public int getHotel_id() {
        return this.hotel_id;
    }

    public void setHotel_id(int hotel_id) {
        this.hotel_id = hotel_id;
    }

    @Override
    public boolean equals(Object o) {
        return this.hotel_id == ((HotelsModel)o).getHotel_id();
    }
}
