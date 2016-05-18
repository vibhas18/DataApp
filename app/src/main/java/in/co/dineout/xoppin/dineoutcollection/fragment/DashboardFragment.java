package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datanetworkmodule.DataPreferences;

import in.co.dineout.xoppin.dineoutcollection.R;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class DashboardFragment extends MasterDataFragment implements View.OnClickListener {



    public static DashboardFragment newInstance(){

        DashboardFragment fragment = new DashboardFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dashboard,container,false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFragmentTitle("Dashboard");
        if(DataPreferences.getUserId(getActivity()) != null){
            initializeView(getView());
        }
    }
    
    private void initializeView(View v){

        ((TextView) v.findViewById(R.id.tv_name)).setText(DataPreferences.getFirstName(getContext())
                + " " + DataPreferences.getLastName(getContext()));
        ((TextView) v.findViewById(R.id.tv_email)).setText(DataPreferences.getEmail(getContext()));
        ((TextView) v.findViewById(R.id.tv_assigned_city)).setText(DataPreferences.getAssignedCity(getContext()));

        v.findViewById(R.id.btn_assigned_tasks).setOnClickListener(this);

        v.findViewById(R.id.btn_pending).setOnClickListener(this);

        v.findViewById(R.id.btn_synced).setOnClickListener(this);

        v.findViewById(R.id.btn_new_rest).setOnClickListener(this);
        v.findViewById(R.id.btn_editing).setOnClickListener(this);
        v.findViewById(R.id.logout_option).setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_new_rest:
            {
                RestaurantFormFragment fragment = RestaurantFormFragment.newInstance(null);
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
            case R.id.btn_editing:
            {
                EditingRestaurantFragment fragment = EditingRestaurantFragment.newInstance();
                addToBackStack(getActivity(), fragment);
                break;
            }
            case R.id.logout_option:
            {

                FragmentActivity activity = getActivity();
                DataPreferences.logoutUser(getContext());
               activity.getSupportFragmentManager().popBackStackImmediate();
                LoginFragment fragment = LoginFragment.newInstance();
                addToBackStack(activity,fragment);
                break;
            }
        }
    }
}
