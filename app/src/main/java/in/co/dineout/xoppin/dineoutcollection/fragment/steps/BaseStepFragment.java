package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import android.support.v4.app.Fragment;

/**
 * Created by Suraj on 22-02-2016.
 */
public abstract class BaseStepFragment extends Fragment {
    public abstract void onStepChanged();
    public abstract void saveDataForStep();
    public abstract void populateViewFromData();
}
