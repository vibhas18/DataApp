package in.co.dineout.xoppin.dineoutcollection.model.pages;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.DetailFragment;
import in.co.dineout.xoppin.dineoutcollection.model.ReviewItem;

/**
 * Created by prateek.aggarwal on 5/2/16.
 */
public class RestDetailPage extends Page {


    private DetailFragment mFragment ;

    public RestDetailPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {

        mFragment = DetailFragment.create(getKey());
        return mFragment;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    public boolean isCompleted() {
        if(mFragment != null)
            return mFragment.isDataValid();

        return false;
    }
}
