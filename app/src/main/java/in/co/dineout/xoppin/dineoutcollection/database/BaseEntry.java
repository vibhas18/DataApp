package in.co.dineout.xoppin.dineoutcollection.database;

import android.provider.BaseColumns;

/**
 * Created by prateek.aggarwal on 5/10/16.
 */
public abstract class BaseEntry implements BaseColumns {

    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER";

    protected static final String COMMA_SEP = " , ";


    public abstract String getCreateStatement();
}
