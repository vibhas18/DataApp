package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.helper.LoginHelper;
import in.co.dineout.xoppin.dineoutcollection.model.MreModel;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class DashboardFragment extends MasterDataFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard,container,false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MreModel user = LoginHelper.getUser(getActivity());
        if(user != null){
            initializeView(getView(),user);
        }
    }
    
    private void initializeView(View v,MreModel user){

        ((TextView) v.findViewById(R.id.tv_name)).setText(user.getFirstName() + " " + user.getLastName());
        ((TextView) v.findViewById(R.id.tv_email)).setText(user.getEmail());
        ((TextView) v.findViewById(R.id.tv_assigned_city)).setText(user.getAssignedCity());

        ((TextView) v.findViewById(R.id.tv_pending_synced)).setText("" +
                DatabaseManager.getInstance().getAllUnSyncedRestaurants().size());
        ((TextView) v.findViewById(R.id.tv_synced)).setText("" +
                DatabaseManager.getInstance().getAllSyncedRestaurants().size());

        v.findViewById(R.id.btn_assigned_tasks).setOnClickListener(this);

        v.findViewById(R.id.btn_pending).setOnClickListener(this);

        v.findViewById(R.id.btn_synced).setOnClickListener(this);

        v.findViewById(R.id.btn_new_rest).setOnClickListener(this);



//        initiateRequestForAssignedTasks();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_new_rest:
            {
                RestaurantFormFragment fragment = RestaurantFormFragment.newInstance(null,null);
                addToBackStack(getActivity(),fragment);
            }
                break;
            case R.id.btn_synced:
            {
               SyncRestaurantFragment fragment = SyncRestaurantFragment.newInstance();
                addToBackStack(getActivity(),fragment);
            }
            break;
            case R.id.btn_pending:
            {
                UnsyncedRestaurantFragment fragment = UnsyncedRestaurantFragment.newInstance();
                addToBackStack(getActivity(), fragment);
            }
            break;
            case R.id.btn_assigned_tasks:
            {
                TaskListFragment fragment = TaskListFragment.newInstance();
                addToBackStack(getActivity(), fragment);
            }
            break;
        }
    }
}
