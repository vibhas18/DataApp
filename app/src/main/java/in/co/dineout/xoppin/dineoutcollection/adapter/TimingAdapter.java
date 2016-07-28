package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.TimingDetailCallback;
import in.co.dineout.xoppin.dineoutcollection.model.TimingModel;

/**
 * Created by prateek.aggarwal on 7/19/16.
 */
public class TimingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private Map<String,TimingModel> timingModels = new HashMap<>();
    private List<String> keys = new ArrayList<>();
    private TimingDetailCallback timingDetailCallback;
    public  TimingAdapter(Context context,Map<String,TimingModel> list,TimingDetailCallback callback){
        this.mContext = context;
        if(list != null){
            this.timingModels = list;
            keys.addAll(list.keySet());
        }
        timingDetailCallback = callback;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TimingViewHolder(LayoutInflater.from(mContext).inflate(R.layout.slot_list_item,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TimingViewHolder)holder).updateData(timingModels.get(keys.get(position)),keys.get(position));
    }

    public void updateMap(Map<String,TimingModel> update){
        if(update != null){
            Iterator<String> key = update.keySet().iterator();
            while(key.hasNext()){
                String k = key.next();
                timingModels.put(k,update.get(k));
            }

            keys = new ArrayList<>();
            keys.addAll(timingModels.keySet());
            notifyDataSetChanged();
        }
    }

    public void deleteTiming(String key){

        if(timingModels.containsKey(key)){
            timingModels.remove(key);
            keys = new ArrayList<>();
            keys.addAll(timingModels.keySet());

        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return keys.size();
    }


    private class TimingViewHolder extends RecyclerView.ViewHolder {

        private View mRoot;
        private TextView mLabel;
        private LinearLayout mSlotContainer;

        public TimingViewHolder(View itemView) {
            super(itemView);
            mRoot = itemView;
            mLabel = (TextView) itemView.findViewById(R.id.day_label);
            mSlotContainer = (LinearLayout)itemView.findViewById(R.id.slot_container);

        }

        public void updateData(final TimingModel model, final String label) {

            if (model != null) {
                mLabel.setText(label);
                if (model.getState().equalsIgnoreCase("open")) {

                    mLabel.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                    mRoot.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            timingDetailCallback.showDetail(label,model);
                        }
                    });

                    mSlotContainer.removeAllViews();
                    for(int i=0;i<model.getTimings().length();i++){

                        JSONObject values = model.getTimings().optJSONObject(i);
                        mSlotContainer.addView(getSlotView(values.optString("st_time","00:00:00"),
                                values.optString("en_time","00:00:00")));
                    }
                } else {
                    mLabel.setTextColor(mContext.getResources().getColor(R.color.grey_97));
                    mSlotContainer.setOnClickListener(null);

                }
            }
        }
    }


    private View getSlotView(String opemTiming,String closetiming){

        View v = LayoutInflater.from(mContext).inflate(R.layout.slot_view,null);
        TextView mOpen = (TextView) v.findViewById(R.id.open_timing);

        TextView mClose = (TextView)v.findViewById(R.id.close_timing);

        if(!TextUtils.isEmpty(opemTiming))
            mOpen.setText(opemTiming);

        if(!TextUtils.isEmpty(closetiming))
            mClose.setText(closetiming);

        return v;
    }
    public Map<String,TimingModel> getTiming(){
        return this.timingModels;
    }

}
