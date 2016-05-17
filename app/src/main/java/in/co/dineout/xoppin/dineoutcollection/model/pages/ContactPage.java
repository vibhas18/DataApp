package in.co.dineout.xoppin.dineoutcollection.model.pages;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ContactFragment;
import in.co.dineout.xoppin.dineoutcollection.model.ReviewItem;

/**
 * Created by prateek.aggarwal on 5/3/16.
 */
public class ContactPage extends Page {

    private ContactFragment mFragment ;

    public ContactPage(ModelCallbacks callbacks, String title) {

        super(callbacks, title);
    }

    @Override
    public Fragment createFragment() {

        mFragment = ContactFragment.create(getKey());
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