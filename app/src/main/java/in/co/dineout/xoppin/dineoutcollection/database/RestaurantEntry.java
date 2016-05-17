package in.co.dineout.xoppin.dineoutcollection.database;

/**
 * Created by prateek.aggarwal on 5/10/16.
 */
public class RestaurantEntry extends BaseEntry {

    public static final String TABLE_NAME = "RestaurantModel";
    public static final String REST_ID = "rest_id";
    public static final String REST_NAME = "rest_name";
    public static final String REST_JSON = "rest_data";

    public static final String REST_MODE = "rest_mode";
    public static RestaurantEntry mSingleton  ;

    public static RestaurantEntry getInstance(){
        if(mSingleton == null)
       mSingleton = new RestaurantEntry();
        return mSingleton;
    }

    @Override
    public String getCreateStatement() {
        String query =
                "CREATE TABLE IF NOT EXISTS " + RestaurantEntry.TABLE_NAME + " (" +
                        RestaurantEntry._ID + " INTEGER PRIMARY KEY " +COMMA_SEP+
                        REST_ID + TEXT_TYPE + COMMA_SEP +
                        REST_NAME + TEXT_TYPE + COMMA_SEP +
                        REST_JSON + TEXT_TYPE + COMMA_SEP +
                        REST_MODE+INTEGER_TYPE+
                        " )";

        return query;
    }
}
