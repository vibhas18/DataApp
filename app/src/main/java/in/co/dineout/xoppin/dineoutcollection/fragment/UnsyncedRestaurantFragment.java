package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.RestaurantDetailListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.service.RestaurantBackgroundService;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class UnsyncedRestaurantFragment extends MasterDataFragment implements View.OnClickListener {

    private RestaurantDetailListAdapter restaurantDetailListAdapter;

    public static UnsyncedRestaurantFragment newInstance(){

        UnsyncedRestaurantFragment fragment = new UnsyncedRestaurantFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_synced_unsynced,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setFragmentTitle("Restaurants waiting to be synced");

        ListView listView = (ListView) getView().findViewById(R.id.lv_list);
        View mSyncDispatcher = getView().findViewById(R.id.sync_container);
        mSyncDispatcher.setVisibility(View.VISIBLE);
        mSyncDispatcher.setOnClickListener(this);
        restaurantDetailListAdapter = new RestaurantDetailListAdapter(getActivity(),
                DataDatabaseUtils.getInstance(getContext()).getUnsynedRestaurant());
        listView.setAdapter(restaurantDetailListAdapter);
    }

    @Override
    public void onClick(View v) {

        if(restaurantDetailListAdapter != null && restaurantDetailListAdapter.getCount() > 0){
            Toast.makeText(getActivity(),"Sync initiated",Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getContext(), RestaurantBackgroundService.class);
        getContext().startService(intent);
        popToHome(getActivity());}
    }
}