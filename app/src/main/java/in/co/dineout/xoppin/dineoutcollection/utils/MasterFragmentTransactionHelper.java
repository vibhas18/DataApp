package in.co.dineout.xoppin.dineoutcollection.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.MasterDataFragment;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class MasterFragmentTransactionHelper {

    private static final ArrayList<Runnable> capturedEvent = new ArrayList<>();

    // initial true;
    private static boolean mStateSave = true;

    public static void setSaveState(boolean saveState) {
        mStateSave = saveState;
    }

    public static void replace(final FragmentManager fragmentManager,
                               final int targetId, final Fragment fragment,
                               final int enter, final int exit, final int popEnter,
                               final int popExit, final boolean isAddToBackStack) {

//		final Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {


        Fragment currentFragment = (Fragment) fragmentManager
                .findFragmentById(R.id.fragment_base_container);


        android.support.v4.app.FragmentTransaction transaction = fragmentManager
                .beginTransaction();
        transaction.setTransition( android.support.v4.app.FragmentTransaction.TRANSIT_NONE);
        if (enter == MasterDataFragment.NO_ANIMATION
                && exit == MasterDataFragment.NO_ANIMATION
                && popEnter == MasterDataFragment.NO_ANIMATION
                && popExit == MasterDataFragment.NO_ANIMATION) {
//					transaction.setCustomAnimations(R.anim.f_enter,
//							R.anim.f_exit, R.anim.f_pop_enter,
//							R.anim.f_pop_exit);

        } else {
            transaction.setCustomAnimations(enter, exit, popEnter,
                    popExit);
        }

        try {
            transaction.replace(targetId, fragment,
                    fragment.getClass().getName());
            if (isAddToBackStack) {

                transaction
                        .addToBackStack(fragment.getClass().getName());
            } else {
                if (currentFragment != null) {
                    transaction.remove(currentFragment);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
//			}
//		};

//		executeAction(runnable);
    }

    public static void popBackStackTo(final FragmentManager manager,
                                      final FragmentManager.BackStackEntry entry, final int flag) {

//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
        manager.popBackStack(entry.getId(), flag);
//			}
//		};
//		executeAction(runnable);
    }

    public static void removeFragmentByTag(final FragmentManager manager,
                                           final String tag) {
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            remove(manager, fragment);
        }
    }

    public static void remove(final FragmentManager manager,
                              final Fragment fragment) {
//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
        try {
            manager.beginTransaction().remove(fragment).commit();
        } catch (IllegalStateException ex) {
            manager.beginTransaction().remove(fragment)
                    .commitAllowingStateLoss();
        }
//			}
//		};
//		executeAction(runnable);
    }

    public static void removeAllFragments(final FragmentManager manager,
                                          final int flag) {

//		Runnable runnable = new Runnable() {
//
//			@Override
//			public void run() {
        if (manager != null && manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry backStackEntry = manager
                    .getBackStackEntryAt(0);
            manager.popBackStack(backStackEntry.getId(), flag);
            //manager.popBackStackImmediate();
        }
        //}
//		};
//		executeAction(runnable);
    }

    private static void executeAction(Runnable runnable) {
        if (mStateSave) {
            capturedEvent.add(runnable);
        } else {
            runnable.run();
        }
    }

    public static void executePendingEvents() {
        if (!mStateSave) {
            while (capturedEvent.size() > 0) {
                capturedEvent.remove(0).run();
            }
        }
    }
}

