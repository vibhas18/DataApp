package in.co.dineout.xoppin.dineoutcollection.utils;

import android.content.Context;
import android.text.TextUtils;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.Stack;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class ImageUploadHandler  {

    private static final String TAG = ImageUploadHandler.class.getSimpleName();

//    public static final String BUCKET_NAME = "dineout-images";
//    private static final String ACCESS_ID = "AKIAJIDYAQM2B6XN6FCA";
//    private static final String ACCESS_SECRET = "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";
//    private static final String S3_REGION = "ap-southeast-1";


    public static final String BUCKET_NAME = "dineout-images-prod";
    private static final String ACCESS_ID = "AKIAJYTYHSZU3VBSNOPA";
    private static final String ACCESS_SECRET = "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";

    private TransferUtility transferUtility;
    private AmazonS3Client s3Client;
    private String mRestId;
    private Context mContext;
    private Stack<ImageStatusModel> mToUpload = new Stack<>();



    public ImageUploadHandler(Context context,String id){


        mContext = context;
        mRestId = id;
        mToUpload.addAll(DataDatabaseUtils.getInstance(context).getPendingImage(id, ImageEntry.MENU_IMAGE));
        mToUpload.addAll(DataDatabaseUtils.getInstance(context).getPendingImage(id, ImageEntry.PROFILE_IMAGE));

        s3Client = new AmazonS3Client(new BasicAWSCredentials(ACCESS_ID, ACCESS_SECRET));
        s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));
        transferUtility = new TransferUtility(s3Client, DineoutCollectApp.getInstance().getApplicationContext());

    }

    public void initialize(){

        if(!mToUpload.empty())
            uploadImage(mToUpload.pop());
        else
            DataDatabaseUtils.getInstance(mContext).markRestaurantPending(mRestId);
    }

    private void uploadImage(final ImageStatusModel model){

        if(model == null)
            return;

        if(TextUtils.isEmpty(model.getImageURI()) || TextUtils.isEmpty(model.getKey()))
            return;
        String key = model.getKey();
        if(!(key.contains(".jpeg") || key.contains(".jpg"))){
            key += ".jpg";
        }
        com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver transferObserver = transferUtility
                .upload(BUCKET_NAME, key, new File(model.getImageURI()));
        transferObserver.setTransferListener(new TransferObserver(model));
    }


    private class TransferObserver implements TransferListener{

        private ImageStatusModel mImageModel;
        TransferObserver(ImageStatusModel model){
            this.mImageModel = model;
        }

        @Override
        public void onStateChanged(int id, TransferState state) {

            if (state.equals(TransferState.COMPLETED)) {
                String url  = "http://d3tfancs2fcmmi.cloudfront.net" + "/"+mImageModel.getKey();
                DataDatabaseUtils.getInstance(mContext).markImageStateSynced(mImageModel.getId(),
                       url);

                if(!mToUpload.empty()){
                    uploadImage(mToUpload.pop());
                }


            }
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

        }

        @Override
        public void onError(int id, Exception ex) {

            mToUpload.push(mImageModel);
        }
    }
}
