package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by suraj on 13/03/16.
 */
public class RestaurantDetailListAdapter extends BaseAdapter {

    private Context mContext;
    private List<RestaurantDetailsModel> mRestModel;
    public RestaurantDetailListAdapter(Context context, List<RestaurantDetailsModel> restaurantDetailsModels) {
        this.mContext = context;
        this.mRestModel = restaurantDetailsModels;
    }

    @Override
    public int getCount() {
        if(this.mRestModel == null)
            return 0;
        return this.mRestModel.size();
    }

    @Override
    public RestaurantDetailsModel getItem(int position) {
        return this.mRestModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.row_restaurant_detail, null, false);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_rest_name.setText(getItem(position).getRestaurantName());
        viewHolder.tv_sync.setText(getItem(position).getSync_status().toString());
//        viewHolder.tv_completion.setText();

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String reportDate = df.format(getItem(position).getCreatedFetchedDate());
        viewHolder.tv_date.setText("Created on " + reportDate);

        if (getItem(position).getMode() == DataDatabaseUtils.SYNC_PROGRESS) {
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        } else {
            viewHolder.progressBar.setVisibility(View.GONE);
        }

        return convertView;
    }

    public void updateData(List<RestaurantDetailsModel> models){
        this.mRestModel = models;
        notifyDataSetChanged();
    }

    private static ViewHolder getViewHolder(View rowView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_rest_name = (TextView) rowView.findViewById(R.id.tv_rest_name);
        viewHolder.tv_sync = (TextView) rowView.findViewById(R.id.tv_sync);
        viewHolder.tv_date = (TextView) rowView.findViewById(R.id.tv_date);
        viewHolder.tv_completion = (TextView) rowView.findViewById(R.id.tv_completion);

        viewHolder.progressBar = (ProgressBar) rowView.findViewById(R.id.progressBar);
        return viewHolder;
    }

    public static class ViewHolder {
        TextView tv_rest_name;
        TextView tv_sync;
        TextView tv_date;
        TextView tv_completion;
        ProgressBar progressBar;
    }
}
