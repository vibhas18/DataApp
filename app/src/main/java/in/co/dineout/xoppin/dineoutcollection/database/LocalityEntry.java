package in.co.dineout.xoppin.dineoutcollection.database;

/**
 * Created by prateek.aggarwal on 5/10/16.
 */
public class LocalityEntry extends BaseEntry {

    public static final String TABLE_NAME = "locality";
    public static final String AREA_ID = "id";
    public static final String NAME = "name";
    public static final String PARENT_ID = "parent_id";

    public static LocalityEntry mSingleton  ;

    public static LocalityEntry getInstance(){
        if(mSingleton == null)
            mSingleton = new LocalityEntry();
        return mSingleton;
    }
    @Override
    public String getCreateStatement() {
        String query =
                "CREATE TABLE IF NOT EXISTS " + LocalityEntry.TABLE_NAME + " (" +
                        LocalityEntry._ID + " INTEGER PRIMARY KEY," +
                        AREA_ID + TEXT_TYPE + COMMA_SEP +
                        NAME + TEXT_TYPE + COMMA_SEP +
                        PARENT_ID+TEXT_TYPE+
                        " )";

        return query;
    }
}
