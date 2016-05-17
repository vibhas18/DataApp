package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GridImageAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

import static android.provider.MediaStore.MediaColumns.DATA;

/**
 * Created by prateek.aggarwal on 5/13/16.
 */
public class ImageUploadFragment extends MasterDataFragment implements View.OnClickListener,AdapterView.OnItemClickListener {


    public static final String RESTAURANT_DETAIL_ID = "RESTAURANT_DETAIL_ID";
    public static final String FILE_URI = "FILE_URI";
    public static final String TYPE = "TYPE";
    private String mRestId;
    private int mImageType;

    private GridView gridView;
    private GridImageAdapter gridImageAdapter;

    public static final int PICK_IMAGE = 2222;
    public static final int TAKE_PHOTO = 1111;
    private TextView tvCamera;
    private TextView tvGallery;
    private Uri fileUri ;
    private String key;
    private ImageStatusModel mImageStatusModel;
    public static ImageUploadFragment newInstance(String id,int type){

        ImageUploadFragment fragment = new ImageUploadFragment();
        fragment.mRestId = id;
        fragment.mImageType = type;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_image_upload_list,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle outState) {
        super.onViewCreated(view, outState);
        if(outState != null){
            mRestId = outState.getString(RESTAURANT_DETAIL_ID);
            fileUri = (Uri) outState.getParcelable(FILE_URI);
            mImageType = outState.getInt(TYPE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        String filePath = null;
        boolean fetchedImage = false;
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK && fileUri != null) {
                fetchedImage = true;
                filePath = fileUri.getPath();
            } else if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
                fetchedImage = true;
                Cursor cursor = getContext().getContentResolver().query(data.getData(),
                        new String[]{DATA}, null, null, null);
                cursor.moveToFirst();
                File file = new File(cursor.getString(cursor.getColumnIndex(DATA)));
                cursor.close();
                filePath = file.getAbsolutePath();
            }

            if (fetchedImage) {
                //Try to compress image...
                boolean status = compressImage(filePath);
            }


            if (mImageType == ImageEntry.MENU_IMAGE) {
                mImageStatusModel.setKey("restaurant_" + Utils.cleanIt(mRestId) + "_" + new Date().getTime() + ".jpg");
            }

            mImageStatusModel.setImageURI(filePath);
            DataDatabaseUtils.getInstance(getContext()).saveImageForSyncing(filePath,
                    mRestId, mImageType, mImageStatusModel.getKey());

            refreshAdapter();
        }


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



    public boolean compressImage(String imageLoc) {
        Bitmap bm = null;
        try {
            bm = resizeImage(imageLoc, imageLoc, 1920, 1080);
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

    protected void showLoginDialog(final ImageStatusModel syncStatusModel) {
        // custom dialog
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.view_custom_dialog_image);
        dialog.setTitle("Options");

        final EditText etCaption = (EditText) dialog.findViewById(R.id.editText);

        etCaption.setText(syncStatusModel.getCaption());

        Button btnDelete = (Button) dialog.findViewById(R.id.btn_delete);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataDatabaseUtils.getInstance(getActivity()).deleteImage(syncStatusModel);
                refreshAdapter();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                syncStatusModel.setCaption(etCaption.getText().toString().trim());
                DataDatabaseUtils.getInstance(getActivity()).saveImageCaption(syncStatusModel);
                refreshAdapter();
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void refreshAdapter() {


        gridImageAdapter = new GridImageAdapter(getActivity(),
                DataDatabaseUtils.getInstance(getActivity()).getPendingImage(mRestId, mImageType));
        gridView.setAdapter(gridImageAdapter);
        gridImageAdapter.notifyDataSetChanged();
    }
    private void initializeView(View v){

        gridView = (GridView) v.findViewById(R.id.gridview);
        tvCamera = (TextView) v.findViewById(R.id.tv_camera);
        tvGallery = (TextView) v.findViewById(R.id.tv_gallery);

        tvCamera.setOnClickListener(this);
        tvGallery.setOnClickListener(this);
        gridView.setOnItemClickListener(this);

        refreshAdapter();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeView(getView());
        mImageStatusModel = new ImageStatusModel();
        mImageStatusModel.setType(mImageType);


    }

    @Override
    public void onClick(View v) {

        if(v == tvCamera){

            Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


            fileUri = getOutputMediaFileUri();
            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            startActivityForResult(takePicture, TAKE_PHOTO);
        }else if(v == tvGallery){

            mImageStatusModel = new ImageStatusModel();
            Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto, PICK_IMAGE);
        }
    }

    public Uri getOutputMediaFileUri( ) {
        return Uri.fromFile(getOutputMediaFile( ));
    }

    private File getOutputMediaFile() {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/DineoutCollection/" + "restaurant_" + mRestId);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());

        String key = "restaurant_" + Utils.cleanIt(mRestId) +  "_" + timeStamp + ".jpg";
        mImageStatusModel.setKey(key);
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + key);
        return mediaFile;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showLoginDialog(gridImageAdapter.getItem(position));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(RESTAURANT_DETAIL_ID, mRestId);
        outState.putInt(TYPE, mImageType);
        outState.putParcelable(FILE_URI, fileUri);
        super.onSaveInstanceState(outState);
    }




}
