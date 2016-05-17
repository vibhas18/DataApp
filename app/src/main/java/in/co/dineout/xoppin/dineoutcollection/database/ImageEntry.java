package in.co.dineout.xoppin.dineoutcollection.database;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class ImageEntry extends BaseEntry {

    public static final String TABLE_NAME = "image_model";

    public static final String IMAGE_URI = "uri_image";
    public static final String IMAGE_TYPE = "type";
    public static final String IMAGE_CAPTION= "caption";
    public static final String IMAGE_STATUS = "status";
    public static final String IMAGE_PATH ="path";
    public static final String IMAGE_STATE = "state";
    public static final String IMAGE_GILD = "gil_id";

    public static final int PROFILE_IMAGE = 0x02;
    public static final int MENU_IMAGE = 0x03;
    public static ImageEntry mSingleton  ;


    public static ImageEntry getInstance(){
        if(mSingleton == null)
            mSingleton = new ImageEntry();
        return mSingleton;
    }

    @Override
    public String getCreateStatement() {
        String query =
                "CREATE TABLE IF NOT EXISTS " + ImageEntry.TABLE_NAME + " (" +
                        RestaurantEntry._ID + " INTEGER PRIMARY KEY " +COMMA_SEP+
                        RestaurantEntry.REST_ID + TEXT_TYPE + COMMA_SEP +
                        IMAGE_URI + TEXT_TYPE + COMMA_SEP +
                        IMAGE_CAPTION + TEXT_TYPE + COMMA_SEP +
                        IMAGE_PATH+TEXT_TYPE+COMMA_SEP+
                        IMAGE_TYPE+INTEGER_TYPE+COMMA_SEP+
                        IMAGE_STATUS+INTEGER_TYPE+COMMA_SEP+
                        IMAGE_STATE+TEXT_TYPE+COMMA_SEP+
                        IMAGE_GILD + TEXT_TYPE+
                        " )";

        return query;
    }
}
