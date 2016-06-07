package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.database.ImageEntry;
import in.co.dineout.xoppin.dineoutcollection.fragment.ImageUploadFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

public class ImageFragment extends BaseStepFragment {

    public ImageFragment() {
        // Required empty public constructor
    }

    private TextView tvProfileImageCount;
    private TextView tvMenuImagesCount;
    private TextView tvAddProfileImage;
    private TextView tvAddMenuImage;

    private TextView tv_sync;

    public static ImageFragment create() {
        ImageFragment fragment = new ImageFragment();

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

                ImageUploadFragment fragment = ImageUploadFragment.newInstance(
                        ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel().getRestaurantId(), ImageEntry.MENU_IMAGE);
                addToBackStack(getActivity(),fragment);
            }
        });

        tvAddProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImageUploadFragment fragment = ImageUploadFragment.newInstance(
                        ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel().getRestaurantId(), ImageEntry.PROFILE_IMAGE);
                addToBackStack(getActivity(),fragment);
            }
        });

        tv_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(getParentFragment() != null && getParentFragment() instanceof RestaurantFormFragment){
                    ((RestaurantFormFragment)getParentFragment()).sendRestaurantForSyncing();
                }
            }
        });

        populateViewFromData();
    }


    @Override
    public void saveDataForStep() {

    }

    @Override
    public void populateViewFromData() {
        RestaurantDetailsModel restaurantDetailsModel = ((RestaurantFormFragment) getParentFragment()).getRestaurantDetailsModel();
        tvProfileImageCount.setText(DataDatabaseUtils.getInstance(getActivity()).getPendingImage(restaurantDetailsModel.getRestaurantId(),ImageEntry.PROFILE_IMAGE).size()+" pending images");
        tvMenuImagesCount.setText(DataDatabaseUtils.getInstance(getActivity()).getPendingImage(restaurantDetailsModel.getRestaurantId(),ImageEntry.MENU_IMAGE).size()+" pending images");

    }


}
