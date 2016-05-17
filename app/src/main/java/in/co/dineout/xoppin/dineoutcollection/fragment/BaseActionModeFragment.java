package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by suraj on 19/02/16.
 */
public abstract class BaseActionModeFragment extends Fragment implements ActionMode.Callback, FragmentManager.OnBackStackChangedListener {
    public static final String TAG = BaseActionModeFragment.class.getSimpleName();

    public static final String KEY_TITLE = "KEY_TITLE";
    public static final String KEY_SUBTITLE = "KEY_SUBTITLE";

    private ActionMode actionMode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().addOnBackStackChangedListener(this);
    }

    private void setupActionMode() {
//        if (getActivity() instanceof RestaurantFormActivity) {
//            ActionMode actionMode = ((RestaurantFormActivity) getActivity()).getActionMode();
//            if (actionMode != null) {
//                actionMode.setTag(Boolean.TRUE);
//            }
//        }

        actionMode = getActivity().startActionMode(this);
    }

    protected ActionMode getActionMode() {
        return actionMode;
    }

    @Override
    public void onBackStackChanged() {
        if (getFragmentManager() != null) {
            int count = getFragmentManager().getBackStackEntryCount();
            FragmentManager.BackStackEntry entry = getFragmentManager().getBackStackEntryAt(count - 1);
            Log.i(TAG, "Stack Top: " + entry.getName() + ", Stack Height: " + count);
            if (getClass().getCanonicalName().equals(entry.getName())) {
                setupActionMode();
            }
        }
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem menuItem) {
        return false;
    }

    ;

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menuItem) {
        if (getArguments().containsKey(KEY_TITLE)) {
            mode.setTitle(getArguments().getString(KEY_TITLE));
        }
        if (getArguments().containsKey(KEY_SUBTITLE)) {
            mode.setSubtitle(getArguments().getString(KEY_SUBTITLE));
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menuItem) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        Log.i(getClass().getSimpleName(), "onDestroyActionMode: " + (mode.getTag() != null && mode.getTag().equals(Boolean.TRUE)));
        if (Boolean.TRUE.equals(mode.getTag())) {
            return;
        }

        if (getActivity() != null && !getActivity().isFinishing() && !isRemoving() && isAdded()) {
            getFragmentManager().popBackStack();
        }
    }
}
