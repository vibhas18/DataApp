package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTaskModel;

/**
 * Created by suraj on 06/02/16.
 */
public class TasksListAdapter extends ArrayAdapter<AssignedTaskModel> {
    private static final String TAG = TasksListAdapter.class.getSimpleName();

    public TasksListAdapter(Context context, ArrayList<AssignedTaskModel> assignedTaskModels) {
        super(context, R.layout.row_task_list, assignedTaskModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_task_list, parent, false);
            viewHolder = getViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AssignedTaskModel taskModel = getItem(position);
        if (taskModel.getR_id() != -1) {
            viewHolder.tvRestName.setText("(" + taskModel.getR_id() + ") " + taskModel.getProfile_name());
        } else {
            viewHolder.tvRestName.setText(taskModel.getProfile_name());
        }

        viewHolder.tvLocality.setText(taskModel.getLocality_name());
        viewHolder.tvTaskDetail.setText(taskModel.getTask_details());
        viewHolder.tvPriority.setText(taskModel.getPriority());
        viewHolder.tvStatus.setText(taskModel.getStatus());

        if (DatabaseManager.getInstance().getRestaurantDetailsModelForRestaurantId(taskModel.getR_id()) != null) {
            viewHolder.tvEditing.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvEditing.setVisibility(View.GONE);
        }

        return convertView;
    }

    private ViewHolder getViewHolder(View rowView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tvRestName = (TextView) rowView.findViewById(R.id.tv_rest_name);
        viewHolder.tvStatus = (TextView) rowView.findViewById(R.id.tv_status);
        viewHolder.tvPriority = (TextView) rowView.findViewById(R.id.tv_priority);
        viewHolder.tvTaskDetail = (TextView) rowView.findViewById(R.id.tv_task_detail);
        viewHolder.tvLocality = (TextView) rowView.findViewById(R.id.tv_locality);
        viewHolder.tvEditing = (TextView) rowView.findViewById(R.id.tv_editing);
        return viewHolder;
    }

    private class ViewHolder {
        private TextView tvRestName;
        private TextView tvStatus;
        private TextView tvPriority;
        private TextView tvTaskDetail;
        private TextView tvLocality;
        private TextView tvEditing;
    }


}
