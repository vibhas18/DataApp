package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.RestaurantFormActivity;
import in.co.dineout.xoppin.dineoutcollection.fragment.CuisineListFragment;
import in.co.dineout.xoppin.dineoutcollection.model.CuisineModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;

public class CuisineFragment extends BaseStepFragment {
    public static final String TAG2 = CuisineFragment.class.getCanonicalName();

    private LinearLayout llPrimaryCuisine;
    private LinearLayout llSecondaryCuisine;

    private ArrayList<CuisineModel> primaryCuisineModels;
    private ArrayList<CuisineModel> secondaryCuisineModels;

    public static CuisineFragment newInstance() {
        CuisineFragment fragment = new CuisineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuisine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        populateViewFromData();
    }

    private void initView(View view) {
        llPrimaryCuisine = (LinearLayout) view.findViewById(R.id.ll_primary_cuisine);
        llSecondaryCuisine = (LinearLayout) view.findViewById(R.id.ll_secondary_cuisine);

        primaryCuisineModels = new ArrayList<CuisineModel>(5);
        secondaryCuisineModels = new ArrayList<CuisineModel>(5);

        view.findViewById(R.id.btn_add_primary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CuisineListFragment fragment = CuisineListFragment.newInstance(primaryCuisineModels, CuisineListFragment.Mode.PRIMARY);
                fragment.setCallbacks(new CuisineListFragment.Callbacks() {
                    @Override
                    public void onCuisineSelected(final ArrayList<CuisineModel> cuisines) {
                        updatePrimaryCuisineView();
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, CuisineListFragment.TAG2).addToBackStack(CuisineListFragment.TAG2).commitAllowingStateLoss();
            }
        });

        view.findViewById(R.id.btn_add_secondary).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CuisineListFragment fragment = CuisineListFragment.newInstance(secondaryCuisineModels, CuisineListFragment.Mode.SECONDARY);
                fragment.setCallbacks(new CuisineListFragment.Callbacks() {
                    @Override
                    public void onCuisineSelected(ArrayList<CuisineModel> cuisines) {
                        upDateSecondaryCuisineView();
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().add(android.R.id.content, fragment, CuisineListFragment.TAG2).addToBackStack(CuisineListFragment.TAG2).commitAllowingStateLoss();
            }
        });

    }

    private void updatePrimaryCuisineView() {
        llPrimaryCuisine.removeAllViews();
        if (null != primaryCuisineModels && primaryCuisineModels.size() > 0) {
            for (CuisineModel cuisineModel : primaryCuisineModels) {
                TextView textView = new TextView(getActivity());
                textView.setText(cuisineModel.getCuisine_name());
                textView.setPadding(10, 10, 10, 10);
                llPrimaryCuisine.addView(textView);
            }
        }
    }

    private void upDateSecondaryCuisineView() {
        llSecondaryCuisine.removeAllViews();
        if (null != secondaryCuisineModels && secondaryCuisineModels.size() > 0) {
            for (CuisineModel cuisineModel : secondaryCuisineModels) {
                TextView textView = new TextView(getActivity());
                textView.setText(cuisineModel.getCuisine_name());
                textView.setPadding(10, 10, 10, 10);
                llSecondaryCuisine.addView(textView);
            }
        }
    }

    @Override
    public void onStepChanged() {

    }

    @Override
    public void saveDataForStep() {
        Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();
        if (null != primaryCuisineModels && primaryCuisineModels.size() > 0) {
            restaurant.setPrimary_cuisine(primaryCuisineModels);
        }
        if (null != secondaryCuisineModels && secondaryCuisineModels.size() > 0) {
            restaurant.setSecondary_cuisine(secondaryCuisineModels);
        }
        ((RestaurantFormActivity)getActivity()).saveRestaurantModel();
    }

    @Override
    public void populateViewFromData() {
        Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();

        if (restaurant.getPrimary_cuisine() != null && restaurant.getPrimary_cuisine().size() > 0) {
            primaryCuisineModels.addAll(restaurant.getPrimary_cuisine());
            updatePrimaryCuisineView();
        }

        if (restaurant.getSecondary_cuisine() != null && restaurant.getSecondary_cuisine().size() > 0) {
            secondaryCuisineModels.addAll(restaurant.getSecondary_cuisine());
            upDateSecondaryCuisineView();
        }
    }
}
