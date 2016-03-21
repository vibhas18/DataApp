package in.co.dineout.xoppin.dineoutcollection.activity;

import android.app.Dialog;
import android.content.Context;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.GridImageAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.SyncStatusModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

import static android.provider.MediaStore.MediaColumns.DATA;

public class ImageUploadListActivity extends AppCompatActivity {

    public static final String ACTION_MENU_IMAGES = "ACTION_MENU_IMAGES";
    public static final String ACTION_PROFILE_IMAGES = "ACTION_PROFILE_IMAGES";

    public static final String RESTAURANT_DETAIL_ID = "RESTAURANT_DETAIL_ID";
    public static final String RESTAURANT_NAME = "RESTAURANT_NAME";
    public static final String FILE_URI = "FILE_URI";
    public static final String TYPE = "TYPE";


    public static final int PICK_IMAGE = 2222;
    public static final int TAKE_PHOTO = 1111;

    public static final int MEDIA_TYPE_IMAGE = 1;

    private Uri fileUri;

    private Context context;

    private GridView gridView;
    private GridImageAdapter gridImageAdapter;

    private TextView tvCamera;
    private TextView tvGallery;

    private int restaurantDetailId;
    private String restaurantName;
    private String filter = "default";
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);

        context = this;

        if (null != savedInstanceState) {
            restaurantDetailId = savedInstanceState.getInt(RESTAURANT_DETAIL_ID);
            fileUri = (Uri) savedInstanceState.getParcelable(FILE_URI);
            restaurantName = savedInstanceState.getString(RESTAURANT_NAME);
            type = savedInstanceState.getString(TYPE);
        } else {
            if (getIntent().getAction().equalsIgnoreCase(ACTION_MENU_IMAGES)) {
                type = SyncStatusModel.MENU;
            } else {
                type = SyncStatusModel.PROFILE;
            }

            restaurantName = getIntent().getStringExtra(RESTAURANT_NAME);
            restaurantDetailId = getIntent().getIntExtra(RESTAURANT_DETAIL_ID, -1);
        }

        gridView = (GridView) findViewById(R.id.gridview);

        if (getIntent().getAction().equalsIgnoreCase(ACTION_MENU_IMAGES)) {
            gridImageAdapter = new GridImageAdapter(ImageUploadListActivity.this,
                    DatabaseManager.getInstance().getImagesForTypeAndRestaurant(restaurantDetailId, SyncStatusModel.MENU));
        } else {
            gridImageAdapter = new GridImageAdapter(ImageUploadListActivity.this,
                    DatabaseManager.getInstance().getImagesForTypeAndRestaurant(restaurantDetailId, SyncStatusModel.PROFILE));
        }

        gridView.setAdapter(gridImageAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showLoginDialog(gridImageAdapter.getItem(position));
            }
        });

        tvCamera = (TextView) findViewById(R.id.tv_camera);
        tvGallery = (TextView) findViewById(R.id.tv_gallery);

        tvCamera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                fileUri = getOutputMediaFileUri(ImageUploadListActivity.MEDIA_TYPE_IMAGE, filter);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                startActivityForResult(takePicture, ImageUploadListActivity.TAKE_PHOTO);
            }
        });

        tvGallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, ImageUploadListActivity.PICK_IMAGE);
            }
        });
    }

    protected void showLoginDialog(final SyncStatusModel syncStatusModel) {
        // custom dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.view_custom_dialog_image);
        dialog.setTitle("Options");

        final EditText etCaption = (EditText) dialog.findViewById(R.id.editText);

        etCaption.setText(syncStatusModel.getImageCaption());

        Button btnDelete = (Button) dialog.findViewById(R.id.btn_delete);
        Button btnCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        Button btnSave = (Button) dialog.findViewById(R.id.btn_save);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseManager.getInstance().deleteSyncStatusModel(syncStatusModel);
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
                syncStatusModel.setImageCaption(etCaption.getText().toString().trim());
                DatabaseManager.getInstance().createOrUpdateSyncStatusModel(syncStatusModel);
                refreshAdapter();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void refreshAdapter() {
        if (TextUtils.isEmpty(type)) {
            return;
        }

        if (type.equalsIgnoreCase(SyncStatusModel.MENU)) {
            gridImageAdapter = new GridImageAdapter(ImageUploadListActivity.this,
                    DatabaseManager.getInstance().getImagesForTypeAndRestaurant(restaurantDetailId, SyncStatusModel.MENU));
        } else {
            gridImageAdapter = new GridImageAdapter(ImageUploadListActivity.this,
                    DatabaseManager.getInstance().getImagesForTypeAndRestaurant(restaurantDetailId, SyncStatusModel.PROFILE));
        }
        gridView.setAdapter(gridImageAdapter);
        gridImageAdapter.notifyDataSetChanged();
    }

    public static Bitmap decodeFile(File f, int width, int height) {
        try {
            //Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            //The new size we want to scale to
            final int REQUIRED_WIDTH = width;
            final int REQUIRED_HIGHT = height;
            //Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_WIDTH && o.outHeight / scale / 2 >= REQUIRED_HIGHT)
                scale *= 2;

            //Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
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


    public Bitmap decodeSampledBitmapFromPath(String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        return bmp;
    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    private Bitmap decodeFile(File f) {
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            FileInputStream stream1 = new FileInputStream(f);
            BitmapFactory.decodeStream(stream1, null, o);
            stream1.close();

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 2000;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            FileInputStream stream2 = new FileInputStream(f);
            Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
            stream2.close();
            return bitmap;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        String filePath = null;
        boolean fetchedImage = false;
        if (requestCode == ImageUploadListActivity.TAKE_PHOTO && resultCode == RESULT_CANCELED) {
            Toast.makeText(ImageUploadListActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == ImageUploadListActivity.TAKE_PHOTO && resultCode == RESULT_OK && fileUri != null) {
            fetchedImage = true;
            filePath = fileUri.getPath();
        } else if (requestCode == ImageUploadListActivity.PICK_IMAGE && resultCode == RESULT_OK) {
            fetchedImage = true;
            Cursor cursor = getContentResolver().query(intent.getData(),
                    new String[]{DATA}, null, null, null);
            cursor.moveToFirst();
            File file = new File(cursor.getString(cursor.getColumnIndex(DATA)));
            cursor.close();
            filePath = file.getAbsolutePath();
        }

        if (fetchedImage) {
            //Try to compress image...
            boolean status = compressImage(filePath);

            if (status) {
                //We create a new image
                SyncStatusModel syncStatusModel = new SyncStatusModel();
                syncStatusModel.setRestaurantDetailId(restaurantDetailId);
                syncStatusModel.setIsPreSynced(false);
                syncStatusModel.setType(type);
                syncStatusModel.setSync_status(RestaurantDetailsModel.SYNC_STATUS.NEW);
                syncStatusModel.setImageCaption("");
                syncStatusModel.setImageKey(new File(filePath).getName()); //do something here
                syncStatusModel.setImageLocalPath(filePath);
                syncStatusModel.setGilId(0);
                syncStatusModel.setImageState("new");

                DatabaseManager.getInstance().createSyncStatusModel(syncStatusModel);

                refreshAdapter();
            } else {
                Toast.makeText(this, "There is an error with this picture, please try again!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public Uri getOutputMediaFileUri(int type, String tag) {
        return Uri.fromFile(getOutputMediaFile(type, tag));
    }

    private String getRestaurantFileName() {
        String restaurantNme = "";
        try {
            restaurantNme = restaurantName.replaceAll(" ", "_").replaceAll("&", "AND");
        } catch (Exception e) {

        }
        return restaurantNme;
    }

    private File getOutputMediaFile(int type, String tag) {

        // External sdcard location
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory() + "/DineoutCollection/" + "restaurant_" + restaurantDetailId);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(getRestaurantFileName(), "Oops! Failed create "
                        + getRestaurantFileName() + " directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "restaurant_" + Utils.cleanIt(getRestaurantFileName().toLowerCase()) + "_" + Utils.cleanIt(tag) + "_" + timeStamp + ".jpg");
        return mediaFile;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(RESTAURANT_DETAIL_ID, restaurantDetailId);
        outState.putString(RESTAURANT_NAME, restaurantName);
        outState.putString(TYPE, type);
        outState.putParcelable(FILE_URI, fileUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {
        restaurantDetailId = outState.getInt(RESTAURANT_DETAIL_ID);
        fileUri = (Uri) outState.getParcelable(FILE_URI);
        restaurantName = outState.getString(RESTAURANT_NAME);
        type = outState.getString(TYPE);

        super.onRestoreInstanceState(outState);
    }
}
