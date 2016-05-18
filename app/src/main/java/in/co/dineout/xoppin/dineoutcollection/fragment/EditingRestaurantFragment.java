package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.RestaurantDetailListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class EditingRestaurantFragment extends MasterDataFragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener {


    private RestaurantDetailListAdapter restaurantDetailListAdapter;

    public static EditingRestaurantFragment newInstance(){

        EditingRestaurantFragment fragment = new EditingRestaurantFragment();
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

        setFragmentTitle("Restaurant In Edit Mode");
        ListView listView = (ListView) getView().findViewById(R.id.lv_list);

        restaurantDetailListAdapter = new RestaurantDetailListAdapter(getActivity(),
                DataDatabaseUtils.getInstance(getContext()).getPendingRestaurant());

        listView.setAdapter(restaurantDetailListAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

    }

    private void refreshAdapter(){
        restaurantDetailListAdapter.updateData(DataDatabaseUtils.getInstance(getContext()).getPendingRestaurant());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        showRestaurantDetail(position);
    }

    private void showRestaurantDetail(int position){
        RestaurantFormFragment fragment = RestaurantFormFragment.
                newInstance(restaurantDetailListAdapter.getItem(position));

        addToBackStack(getActivity(),fragment);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Syncing...");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want Sync this? Once it is sent to sync it cannot be edited.");

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("Sync", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                RestaurantDetailsModel model = restaurantDetailListAdapter.getItem(position);

                if(model != null){

                    int index = model.validateRestaurant(getActivity());
                    if(index == -1){
                        Utils.sendToSync(getActivity(),restaurantDetailListAdapter.getItem(position));
                        refreshAdapter();
                    }else{
                        RestaurantFormFragment fragment = RestaurantFormFragment.
                                newInstance(model,index);

                        addToBackStack(getActivity(),fragment);
                    }
                }


            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
        
        return true;
    }
}