package in.co.dineout.xoppin.dineoutcollection.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by prateek.aggarwal on 5/10/16.
 */
public class DataDatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DataCollection.db";



    private Context mContext;
    public DataDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(AreaEntry.getInstance().getCreateStatement());

        db.execSQL(LocalityEntry.getInstance().getCreateStatement());
        db.execSQL(RestaurantEntry.getInstance().getCreateStatement());
        db.execSQL(ImageEntry.getInstance().getCreateStatement());

        updateAreaDetails(db);
        updateLocalityDetails(db);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private void updateAreaDetails(SQLiteDatabase db) {
        CSVReader csvReader = null;
        List<String[]> rows = null;
        try {
            InputStream inputStream = mContext.getResources().getAssets().open("area.csv");
            csvReader = new CSVReader(new InputStreamReader(inputStream));
            rows = csvReader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Object row : rows) {
            String[] fields = (String[]) row;

            ContentValues values = new ContentValues();
            values.put(AreaEntry.AREA_ID,fields[0]);
            values.put(AreaEntry.NAME, fields[2]);
            values.put(AreaEntry.PARENT_ID, fields[1]);


            String insertStatement = "insert into "+AreaEntry.TABLE_NAME +"("+
                    AreaEntry.AREA_ID+AreaEntry.COMMA_SEP+AreaEntry.NAME+AreaEntry.COMMA_SEP+
                    AreaEntry.PARENT_ID+") values("+" '"+fields[0]+"'"+AreaEntry.COMMA_SEP
                    +"'"+fields[2]+"'"+AreaEntry.COMMA_SEP
                    +"'"+fields[1]+"' )";


            db.execSQL(insertStatement);
        }
    }

    private void updateLocalityDetails(SQLiteDatabase db) {
        CSVReader csvReader = null;
        List<String[]> rows = null;
        try {
            InputStream inputStream = mContext.getResources().getAssets().open("locality.csv");
            csvReader = new CSVReader(new InputStreamReader(inputStream));
            rows = csvReader.readAll();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (csvReader != null) {
                    csvReader.close();
                }
            } catch (Exception e) {

            }
        }

        for (Object row : rows) {
            String[] fields = (String[]) row;
            String insertStatement = "insert into "+LocalityEntry.TABLE_NAME +"("+
                    LocalityEntry.AREA_ID+LocalityEntry.COMMA_SEP+LocalityEntry.NAME+LocalityEntry.COMMA_SEP+
                    LocalityEntry.PARENT_ID+") values("+" '"+fields[0]+"'"+LocalityEntry.COMMA_SEP
                    +"'"+fields[2]+"'"+LocalityEntry.COMMA_SEP
                    +"'"+fields[1]+"' )";

           db.execSQL(insertStatement);
        }
    }


}
