package in.co.dineout.xoppin.dineoutcollection.model.pages;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.NameAndDetailFragment;
import in.co.dineout.xoppin.dineoutcollection.model.ReviewItem;

/**
 * Created by prateek.aggarwal on 5/2/16.
 */
public class BasicDetailPage extends Page {

    NameAndDetailFragment mBasicDetailFragment ;
    public BasicDetailPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {

        mBasicDetailFragment = NameAndDetailFragment.create(getKey());
        return mBasicDetailFragment;
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }

    @Override
    public boolean isCompleted() {
        if(mBasicDetailFragment != null)
        return mBasicDetailFragment.isDataValid();

        return false;
    }
}
