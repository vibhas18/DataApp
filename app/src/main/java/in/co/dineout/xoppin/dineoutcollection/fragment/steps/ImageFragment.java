package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.ImageUploadListActivity;
import in.co.dineout.xoppin.dineoutcollection.activity.RestaurantFormActivity;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.SyncStatusModel;

public class ImageFragment extends BaseStepFragment {

    public ImageFragment() {
        // Required empty public constructor
    }

    private TextView tvProfileImageCount;
    private TextView tvMenuImagesCount;
    private TextView tvAddProfileImage;
    private TextView tvAddMenuImage;

    private TextView tv_sync;

    public static ImageFragment newInstance() {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvAddMenuImage = (TextView) view.findViewById(R.id.tv_add_menu_image);
        tvAddProfileImage = (TextView) view.findViewById(R.id.tv_add_profile_image);
        tvMenuImagesCount = (TextView) view.findViewById(R.id.tv_menu_image_count);
        tvProfileImageCount = (TextView) view.findViewById(R.id.tv_profile_image_count);
        tv_sync = (TextView) view.findViewById(R.id.tv_sync);

        tvAddMenuImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ImageUploadListActivity.class);
                i.setAction(ImageUploadListActivity.ACTION_MENU_IMAGES);
                i.putExtra(ImageUploadListActivity.RESTAURANT_DETAIL_ID, ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel().getId());
                startActivity(i);
            }
        });

        tvAddProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ImageUploadListActivity.class);
                i.setAction(ImageUploadListActivity.ACTION_PROFILE_IMAGES);
                i.putExtra(ImageUploadListActivity.RESTAURANT_DETAIL_ID, ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel().getId());
                i.putExtra(ImageUploadListActivity.RESTAURANT_NAME, ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel().getRestaurantName());
                startActivity(i);
            }
        });

        tv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RestaurantFormActivity)getActivity()).getRestaurantDetailsModel().sendToSync(getActivity());
            }
        });

        populateViewFromData();
    }

    @Override
    public void onStepChanged() {

    }

    @Override
    public void saveDataForStep() {

    }

    @Override
    public void populateViewFromData() {
        RestaurantDetailsModel restaurantDetailsModel = ((RestaurantFormActivity) getActivity()).getRestaurantDetailsModel();
        tvProfileImageCount.setText("" + DatabaseManager.getInstance().getImageCountForTypeAndRestaurant(restaurantDetailsModel.getId(), SyncStatusModel.PROFILE));
        tvMenuImagesCount.setText("" + DatabaseManager.getInstance().getImageCountForTypeAndRestaurant(restaurantDetailsModel.getId(), SyncStatusModel.MENU));
    }
}
