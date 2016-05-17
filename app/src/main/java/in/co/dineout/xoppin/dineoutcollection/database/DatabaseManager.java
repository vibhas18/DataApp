package in.co.dineout.xoppin.dineoutcollection.database;

/**
 * Created by suraj on 13/03/16.
 */
public class DatabaseManager {
//    static private DatabaseManager instance;
//
//    static public void init(Context ctx) {
//        if (null == instance) {
//            instance = new DatabaseManager(ctx);
//        }
//    }
//
//    static public DatabaseManager getInstance() {
//        return instance;
//    }
//
//    private DatabaseHelper helper;
//
//    private DatabaseManager(Context ctx) {
//        helper = new DatabaseHelper(ctx);
//    }
//
//    private DatabaseHelper getHelper() {
//        return helper;
//    }
//
//    public List<AreaModel> getAreaForCityId(int cityId) {
//        try {
//            PreparedQuery<AreaModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(AreaModel.class).queryBuilder().where().eq("parentId", cityId)
//                    .prepare();
//            return DatabaseManager.getInstance().getHelper().getDao(AreaModel.class).query(test);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<LocalityModel> getLocalityForAreaId(int areaId) {
//        try {
//            PreparedQuery<LocalityModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(LocalityModel.class).queryBuilder().where().eq("parentId", areaId)
//                    .prepare();
//            return DatabaseManager.getInstance().getHelper().getDao(LocalityModel.class).query(test);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public AreaModel getAreaForId(String id) {
//        try {
//            List<AreaModel> areaModels = DatabaseManager.getInstance().getHelper().getDao(AreaModel.class).queryForEq("id", id);
//            if (null != areaModels && areaModels.size() > 0)
//                return areaModels.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public LocalityModel getLocalityForId(String id) {
//        try {
//            List<LocalityModel> localityModels = DatabaseManager.getInstance().getHelper().getDao(LocalityModel.class).queryForEq("id", id);
//            if (null != localityModels && localityModels.size() > 0)
//                return localityModels.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public SyncStatusModel getNextSyncModel() {
//        try {
//            PreparedQuery<SyncStatusModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(SyncStatusModel.class).queryBuilder()
//                    .where()
//                    .eq("type", SyncStatusModel.MENU)
//                    .or()
//                    .eq("type", SyncStatusModel.PROFILE)
//                    .or()
//                    .eq("type", SyncStatusModel.DATA)
//                    .and()
//                    .eq("syncRequested", true)
//                    .and()
//                    .not()
//                    .eq("sync_status", RestaurantDetailsModel.SYNC_STATUS.SYNCED)
//                    .prepare();
//
//            ArrayList<SyncStatusModel> syncStatusModels = (ArrayList) getHelper().getDao(SyncStatusModel.class).query(test);
//
//            if (null != syncStatusModels && syncStatusModels.size() > 0) {
//                return syncStatusModels.get(0);
//            }
//
//            PreparedQuery<SyncStatusModel> test1 = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(SyncStatusModel.class).queryBuilder()
//                    .where()
//                    .eq("type", SyncStatusModel.DATA)
//                    .and()
//                    .eq("syncRequested", true)
//                    .and()
//                    .not()
//                    .eq("sync_status", RestaurantDetailsModel.SYNC_STATUS.SYNCED)
//                    .prepare();
//
//            ArrayList<SyncStatusModel> syncStatusModels1 = (ArrayList) getHelper().getDao(SyncStatusModel.class).query(test);
//
//            if (null != syncStatusModels1 && syncStatusModels1.size() > 0) {
//                return syncStatusModels1.get(0);
//            }
//
//            PreparedQuery<SyncStatusModel> test2 = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(SyncStatusModel.class).queryBuilder()
//                    .where()
//                    .eq("type", SyncStatusModel.MENU)
//                    .or()
//                    .eq("type", SyncStatusModel.PROFILE)
//                    .and()
//                    .not()
//                    .eq("sync_status", RestaurantDetailsModel.SYNC_STATUS.SYNCED)
//                    .prepare();
//
//            ArrayList<SyncStatusModel> syncStatusModels2 = (ArrayList) getHelper().getDao(SyncStatusModel.class).query(test2);
//            if (null != syncStatusModels2 && syncStatusModels2.size() > 0) {
//                return syncStatusModels2.get(0);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void createOrUpdateRestaurantDetailsModel(RestaurantDetailsModel restaurantDetailsModel) {
////        try {
////            Dao.CreateOrUpdateStatus i = getHelper().getDao(RestaurantDetailsModel.class).createOrUpdate(restaurantDetailsModel);
////            if (i.isCreated()) {
////                ArrayList<ImageModel> profileImages = restaurantDetailsModel.getRestaurant().getProfile_image();
////                ArrayList<ImageModel> menuImages = restaurantDetailsModel.getRestaurant().getMenu_image();
////
////                if (null != profileImages && profileImages.size() > 0) {
////                    for (ImageModel imageModel : profileImages) {
////                        SyncStatusModel syncStatusModel = new SyncStatusModel(restaurantDetailsModel.getId(), imageModel, SyncStatusModel.PROFILE);
////                        createSyncStatusModel(syncStatusModel);
////                    }
////                }
////
////                if (null != menuImages && menuImages.size() > 0) {
////                    for (ImageModel imageModel : menuImages) {
////                        SyncStatusModel syncStatusModel = new SyncStatusModel(restaurantDetailsModel.getId(), imageModel, SyncStatusModel.MENU);
////                        createSyncStatusModel(syncStatusModel);
////                    }
////                }
////            }
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//
//    public void createSyncStatusModel(SyncStatusModel syncStatusModel) {
//        try {
//            getHelper().getDao(SyncStatusModel.class).create(syncStatusModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void createOrUpdateSyncStatusModel(SyncStatusModel syncStatusModel) {
//        try {
//            getHelper().getDao(SyncStatusModel.class).createOrUpdate(syncStatusModel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void deleteSyncStatusModel(SyncStatusModel syncStatusModel) {
//        try {
//            if (syncStatusModel.isPreSynced()) {
//                syncStatusModel.setDeleted(true);
//                getHelper().getDao(SyncStatusModel.class).update(syncStatusModel);
//            } else {
//                getHelper().getDao(SyncStatusModel.class).delete(syncStatusModel);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public ArrayList<RestaurantDetailsModel> getAllUnSyncedRestaurants() {
//        try {
//            PreparedQuery<RestaurantDetailsModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(RestaurantDetailsModel.class)
//                    .queryBuilder().where().not().eq("sync_status", RestaurantDetailsModel.SYNC_STATUS.SYNCED)
//                    .prepare();
//            return (ArrayList) getHelper().getDao(RestaurantDetailsModel.class).query(test);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<RestaurantDetailsModel>(2);
//    }
//
//    public ArrayList<RestaurantDetailsModel> getAllSyncedRestaurants() {
//        try {
//            PreparedQuery<RestaurantDetailsModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(RestaurantDetailsModel.class)
//                    .queryBuilder().where().eq("sync_status", RestaurantDetailsModel.SYNC_STATUS.SYNCED)
//                    .prepare();
//            return (ArrayList) getHelper().getDao(RestaurantDetailsModel.class).query(test);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<RestaurantDetailsModel>(2);
//    }
//
//    public RestaurantDetailsModel getRestaurantDetailsModelForRestaurantId(int restaurantId) {
//        try {
//            List<RestaurantDetailsModel> restaurantDetailsModels = getHelper().getDao(RestaurantDetailsModel.class)
//                    .query(
//                            getHelper().getDao(RestaurantDetailsModel.class).queryBuilder()
//                                    .where().eq("restaurantId", restaurantId).prepare());
//
//            if (null != restaurantDetailsModels && restaurantDetailsModels.size() > 0) {
//                return restaurantDetailsModels.get(0);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public RestaurantDetailsModel getRestaurantDetailModelForId(int id) {
//        try {
//            List<RestaurantDetailsModel> restaurantDetailsModels = DatabaseManager.getInstance().getHelper().getDao(RestaurantDetailsModel.class).queryForEq("id", id);
//            if (null != restaurantDetailsModels && restaurantDetailsModels.size() > 0)
//                return restaurantDetailsModels.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public RestaurantDetailsModel getRestaurantDetailModelForRestaurantId(int id) {
//        try {
//            List<RestaurantDetailsModel> restaurantDetailsModels = DatabaseManager.getInstance().getHelper().getDao(RestaurantDetailsModel.class).queryForEq("restaurantId", id);
//            if (null != restaurantDetailsModels && restaurantDetailsModels.size() > 0)
//                return restaurantDetailsModels.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public ArrayList<SyncStatusModel> getImagesForTypeAndRestaurant(int restaurantDetailId, String type) {
//        try {
//            PreparedQuery<SyncStatusModel> test = DatabaseManager.getInstance()
//                    .getHelper()
//                    .getRuntimeExceptionDao(SyncStatusModel.class)
//                    .queryBuilder().where().eq("restaurantDetailId", restaurantDetailId)
//                    .and().eq("type", type)
//                    .and().not().eq("deleted", true)
//                    .prepare();
//            return (ArrayList) getHelper().getDao(SyncStatusModel.class).query(test);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ArrayList<SyncStatusModel>(1);
//    }
//
//    public int getImageCountForTypeAndRestaurant(int restaurantDetailId, String type) {
//        return getImagesForTypeAndRestaurant(restaurantDetailId, type).size();
//    }

}

