package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import in.co.dineout.xoppin.dineoutcollection.R;

/**
 * Created by suraj on 06/02/16.
 */
public class TasksListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = TasksListAdapter.class.getSimpleName();

    private Context mContext;
    private JSONArray mTasksList;
    private TaskDetailCallback mCallback;
    public TasksListAdapter(Context context,JSONArray taskList,TaskDetailCallback callback){
        this.mContext = context;
        this.mTasksList = taskList;
        this.mCallback = callback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(mContext).inflate(R.layout.row_task_list,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        JSONObject tasks = this.mTasksList.optJSONObject(position);
        if(tasks != null){
            ((TaskViewHolder)holder).updateData(tasks);
        }
    }

    @Override
    public int getItemCount() {

        if(this.mTasksList != null)
            return this.mTasksList.length();
        return 0;
    }

    private class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvRestName;
        private TextView tvStatus;
        private TextView tvPriority;
        private TextView tvTaskDetail;
        private TextView tvLocality;
        private TextView tvEditing;
        private View mRoot;

        public TaskViewHolder(View itemView) {
            super(itemView);
            initializeView(itemView);
        }

        private void initializeView(View v){

            mRoot = v;
            tvRestName = (TextView) v.findViewById(R.id.tv_rest_name);
            tvStatus = (TextView) v.findViewById(R.id.tv_status);
            tvPriority = (TextView) v.findViewById(R.id.tv_priority);
            tvTaskDetail = (TextView) v.findViewById(R.id.tv_task_detail);
            tvLocality = (TextView) v.findViewById(R.id.tv_locality);
            tvEditing = (TextView) v.findViewById(R.id.tv_editing);
        }

        public void updateData(JSONObject data){

            int restId = data.optInt("r_id",-1);
            if (restId != -1) {
                tvRestName.setText("(" + restId + ") " + data.optString("profile_name"));
            } else {
                tvRestName.setText(data.optString("profile_name"));
            }

            tvLocality.setText(data.optString("locality_name"));
            tvTaskDetail.setText(data.optString("task_details"));
            tvPriority.setText(data.optString("priority"));
            tvStatus.setText(data.optString("status"));

            tvEditing.setVisibility(View.VISIBLE);
            mRoot.setTag(restId);



        }

        @Override
        public void onClick(View v) {
            if(mCallback != null){
                mCallback.showTaskDetail((int)v.getId());
            }
        }
    }

    public void updateAdapter(JSONArray object){
        this.mTasksList = object;
        notifyDataSetChanged();
    }

    public interface TaskDetailCallback{

        public void showTaskDetail(int id);
    }


}
