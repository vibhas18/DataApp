package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class ImageModel implements Serializable {
    private static final long serialVersionUID = 1423984568322800092L;

    private String image_name;
    private String image_caption;
    private String image_state;
    private int gil_id = 0;

    public String getImage_name() {
        return this.image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getImage_caption() {
        return this.image_caption;
    }

    public void setImage_caption(String image_caption) {
        this.image_caption = image_caption;
    }

    public String getImage_state() {
        return this.image_state;
    }

    public void setImage_state(String image_state) {
        this.image_state = image_state;
    }

    public int getGil_id() {
        return this.gil_id;
    }

    public void setGil_id(int gil_id) {
        this.gil_id = gil_id;
    }
}
