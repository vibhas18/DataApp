package in.co.dineout.xoppin.dineoutcollection.model;

import android.text.TextUtils;

import java.io.Serializable;

/**
 * Created by suraj on 04/02/16.
 */
public class FeatureModel implements Serializable {
    private static final long serialVersionUID = 5136305765756436242L;

    public FeatureModel() {}

    public FeatureModel (String featureModel) {
        name = featureModel;
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {

        if(TextUtils.isEmpty(this.getName())){
            return false;
        }

        if(o != null && TextUtils.isEmpty(((FeatureModel)o).getName()))
            return false;

        return this.getName().equalsIgnoreCase(((FeatureModel)o).getName());
    }

    @Override
    public int hashCode() {

        if(TextUtils.isEmpty(this.getName())){
            return super.hashCode();
        }

        return this.getName().hashCode();
    }
}
