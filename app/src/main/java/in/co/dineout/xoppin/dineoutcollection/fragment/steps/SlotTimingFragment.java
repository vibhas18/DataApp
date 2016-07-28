package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.TimingAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.AddTimingCallback;
import in.co.dineout.xoppin.dineoutcollection.fragment.AddTimingDialog;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.TimingDetailCallback;
import in.co.dineout.xoppin.dineoutcollection.model.TimingModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by prateek.aggarwal on 7/19/16.
 */
public class SlotTimingFragment extends BaseStepFragment implements TimingDetailCallback,AddTimingCallback,View.OnClickListener {

//    private List<TimingModel> mTimingList;
    private TimingAdapter timingAdapter;
    private RecyclerView mTimingView;

    public static SlotTimingFragment create() {
        SlotTimingFragment fragment = new SlotTimingFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.slot_timing_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTimingView = (RecyclerView)view.findViewById(R.id.timing_list);

        mTimingView.setLayoutManager(new LinearLayoutManager(getActivity()));
        timingAdapter = new TimingAdapter(getActivity(),null,this);
        mTimingView.setAdapter(timingAdapter);

        view.findViewById(R.id.add_timing).setOnClickListener(this);

        populateViewFromData();


    }

    @Override
    public void saveDataForStep() {

        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();
        restaurant.setSlotTimings( timingAdapter.getTiming());
    }

    @Override
    public void populateViewFromData() {

        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();

        if(restaurant.getSlotOpenTiming() != null){
            timingAdapter.updateMap(restaurant.getSlotOpenTiming());
        }
    }


    private void showAddTimingDialog(String label,TimingModel model){

       AddTimingDialog dialog =  AddTimingDialog.newInstance(model,label,this);

        dialog.show(getActivity().getSupportFragmentManager(),"timing");
    }

    @Override
    public void showDetail(String label, TimingModel model) {

        showAddTimingDialog(label,model);
    }

    @Override
    public void addSlots(HashMap<String, TimingModel> update) {

        timingAdapter.updateMap(update);
    }

    @Override
    public void deleteTiming(String key) {

      timingAdapter.deleteTiming(key);

    }

    @Override
    public void onClick(View v) {

        showAddTimingDialog(null,null);
    }
}
