package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.RestaurantDetailListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class SyncRestaurantFragment extends MasterDataFragment {


    public static SyncRestaurantFragment newInstance(){

        SyncRestaurantFragment fragment = new SyncRestaurantFragment();
        return fragment;
    }
    private RestaurantDetailListAdapter restaurantDetailListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_synced_unsynced,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = (ListView) getView().findViewById(R.id.lv_list);

        restaurantDetailListAdapter = new RestaurantDetailListAdapter(getActivity(),
                DatabaseManager.getInstance().getAllSyncedRestaurants());

        listView.setAdapter(restaurantDetailListAdapter);
    }
}
