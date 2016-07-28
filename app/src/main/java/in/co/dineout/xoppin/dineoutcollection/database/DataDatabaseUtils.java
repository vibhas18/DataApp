package in.co.dineout.xoppin.dineoutcollection.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.AreaModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.LocalityModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by prateek.aggarwal on 5/10/16.
 */
public class DataDatabaseUtils {

    private DataDatabaseHelper mHelper;
    private static DataDatabaseUtils mSingleton;

    public static final int SYNC_READY = 1;
    public static final int PENDING =0;
    public static final int SYNCED = 2;
    public static final int SYNC_PROGRESS = 3;

    private DataDatabaseUtils(Context context){

        mHelper = new DataDatabaseHelper(context);

    }

    public List<AreaModel> getAreaForCityId(String id){


        SQLiteDatabase database = mHelper.getReadableDatabase();
        String[] projection ={
                AreaEntry.AREA_ID,
                AreaEntry.PARENT_ID,
                AreaEntry.NAME
        };
        String sortOrder =
                AreaEntry.AREA_ID + " DESC";
        String selection = AreaEntry.PARENT_ID+" =?";

        Cursor c = database.query(
                AreaEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );

        List<AreaModel> areaList = new ArrayList<>();
        if(c.moveToFirst()){

            do{
                AreaModel model = new AreaModel(c.getInt(c.getColumnIndex(AreaEntry.AREA_ID)),
                        c.getInt(c.getColumnIndex(AreaEntry.PARENT_ID)),c.getString(c.getColumnIndex(AreaEntry.NAME)));

                areaList.add(model);
            }while (c.moveToNext());


            return areaList;
        }

        if(!c.isClosed())
            c.close();

        if(database.isOpen())
            database.close();
        return null;
    }

    public AreaModel getAreaForId(String id){

        SQLiteDatabase database = mHelper.getReadableDatabase();
        String[] projection ={
                AreaEntry.AREA_ID,
                AreaEntry.PARENT_ID,
                AreaEntry.NAME
        };
        String sortOrder =
                AreaEntry.AREA_ID + " DESC";
        String selection = AreaEntry.AREA_ID+" =?";

        Cursor c = database.query(
                AreaEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );

        if(c.moveToFirst()){

            AreaModel model = new AreaModel(c.getInt(c.getColumnIndex(AreaEntry.AREA_ID)),
                    c.getInt(c.getColumnIndex(AreaEntry.PARENT_ID)),c.getString(c.getColumnIndex(AreaEntry.NAME)));

            return model;
        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return null;
    }


    public List<LocalityModel> getLocalityForAreaId(String id){


        SQLiteDatabase database = mHelper.getReadableDatabase();
        String[] projection ={
                LocalityEntry.AREA_ID,
                LocalityEntry.PARENT_ID,
                LocalityEntry.NAME
        };
        String sortOrder =
                LocalityEntry.AREA_ID + " DESC";
        String selection = LocalityEntry.PARENT_ID+"=?";

        Cursor c = database.query(
                LocalityEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );

        List<LocalityModel> areaList = new ArrayList<>();
        if(c.moveToFirst()){

            do{
                LocalityModel model = new LocalityModel(c.getInt(c.getColumnIndex(AreaEntry.AREA_ID)),
                        c.getInt(c.getColumnIndex(AreaEntry.PARENT_ID)),c.getString(c.getColumnIndex(AreaEntry.NAME)));

                areaList.add(model);
            }while (c.moveToNext());


            return areaList;
        }


        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return null;
    }

    public LocalityModel getLocalityForId(String id){

        SQLiteDatabase database = mHelper.getReadableDatabase();
        String[] projection ={
                LocalityEntry.AREA_ID,
                LocalityEntry.PARENT_ID,
                LocalityEntry.NAME
        };
        String sortOrder =
                LocalityEntry.AREA_ID + " DESC";
        String selection = LocalityEntry.AREA_ID+" LIKE ?";

        Cursor c = database.query(
                LocalityEntry.TABLE_NAME,
                projection,
                selection,
                new String[]{id},
                null,
                null,
                sortOrder
        );

        if(c.moveToFirst()){

            LocalityModel model = new LocalityModel(c.getInt(c.getColumnIndex(LocalityEntry.AREA_ID)),
                    c.getInt(c.getColumnIndex(LocalityEntry.PARENT_ID)),c.getString(c.getColumnIndex(LocalityEntry.NAME)));

            return model;
        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();

        return null;
    }

    private void saveRestaurant(String id,String name,String json,int mode){


        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.REST_ID,id);
        values.put(RestaurantEntry.REST_NAME,name);
        values.put(RestaurantEntry.REST_JSON,json);
        values.put(RestaurantEntry.REST_MODE,mode);


        RestaurantDetailsModel model = getRestaurantModelForId(id);
        SQLiteDatabase db = mHelper.getWritableDatabase();
        if(model == null ){
            db.insert(RestaurantEntry.TABLE_NAME,"null",values);
        }else{


            updateRestaurantForId(id,json,mode);
        }
        if(db.isOpen())
            db.close();

    }

    public void saveRestaurantForSyncing(String id,String name,String json){
        saveRestaurant(id, name, json, SYNC_READY);
    }

    public void saveRestaurantForEditing(String id,String name,String json){
        saveRestaurant(id, name, json, PENDING);
    }

    public void updateRestaurantForId(String id,String json,int mode){

        SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = RestaurantEntry.REST_ID+" =?";
        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.REST_JSON,json);

        values.put(RestaurantEntry.REST_MODE,mode);
        database.update(RestaurantEntry.TABLE_NAME, values, selection, new String[]{id});
        if(database.isOpen())
            database.close();

    }

    public RestaurantDetailsModel getRestaurantModelForId(String id){

        SQLiteDatabase database = mHelper.getReadableDatabase();
        String[] projection ={
                RestaurantEntry.REST_ID,
                RestaurantEntry.REST_NAME,
                RestaurantEntry.REST_JSON,
                RestaurantEntry.REST_MODE
        };

        String selection = RestaurantEntry.REST_ID+" LIKE ?";
        String[] selectionArgs = {id};


        Cursor c = database.query(
                RestaurantEntry.TABLE_NAME,  // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                 // The sort order
        );

        if(c.moveToFirst()){

            RestaurantDetailsModel model = new RestaurantDetailsModel(c.getString(c.getColumnIndex(RestaurantEntry.REST_JSON)));
            model.setMode(c.getInt(c.getColumnIndex(RestaurantEntry.REST_MODE)));
            model.setRestaurantId(c.getString(c.getColumnIndex(RestaurantEntry.REST_ID)));
            return model;
        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return null;
    }

    public synchronized List<RestaurantDetailsModel> getUnsynedRestaurant(){

        String[] projection ={
                RestaurantEntry.REST_ID,
                RestaurantEntry.REST_NAME,
                RestaurantEntry.REST_JSON,
                RestaurantEntry.REST_MODE
        };

        String sortOrder =
                RestaurantEntry.REST_NAME + " DESC";
        String selection = RestaurantEntry.REST_MODE+" =? OR "+RestaurantEntry.REST_MODE+" =?";
        String[] mode = {String.valueOf(SYNC_READY),String.valueOf(SYNC_PROGRESS)};

        return getRestaurantList(projection,selection,mode,sortOrder);
    }


    public List<RestaurantDetailsModel> getSynedRestaurant(){

        String[] projection ={
                RestaurantEntry.REST_ID,
                RestaurantEntry.REST_NAME,
                RestaurantEntry.REST_JSON,
                RestaurantEntry.REST_MODE
        };

        String sortOrder =
                RestaurantEntry.REST_NAME + " DESC";
        String selection = RestaurantEntry.REST_MODE+"=?";
        String[] mode = {SYNCED+""};

        return getRestaurantList(projection,selection,mode,sortOrder);
    }

    public List<RestaurantDetailsModel> getPendingRestaurant(){

        String[] projection ={
                RestaurantEntry.REST_ID,
                RestaurantEntry.REST_NAME,
                RestaurantEntry.REST_JSON,
                RestaurantEntry.REST_MODE
        };

        String sortOrder =
                RestaurantEntry.REST_NAME + " DESC";
        String selection = RestaurantEntry.REST_MODE+"=?";
        String[] mode = {PENDING+""};

        return getRestaurantList(projection,selection,mode,sortOrder);
    }

    private List<RestaurantDetailsModel> getRestaurantList(String[] projection,String selection,String[] where,String sortOrder){

        SQLiteDatabase database = mHelper.getReadableDatabase();

        Cursor c = database.query(
                RestaurantEntry.TABLE_NAME,
                projection,
                selection,
                where,
                null,
                null,
                sortOrder
        );

        List<RestaurantDetailsModel> list = new ArrayList<>();
        if(c.moveToFirst()){


            do{
                RestaurantDetailsModel model = new RestaurantDetailsModel(c.getString(c.getColumnIndex(RestaurantEntry.REST_JSON)));

                model.setMode(c.getInt(c.getColumnIndex(RestaurantEntry.REST_MODE)));
                model.setRestaurantId(c.getString(c.getColumnIndex(RestaurantEntry.REST_ID)));

                list.add(model);
            }while (c.moveToNext());


        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return list;
    }

    private synchronized void markRestaurantState(String id,int mode){

        SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = RestaurantEntry.REST_ID+" =? AND "+RestaurantEntry.REST_MODE +" <>?";
        ContentValues values = new ContentValues();
        values.put(RestaurantEntry.REST_MODE,mode);
        database.update(RestaurantEntry.TABLE_NAME, values, selection, new String[]{id,String.valueOf(mode)});
        if(database.isOpen())
            database.close();
    }

    public void markImageStateSynced(int id,String path){

        SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = ImageEntry._ID+" =? ";
        ContentValues values = new ContentValues();
        values.put(ImageEntry.IMAGE_PATH,path);
        values.put(ImageEntry.IMAGE_STATUS,DataDatabaseUtils.SYNCED);
        database.update(ImageEntry.TABLE_NAME, values, selection, new String[]{String.valueOf(id)});
        if(database.isOpen())
            database.close();

    }

    public void saveImageForSyncing(String uri,String restId,int type,String key){

        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ImageEntry.IMAGE_URI,uri);
        values.put(ImageEntry.IMAGE_TYPE,type);
        values.put(ImageEntry.IMAGE_CAPTION,"");
        values.put(RestaurantEntry.REST_ID,restId);
        values.put(ImageEntry.IMAGE_PATH,""+"http://d3tfancs2fcmmi.cloudfront.net/"+key+".jpg");
        values.put(ImageEntry.IMAGE_STATUS,DataDatabaseUtils.SYNC_READY);
        values.put(ImageEntry.IMAGE_GILD,"0");
        values.put(ImageEntry.IMAGE_STATE,"new");
        database.insert(ImageEntry.TABLE_NAME, null, values);
        if(database.isOpen())
            database.close();

    }

    public void saveImageCaption(ImageStatusModel model){

        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ImageEntry.IMAGE_CAPTION, model.getCaption());
        if(model.getImage_state().equalsIgnoreCase("new"))
        values.put(ImageEntry.IMAGE_STATUS,SYNC_READY);

        String selection = ImageEntry._ID+" =?";
        String[] where = {String.valueOf(model.getId())};
        database.update(ImageEntry.TABLE_NAME, values, selection, where);
        if(database.isOpen())
            database.close();

    }

    public void deleteImage(ImageStatusModel model){


        SQLiteDatabase database = mHelper.getWritableDatabase();

        if(model != null && model.getImage_state().equalsIgnoreCase("old")){

            ContentValues values = new ContentValues();

            values.put(ImageEntry.IMAGE_STATE, "remove");
            String selection = ImageEntry._ID+" =?";
            String[] where = {String.valueOf(model.getId())};
            database.update(ImageEntry.TABLE_NAME, values, selection, where);

        }else if(model != null &&  model.getGil_id() == 0 && model.getImage_state().equalsIgnoreCase("new")){
            String selection = ImageEntry._ID+" =?";
            String[] where = {String.valueOf(model.getId())};
            database.delete(ImageEntry.TABLE_NAME,selection,where);
        }


        if(database.isOpen())
            database.close();

    }

    private ImageStatusModel getImageForId(int id){

        SQLiteDatabase database = mHelper.getReadableDatabase();

        String[] projection = {

                ImageEntry._ID,
                ImageEntry.IMAGE_URI,
                ImageEntry.IMAGE_CAPTION,
                ImageEntry.IMAGE_PATH,
                ImageEntry.IMAGE_STATUS,
                ImageEntry.IMAGE_TYPE,
                ImageEntry.IMAGE_GILD,
                ImageEntry.IMAGE_STATE,
                RestaurantEntry.REST_ID,

        };

        String selection = ImageEntry._ID+" =?";
        String[] where = {String.valueOf(id)};

        Cursor c = database.query(
                ImageEntry.TABLE_NAME,
                projection,
                selection,
                where,
                null,
                null,
                null
        );


        if(c.moveToFirst()){
            ImageStatusModel model = new ImageStatusModel();
            model.setId(c.getInt(c.getColumnIndex(ImageEntry._ID)));
            model.setRestaurantId(c.getString(c.getColumnIndex(RestaurantEntry.REST_ID)));
            model.setType(c.getInt(c.getColumnIndex(ImageEntry.IMAGE_TYPE)));
            model.setCaption(c.getString(c.getColumnIndex(ImageEntry.IMAGE_CAPTION)));
            model.setImageURI(c.getString(c.getColumnIndex(ImageEntry.IMAGE_URI)));
            model.setRemotePath(c.getString(c.getColumnIndex(ImageEntry.IMAGE_PATH)));
            model.setImage_state(c.getString(c.getColumnIndex(ImageEntry.IMAGE_STATE)));
            model.setGil_id(c.getInt(c.getColumnIndex(ImageEntry.IMAGE_GILD)));

            return model;

        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return null;
    }

    public void saveImageForSyncing(String resId,ImageStatusModel model,int status){

        SQLiteDatabase database = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ImageEntry.IMAGE_PATH,model.getRemotePath());
        values.put(ImageEntry.IMAGE_TYPE,model.getType());
        values.put(RestaurantEntry.REST_ID,resId);
        values.put(ImageEntry.IMAGE_CAPTION,model.getCaption());
        values.put(ImageEntry.IMAGE_GILD,model.getGil_id());
        values.put(ImageEntry.IMAGE_STATE,model.getImage_state());
        values.put(ImageEntry.IMAGE_STATUS,status);

        database.insert(ImageEntry.TABLE_NAME, null, values);
        if(database.isOpen())
            database.close();

    }

    public void saveProfileImage(String uri ,String resId,String key){
        saveImageForSyncing(uri, resId, ImageEntry.PROFILE_IMAGE, key);
    }

    public void saveProfileImage(List<ImageStatusModel> model,String resId){


        saveImages(model, resId, ImageEntry.PROFILE_IMAGE);
    }

    public void saveMenuImage(List<ImageStatusModel> model,String resId){


        saveImages(model,resId,ImageEntry.MENU_IMAGE);
    }

    public boolean hasProfileImages(String id){

        return getPendingImage(id,ImageEntry.PROFILE_IMAGE).size()>0 || getSyncedImage(id,ImageEntry.PROFILE_IMAGE).size()>0;
    }

    public boolean hasMenuImages(String id){

        return getPendingImage(id,ImageEntry.MENU_IMAGE).size()>0 || getSyncedImage(id,ImageEntry.MENU_IMAGE).size()>0;
    }

    private void saveImages(List<ImageStatusModel> model,String resId,int type){

        for(ImageStatusModel statusModel:model){
            if(statusModel != null){

                statusModel.setType(type);
                saveImageForSyncing(resId,statusModel,SYNCED);
            }
        }
    }

    public void saveMenuImage(String uri ,String resId,String key){
        saveImageForSyncing(uri, resId, ImageEntry.MENU_IMAGE, key);
    }

    public synchronized void markRestaurantSynced(String id){

        markRestaurantState(id, SYNCED);
    }


    public void markRestaurantPending(String id){

        markRestaurantState(id,PENDING);
    }

    public void markRestaurantSyncProgress(String id){

        markRestaurantState(id,SYNC_PROGRESS);
    }

    public void markRestaurantUnsynced(String id){

        markRestaurantState(id,DataDatabaseUtils.SYNC_READY);
    }


    public static DataDatabaseUtils getInstance(Context context){

        if(mSingleton == null)
            mSingleton = new DataDatabaseUtils(context);

        return mSingleton;
    }



    public List<ImageStatusModel> getPendingImage(String restId,int type){

        String selection = RestaurantEntry.REST_ID +" =? AND "+ImageEntry.IMAGE_TYPE+" =?"+" AND "
                +ImageEntry.IMAGE_STATUS + " =? AND "+ImageEntry.IMAGE_STATE+" !=?";
        String[] where = {restId, String.valueOf(type),String.valueOf(DataDatabaseUtils.SYNC_READY),"remove"};

        return getImageList(selection,where);

    }

    public List<ImageStatusModel> getSyncedImage(String restId,int type){

        String selection = RestaurantEntry.REST_ID +" =? AND "+ImageEntry.IMAGE_TYPE+" =?"+" AND "+ImageEntry.IMAGE_STATUS + " =?";
        String[] where = {restId, String.valueOf(type),String.valueOf(DataDatabaseUtils.SYNCED)};

        return getImageList(selection,where);

    }



    private List<ImageStatusModel> getImageList(String selection,String[] where){

        SQLiteDatabase database = mHelper.getReadableDatabase();

        String[] projection = {

                ImageEntry._ID,
                ImageEntry.IMAGE_URI,
                ImageEntry.IMAGE_CAPTION,
                ImageEntry.IMAGE_PATH,
                ImageEntry.IMAGE_STATUS,
                ImageEntry.IMAGE_TYPE,
                ImageEntry.IMAGE_GILD,
                ImageEntry.IMAGE_STATE,
                RestaurantEntry.REST_ID,

        };

        Cursor c = database.query(
                ImageEntry.TABLE_NAME,
                projection,
                selection,
                where,
                null,
                null,
                null
        );

        List<ImageStatusModel> list = new ArrayList<>();
        if(c.moveToFirst()){


            do{
                ImageStatusModel model = new ImageStatusModel();
                model.setId(c.getInt(c.getColumnIndex(ImageEntry._ID)));
                model.setRestaurantId(c.getString(c.getColumnIndex(RestaurantEntry.REST_ID)));
                model.setType(c.getInt(c.getColumnIndex(ImageEntry.IMAGE_TYPE)));
                model.setCaption(c.getString(c.getColumnIndex(ImageEntry.IMAGE_CAPTION)));
                model.setImageURI(c.getString(c.getColumnIndex(ImageEntry.IMAGE_URI)));
                model.setRemotePath(c.getString(c.getColumnIndex(ImageEntry.IMAGE_PATH)));
                model.setImage_state(c.getString(c.getColumnIndex(ImageEntry.IMAGE_STATE)));
                model.setGil_id(c.getInt(c.getColumnIndex(ImageEntry.IMAGE_GILD)));


                list.add(model);
            }while (c.moveToNext());


        }

        if(!c.isClosed())
            c.close();
        if(database.isOpen())
            database.close();
        return list;
    }


    public void markProgressToReady(){

       List<RestaurantDetailsModel> unsynced = getUnsynedRestaurant();
        for(RestaurantDetailsModel model:unsynced){
            if(model != null && model.getMode() == SYNC_PROGRESS){
                markRestaurantUnsynced(model.getRestaurantId());
            }
        }
    }

    public void deleteSyncedImagesForRestaurant(String id){



        SQLiteDatabase database = mHelper.getWritableDatabase();

        String selection = RestaurantEntry.REST_ID+" =? AND "+ImageEntry.IMAGE_STATUS +" =?";
        String[] where = {String.valueOf(id),String.valueOf(DataDatabaseUtils.SYNCED)};
        database.delete(ImageEntry.TABLE_NAME,selection,where);



        if(database.isOpen())
            database.close();
    }

}
