package in.co.dineout.xoppin.dineoutcollection.model.pages;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.CuisineFragment;
import in.co.dineout.xoppin.dineoutcollection.model.ReviewItem;

/**
 * Created by prateek.aggarwal on 5/2/16.
 */
public class CuisinePage extends Page {

    private CuisineFragment mFragment ;
    public CuisinePage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {

        mFragment = CuisineFragment.create(getKey());
        return mFragment;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    public boolean isCompleted() {
        try{
            if(mFragment != null)
                return mFragment.isDataValid();
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
