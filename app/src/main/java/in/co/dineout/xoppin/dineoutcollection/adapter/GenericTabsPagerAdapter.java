package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ContactFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.CuisineFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.DetailFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ImageFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.NameAndDetailFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.steps.SlotTimingFragment;

/**
 * Created by suraj on 19/02/16.
 */
public class GenericTabsPagerAdapter extends FragmentStatePagerAdapter {
    private int mCutOffPage;
    private Fragment mPrimaryItem;
    private List<Fragment> mCurrentPageSequence;
    public GenericTabsPagerAdapter(FragmentManager fm) {
        super(fm);
        mCurrentPageSequence = new ArrayList<>();
        mCurrentPageSequence.add(NameAndDetailFragment.create());
        mCurrentPageSequence.add(DetailFragment.create());
        mCurrentPageSequence.add(ContactFragment.create());
        mCurrentPageSequence.add(SlotTimingFragment.create());
        mCurrentPageSequence.add(CuisineFragment.create());
        mCurrentPageSequence.add(ImageFragment.create());
    }

    @Override
    public Fragment getItem(int i) {
        return mCurrentPageSequence.get(i);
    }

    @Override
    public int getItemPosition(Object object) {
        // TODO: be smarter about this
        if (object == mPrimaryItem) {
            // Re-use the current fragment (its position never changes)
            return POSITION_UNCHANGED;
        }

        return POSITION_NONE;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        mPrimaryItem = (Fragment) object;
    }

    @Override
    public int getCount() {
        if (mCurrentPageSequence == null) {
            return 0;
        }
        return mCurrentPageSequence.size() ;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    private String[] title = {"Basic","Type","Contact","Timing","Cuisine","Image"};
}