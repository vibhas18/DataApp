package in.co.dineout.xoppin.dineoutcollection.utils;

import android.content.Context;

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

    public static final String BUCKET_NAME = "dineout-images";
    private static final String ACCESS_ID = "AKIAJIDYAQM2B6XN6FCA";
    private static final String ACCESS_SECRET = "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";
//    private static final String S3_REGION = "ap-southeast-1";

    private TransferUtility transferUtility;
    private AmazonS3Client s3Client;
    private Context mContext;
    private Stack<ImageStatusModel> mToUpload = new Stack<>();



    public ImageUploadHandler(Context context,String id){


        mContext = context;
        mToUpload.addAll(DataDatabaseUtils.getInstance(context).getPendingImage(id, ImageEntry.MENU_IMAGE));
        mToUpload.addAll(DataDatabaseUtils.getInstance(context).getPendingImage(id, ImageEntry.PROFILE_IMAGE));

        s3Client = new AmazonS3Client(new BasicAWSCredentials(ACCESS_ID, ACCESS_SECRET));
        s3Client.setRegion(Region.getRegion(Regions.AP_SOUTHEAST_1));
        transferUtility = new TransferUtility(s3Client, DineoutCollectApp.getInstance().getApplicationContext());

    }

    public void initialize(){

        uploadImage(mToUpload.pop());
    }

    private void uploadImage(final ImageStatusModel model){

        com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver transferObserver = transferUtility
                .upload(BUCKET_NAME, model.getKey(), new File(model.getKey()));
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
                String url  = "http://d2932g54ef2vds.cloudfront.net" + "/"+mImageModel.getKey()+".jpeg";
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
