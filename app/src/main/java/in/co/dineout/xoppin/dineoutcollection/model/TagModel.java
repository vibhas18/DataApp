package in.co.dineout.xoppin.dineoutcollection.model;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class TagModel implements Serializable {
    private static final long serialVersionUID = -5400372138465772635L;

    public TagModel() {}

    public TagModel(String tagName) {
        this.tag_name = tagName;
    }

    private String tag_name;

    public String getTag_name() {
        return this.tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }
}
