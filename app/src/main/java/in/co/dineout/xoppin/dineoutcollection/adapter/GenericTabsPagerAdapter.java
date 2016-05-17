package in.co.dineout.xoppin.dineoutcollection.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.fragment.steps.ImageFragment;
import in.co.dineout.xoppin.dineoutcollection.model.pages.Page;

/**
 * Created by suraj on 19/02/16.
 */
public class GenericTabsPagerAdapter extends FragmentStatePagerAdapter {
    private int mCutOffPage;
    private Fragment mPrimaryItem;
    private List<Page> mCurrentPageSequence;
    public GenericTabsPagerAdapter(FragmentManager fm,List<Page> sequence) {
        super(fm);

        mCurrentPageSequence = sequence;
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= mCurrentPageSequence.size()) {
            return ImageFragment.create("Image");
        }

        return mCurrentPageSequence.get(i).createFragment();
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
        return Math.min(mCutOffPage + 1, mCurrentPageSequence.size() + 1);
    }

    public void setCutOffPage(int cutOffPage) {
        if (cutOffPage < 0) {
            cutOffPage = Integer.MAX_VALUE;
        }
        mCutOffPage = cutOffPage;
        notifyDataSetChanged();
    }

    public int getCutOffPage() {
        return mCutOffPage;
    }

    public void updateData(List<Page> sequence){
        this.mCurrentPageSequence = sequence;
        notifyDataSetChanged();
    }

}