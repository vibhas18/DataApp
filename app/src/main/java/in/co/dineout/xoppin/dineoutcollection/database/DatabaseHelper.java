package in.co.dineout.xoppin.dineoutcollection.database;

/**
 * Created by suraj on 18/02/16.
 */

public class DatabaseHelper {
//        extends OrmLiteSqliteOpenHelper {
//    private static final String TAG = DatabaseHelper.class.getSimpleName();
//
//    public static final String DATABASE_NAME = "dineout_collect.db";
//
//    private static final int VER_LAUNCH = 1;
//
//    private static final int DATABASE_VERSION = VER_LAUNCH;
//
//    private Context mContext;
//
//    public DatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        mContext = context;
//    }
//
//    @Override
//    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
////        try {
////            TableUtils.createTable(connectionSource, AreaModel.class);
////            TableUtils.createTable(connectionSource, LocalityModel.class);
////            TableUtils.createTable(connectionSource, RestaurantDetailsModel.class);
////            TableUtils.createTable(connectionSource, SyncStatusModel.class);
////
////            //populate data
////            List<AreaModel> areaModels = getAreaDetails();
////            for (AreaModel areaModel : areaModels) {
////                getDao(AreaModel.class).create(areaModel);
////            }
////
////            List<LocalityModel> localityModels = getLocalityDetails();
////            for (LocalityModel localityModel : localityModels) {
////                getDao(LocalityModel.class).create(localityModel);
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i1) {
//
//    }
//
//    private List<AreaModel> getAreaDetails() {
//        CSVReader csvReader = null;
//        List<String[]> rows = null;
//        try {
//            InputStream inputStream = mContext.getResources().getAssets().open("area.csv");
//            csvReader = new CSVReader(new InputStreamReader(inputStream));
//            rows = csvReader.readAll();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (csvReader != null) {
//                    csvReader.close();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        List<AreaModel> areaModels = new ArrayList<AreaModel>();
//        for (Object row : rows) {
//            String[] fields = (String[]) row;
//            areaModels.add(new AreaModel(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2]));
//        }
//        return areaModels;
//    }
//
//    private List<LocalityModel> getLocalityDetails() {
//        CSVReader csvReader = null;
//        List<String[]> rows = null;
//        try {
//            InputStream inputStream = mContext.getResources().getAssets().open("locality.csv");
//            csvReader = new CSVReader(new InputStreamReader(inputStream));
//            rows = csvReader.readAll();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (csvReader != null) {
//                    csvReader.close();
//                }
//            } catch (Exception e) {
//
//            }
//        }
//
//        List<LocalityModel> localityModels = new ArrayList<LocalityModel>();
//        for (Object row : rows) {
//            String[] fields = (String[]) row;
//            localityModels.add(new LocalityModel(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2]));
//        }
//        return localityModels;
//    }

}