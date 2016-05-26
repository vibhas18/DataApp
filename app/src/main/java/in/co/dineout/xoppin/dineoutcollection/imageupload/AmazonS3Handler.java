package in.co.dineout.xoppin.dineoutcollection.imageupload;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.SyncStatusModel;

/**
 * Created by suraj on 14/03/16.
 */
public class AmazonS3Handler {
    private static final String TAG = AmazonS3Handler.class.getSimpleName();

    public static final String BUCKET_NAME = "dineout-images-prod";
    private static final String ACCESS_ID = "AKIAJYTYHSZU3VBSNOPA";
    private static final String ACCESS_SECRET = "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";
//    private static final String S3_REGION = "ap-x-1";

    private static AmazonS3Handler amazonS3Handler;
    private TransferUtility transferUtility;
    private AmazonS3Client s3Client;

    public static void init(Context context) {
        amazonS3Handler = new AmazonS3Handler(context);
    }

    private AmazonS3Handler(Context context) {
        s3Client = new AmazonS3Client(new BasicAWSCredentials(ACCESS_ID, ACCESS_SECRET));
        s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
        transferUtility = new TransferUtility(s3Client, DineoutCollectApp.getInstance().getApplicationContext());

    }

    public static AmazonS3Handler getInstance() {
        return amazonS3Handler;
    }

    public void uploadImage(final SyncStatusModel syncStatusModel, final SyncStatusCallbacks syncStatusCallbacks) {
        Log.i("test uploadImage", syncStatusModel.getImageKey() + "  test");


        TransferObserver transferObserver = transferUtility.upload(BUCKET_NAME, syncStatusModel.getImageKey(), new File(syncStatusModel.getImageLocalPath()));
        transferObserver.setTransferListener(new TransferListener() {
            @Override
            public void onStateChanged(int id, TransferState state) {

                if (state.equals(TransferState.COMPLETED)) {
                    Log.i("test uploadCompleted", syncStatusModel.getImageKey() + "  test");
                    syncStatusModel.setSync_status(RestaurantDetailsModel.SYNC_STATUS.SYNCED);
//                    DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
                    syncStatusCallbacks.onSyncCompleted();
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

            }

            @Override
            public void onError(int id, Exception ex) {
                Log.i("test upload error", syncStatusModel.getImageKey() + "  test");
                syncStatusCallbacks.onErrorReceived();
            }
        });

    }


    public void test() {

    }

}
