package in.co.dineout.xoppin.dineoutcollection.model.pages;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.TimingsFragment;
import in.co.dineout.xoppin.dineoutcollection.model.ReviewItem;

/**
 * Created by prateek.aggarwal on 5/2/16.
 */

public class TimingPage extends Page {


    public TimingPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {
        return TimingsFragment.create(getKey());
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {

    }
}

