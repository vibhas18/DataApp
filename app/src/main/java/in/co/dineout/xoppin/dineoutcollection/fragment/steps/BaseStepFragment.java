package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import android.app.Activity;
import android.os.Bundle;

import in.co.dineout.xoppin.dineoutcollection.fragment.MasterDataFragment;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.model.PageFragmentCallbacks;
import in.co.dineout.xoppin.dineoutcollection.model.pages.Page;

/**
 * Created by Suraj on 22-02-2016.
 */
public abstract class BaseStepFragment extends MasterDataFragment {

    protected  static final String ARG_KEY = "key";
    public abstract void onStepChanged();
    public abstract void saveDataForStep();
    public abstract void populateViewFromData();
    public abstract boolean isDataValid();
    private String mKey;
    protected Page mPage;
    protected PageFragmentCallbacks mCallbacks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mKey = args.getString(ARG_KEY);
        mPage = mCallbacks.onGetPage(mKey);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getParentFragment()==null) {
            throw new ClassCastException("ParentFragment must be there and should implement PageFragmentCallbacks");
        }

        mCallbacks = (PageFragmentCallbacks) ((RestaurantFormFragment)getParentFragment());
    }

    protected synchronized void notifyChanges(){

        if(getActivity() != null)
        mPage.notifyDataChanged();

    }
}
