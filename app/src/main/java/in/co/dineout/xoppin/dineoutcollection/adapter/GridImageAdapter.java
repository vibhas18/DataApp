package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;

/**
 * Created by suraj on 14/03/16.
 */
public class GridImageAdapter extends BaseAdapter {
    private static final String TAG = GridImageAdapter.class.getSimpleName();

    private Context mContext;
    private List<ImageStatusModel> mSyncStatusModels;

    public GridImageAdapter(@NonNull Context context, @NonNull List<ImageStatusModel> syncStatusModels) {
        mContext = context;
        mSyncStatusModels = syncStatusModels;
    }

    @Override
    public int getCount() {
        return mSyncStatusModels.size();
    }

    @Override
    public ImageStatusModel getItem(int position) {
        return mSyncStatusModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_image, null, false);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.iv_image);
        TextView tvCaption = (TextView) convertView.findViewById(R.id.tv_caption);


        ImageStatusModel syncStatusModel = getItem(position);

//        Picasso.with(mContext).load(syncStatusModel.getSyncedImageUrl()).into(imageView);
        Picasso.with(mContext).load(Uri.fromFile(new File(syncStatusModel.getImageURI()))).into(imageView);

        tvCaption.setText(syncStatusModel.getCaption());

        return convertView;
    }
}
