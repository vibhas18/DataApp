package in.co.dineout.xoppin.dineoutcollection.model.dbmodels;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import in.co.dineout.xoppin.dineoutcollection.model.GenericModel;

/**
 * Created by suraj on 18/02/16.
 */
@DatabaseTable
public class LocalityModel implements GenericModel {
    private static final long serialVersionUID = -5021806655770812670L;

    @DatabaseField(id = true)
    private int id;
    @DatabaseField
    private int parentId;
    @DatabaseField
    private String name;

    public LocalityModel() {
    }

    public LocalityModel(int id, int parent_id, String name) {
        this.id = id;
        this.parentId = parent_id;
        this.name = name;
    }


    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return this.parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
