package in.co.dineout.xoppin.dineoutcollection.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Log;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    private static final String COPY_FILE_APPENDER = "_copy.jpg";

    private TransferUtility transferUtility;
    private AmazonS3Client s3Client;
    private String mRestId;
    private Context mContext;
    private Stack<ImageStatusModel> mToUpload = new Stack<>();
    private ImageUploadCallback mCallback;


    public ImageUploadHandler(Context context,String id,ImageUploadCallback callback){


        mContext = context;
        mCallback = callback;
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
        else{
            if(mCallback != null){
                mCallback.initiateSaveRestaurant();
            }
        }

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
                .upload(BUCKET_NAME, key, getCopyFile(new File(model.getImageURI())));
        transferObserver.setTransferListener(new TransferObserver(model));
    }

    private File getCopyFile(File src){

        String path = src.getAbsolutePath();


        path = path.replaceAll(".jpeg","").replaceAll(".jpg","");

        File f = new File(path+COPY_FILE_APPENDER);
        boolean isCompressionEligible = false;
        try{
            copyFile(src,f);
            isCompressionEligible = true;
        }catch (IOException e){

            e.printStackTrace();
            f = src;
        }

        if(isCompressionEligible)
            compressImage(f.getAbsolutePath());
        return f;
    }

    private void copyFile(File src,File dst ) throws IOException {

        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();

    }

    public boolean compressImage(String src) {
        Bitmap bm = null;
        try {
            bm = resizeImage(src, src, 1920, 1080);
            if (bm != null) {
                bm.recycle();
                bm = null;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Bitmap resizeImage(String pathOfInputImage, String pathOfOutputImage, int dstWidth, int dstHeight) {
        try {
            int inWidth = 0;
            int inHeight = 0;

            InputStream in = new FileInputStream(pathOfInputImage);
            ExifInterface exif = new ExifInterface(pathOfInputImage);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            // decode image size (decode metadata only, not the whole image)
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            in = null;

            // save width and height
            inWidth = options.outWidth;
            inHeight = options.outHeight;

            // decode full image pre-resized
            in = new FileInputStream(pathOfInputImage);
            options = new BitmapFactory.Options();
            // calc rought re-size (this is no exact resize)
            options.inSampleSize = Math.max(inWidth / dstWidth, inHeight / dstHeight);
            // decode full image
            Bitmap roughBitmap = BitmapFactory.decodeStream(in, null, options);

            // calc exact destination size
            Matrix m = new Matrix();
            RectF inRect = new RectF(0, 0, roughBitmap.getWidth(), roughBitmap.getHeight());
            RectF outRect = new RectF(0, 0, dstWidth, dstHeight);
            m.setRectToRect(inRect, outRect, Matrix.ScaleToFit.CENTER);
            float[] values = new float[9];
            m.getValues(values);

            // resize bitmap
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(roughBitmap, (int) (roughBitmap.getWidth() * values[0]), (int) (roughBitmap.getHeight() * values[4]), true);
            if (roughBitmap != resizedBitmap) {
                roughBitmap.recycle();
            }

            resizedBitmap = rotateBitmap(resizedBitmap, orientation);
            // save image
            try {
                // check possible image corruption
                if (resizedBitmap.getByteCount() >= 1000) {
                    FileOutputStream out = new FileOutputStream(pathOfOutputImage);
                    resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 85, out);
                    return resizedBitmap;
                }
            } catch (Exception e) {
                Log.e("Image", e.getMessage(), e);
            }
        } catch (IOException e) {
            Log.e("Image", e.getMessage(), e);
        }
        return null;
    }


    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        try {
            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_NORMAL:
                    return bitmap;
                case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                    matrix.setScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.setRotate(180);
                    break;
                case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_TRANSPOSE:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.setRotate(90);
                    break;
                case ExifInterface.ORIENTATION_TRANSVERSE:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }
            try {
                Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return bmRotated;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                // return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //return null;
        }
        return bitmap;
    }



    private class TransferObserver implements TransferListener{

        private ImageStatusModel mImageModel;
        TransferObserver(ImageStatusModel model){
            this.mImageModel = model;
        }

        @Override
        public void onStateChanged(int id, TransferState state) {

            if (state.equals(TransferState.COMPLETED)) {


                String url  = "http://d3tfancs2fcmmi.cloudfront.net" + "/"+mImageModel.getKey()+".jpg";
                DataDatabaseUtils.getInstance(mContext).markImageStateSynced(mImageModel.getId(),
                        url);

                if(mToUpload.isEmpty() && Utils.isConnected(mContext) ){
                    mCallback.initiateSaveRestaurant();

                }
                if(!mToUpload.empty() && Utils.isConnected(mContext)){
                    uploadImage(mToUpload.pop());
                }
                deleteFile(new File(mImageModel.getImageURI()+COPY_FILE_APPENDER));

            }
        }

        private void deleteFile(File src){
           try{

            src.delete();
           }
           catch (Exception e){

            }
        }

        @Override
        public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {

        }

        @Override
        public void onError(int id, Exception ex) {

            if(!Utils.isConnected(mContext)){

                mToUpload = new Stack<>();
//                DataDatabaseUtils.getInstance(mContext).markRestaurantUnsynced(mRestId);
            }


        }
    }
}
