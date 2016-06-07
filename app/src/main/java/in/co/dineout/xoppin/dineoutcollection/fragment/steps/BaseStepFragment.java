package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import in.co.dineout.xoppin.dineoutcollection.fragment.MasterDataFragment;

/**
 * Created by Suraj on 22-02-2016.
 */
public abstract class BaseStepFragment extends MasterDataFragment {

    public abstract void saveDataForStep();
    public abstract void populateViewFromData();


    @Override
    public void onStop() {
        super.onStop();
//        saveDataForStep();
    }


}
