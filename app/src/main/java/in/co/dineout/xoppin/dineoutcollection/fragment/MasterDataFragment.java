package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dineout.android.volley.Request;
import com.dineout.android.volley.Response;
import com.dineout.android.volley.VolleyError;
import com.dineout.android.volley.toolbox.ImageLoader;
import com.example.datanetworkmodule.DineoutNetworkManager;
import com.example.datanetworkmodule.ImageRequestManager;

import org.json.JSONObject;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.utils.MasterFragmentTransactionHelper;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class MasterDataFragment extends DialogFragment {


    public static final int STYLE_NO_FRAME = DialogFragment.STYLE_NO_FRAME;
    public static final int STYLE_NO_TITLE = DialogFragment.STYLE_NO_TITLE;
    public static final int STYLE_NORMAL = DialogFragment.STYLE_NORMAL;
    public static final String EXIT_ON_BACK_PRESS = "exitOnBackPress";
   
    private static final String TITLE = "base_title_key";
    private static final String IS_CHILD_FRAGMENT = "is_child_fragment";
    // Arbitrary value; set it to some reasonable default
    private static final int DEFAULT_CHILD_ANIMATION_DURATION = 400;
    public static int NO_ANIMATION = -1;
    private static String uptime = null;
    /**
     * is scrollable header scroll to top completely or not
     */
    protected boolean isScrolledToTopCompletely;
    ProgressBar progressBar;
    private Toolbar mToolbar;
    //    TcConditionalDialogView networkErrorView;
    private float scrollContainerY;
    private boolean pageCurrent = true;
    private String exceptionType;

    /*
     * set true if its a child fragment, Normally viewPager child fragemnt are child
     * fragments *
     */
    private boolean childFragment;
    private String title;

    /*
     * to snapdeal logo by default other wise override {@code getLogo} and
	 * return the logo reg id. *
	 */

    private boolean isLastScrollingDown;
    /**
     * set if header is auto reset when user finger up.Default values is true.
     **/
    private boolean isAutoResetHeader = true;
    /**
     * network manager will available in every fragment, user for making network
     * volley request
     **/
    private DineoutNetworkManager networkManager;
    /**
     * Image request manager, it will avaibale in every child class and
     * adapters, So dev no need create a another one.
     */
    private ImageRequestManager imageRequestManager;
    /**
     * Hold all request that are failed due any reason, And call
     * {@code retryRequestFail} for every request. Developer can decide in
     * retryRequestFail method weather request should be resent or not by using
     * request identifier.
     **/
    private Map<Integer, Map.Entry<Request<?>, VolleyError>> failedRequestsMap;

    /**
     * menu inflater for inflating menus. we are not using Action bar and not
     * setting tool bar into Actvity actionbar , So we have to inflate menu item
     * ourself. But we are supporting original callback for creating and
     * selecting menuitem.
     **/
    //private SupportMenuInflater menuInflater;
    /**
     * hide menu items from overflow menu that is comman for all screen.
     **/
    /*
     * Hold all success request, Used to for sending unnecessary data on ui or
     * cache response to prevent flickering on screen.
     */
    private Map<Integer, String> successfulRequests;
    /*
     * default handler for a fragment. *
     */
    private Handler mHandler;
    private OnFragmentDialogDismissListener onFragmentDialogDismissListener;
    //TODO - Added by Shashank to handle showing Progress Wheel on Fragments [Dated: 30 March'16]
//    private DOLoaderDialog progressDialog;

    /**
     * Fragment replace methods **
     */

    public static void addToBackStack(FragmentActivity activity,
                                      MasterDataFragment fragment, boolean defaultAnimation) {
        addToBackStack(activity.getSupportFragmentManager(), fragment,
                defaultAnimation);
    }

    public static void addToBackStack(final FragmentManager manager,
                                      MasterDataFragment fragment, boolean defaultAnimation) {

        if (defaultAnimation) {
            addToBackStack(manager, fragment);
        } else {
            replace(manager, R.id.fragment_base_container, fragment, 0, 0, 0, 0, true);
        }
    }

    public static void addToBackStack(FragmentActivity activity,
                                      MasterDataFragment fragment) {
        addToBackStack(activity.getSupportFragmentManager(), fragment);
    }

    public static void addToBackStack(final FragmentManager manager,
                                      MasterDataFragment fragment) {
        addToBackStack(manager, fragment, R.id.fragment_base_container);
    }

    public static void addToBackStack(final FragmentManager manager,
                                      MasterDataFragment fragment, int target) {
        replace(manager, target, fragment, NO_ANIMATION, NO_ANIMATION,
                NO_ANIMATION, NO_ANIMATION, true);
    }

    public static void addToBackStack(final FragmentManager manager,
                                      MasterDataFragment fragment, int enter, int exit) {
        addToBackStack(manager, R.id.fragment_base_container, fragment, enter, exit);
    }

    public static void addToBackStack(final FragmentManager manager,
                                      int targetId, MasterDataFragment fragment, int enter, int exit) {
        replace(manager, targetId, fragment, enter, exit, 0, 0, true);
    }

    public static void replace(FragmentActivity activity,
                               MasterDataFragment fragment) {
        replace(activity.getSupportFragmentManager(), R.id.fragment_base_container,
                fragment, NO_ANIMATION, NO_ANIMATION, NO_ANIMATION,
                NO_ANIMATION, true);
    }

    public static void removeTopAndAddToBackStack(FragmentActivity activity,
                                                  MasterDataFragment fragment) {
        popBackStack(activity.getSupportFragmentManager());
        replace(activity.getSupportFragmentManager(), R.id.fragment_base_container,
                fragment, NO_ANIMATION, NO_ANIMATION, NO_ANIMATION,
                NO_ANIMATION, true);
    }

    public static void replace(FragmentActivity activity, int targetId,
                               MasterDataFragment fragment) {
        replace(activity.getSupportFragmentManager(), targetId, fragment);
    }

    public static void replace(FragmentManager fragmentManager, int targetId,
                               MasterDataFragment fragment) {
        // may provide default animation
        replace(fragmentManager, targetId, fragment, NO_ANIMATION,
                NO_ANIMATION, NO_ANIMATION, NO_ANIMATION, false);
    }

    public static void replace(FragmentManager fragmentManager, int targetId,
                               MasterDataFragment fragment, boolean defaultAnimation) {
        // may provide default animation
        if (defaultAnimation) {
            replace(fragmentManager, targetId, fragment);
        } else {
            replace(fragmentManager, targetId, fragment, 0, 0, 0, 0, false);
        }
    }

    public static void replace(FragmentManager fragmentManager, int targetId,
                               MasterDataFragment fragment, int enter, int exit, int popEnter,
                               int popExit, boolean isAddToBackStack) {

        MasterFragmentTransactionHelper.replace(fragmentManager, targetId,
                fragment,
                enter, exit, popEnter, popExit, isAddToBackStack);
    }

    public static void popBackStack(FragmentManager manager) {
        if (manager != null) {
            try {
                manager.popBackStack();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void popToHome(FragmentActivity activity) {
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1) {
            popBackStackTo(fragmentManager,
                    fragmentManager.getBackStackEntryAt(1));
        }
    }

    public static void popBackStackTo(FragmentManager manager,
                                      FragmentManager.BackStackEntry entry) {
        MasterFragmentTransactionHelper.popBackStackTo(manager, entry,
                FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void showFragment(FragmentManager manager, MasterDataFragment fragment) {
        FragmentTransaction ft = manager.beginTransaction();
        Fragment previousDialog = manager.findFragmentByTag("dialog");

        if (previousDialog != null) {
            ft.remove(previousDialog);
        }

        //ft.addToBackStack(null);
        if (fragment != null) {
            fragment.show(manager, "dialog");
        }
    }

    private void initializeToolbar() {

        mToolbar = (Toolbar) getView().findViewById(R.id.toolbar_fragment);

        if (mToolbar != null) {
//            mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_navigation_arrow_back));
            mToolbar.setTitleTextColor(getResources().getColor(R.color.white));
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleNavigation();
                }
            });


        }
    }

    public void handleNavigation() {
        popBackStack(getActivity().getSupportFragmentManager());
    }

    public boolean isPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(boolean pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    protected void onRequestLoadData() {

    }

    public OnFragmentDialogDismissListener getOnFragmentDialogDismissListener() {
        return onFragmentDialogDismissListener;
    }

    public void setOnFragmentDialogDismissListener(
            OnFragmentDialogDismissListener onFragmentDialogDismissListener) {
        this.onFragmentDialogDismissListener = onFragmentDialogDismissListener;
    }

    /**
     * Return handler if already created otherwise create new one.
     **/
    public Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        return mHandler;
    }

    /**
     * Fragment Life method start. **
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Log.d("Test", "Inside onCreate");
        successfulRequests = new HashMap<>();
        setHasOptionsMenu(true);
        networkManager = DineoutNetworkManager.newInstance(getActivity(), "");
        imageRequestManager = ImageRequestManager.getInstance(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @CallSuper
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeToolbar();
        //scrollableContainer = getView().findViewById
        //		(getScrollableContainerId());


//					progressBar = (ProgressBar) getView().findViewById(R.id
//							.progressBarFragment);
//					networkErrorView = (TcConditionalDialogView) getView().findViewById(R.id.networkErrorView);
    }

    @CallSuper
    @Override
    public void onStart() {
        super.onStart();
        onNetworkConnectionChanged(true);


    }

    /**
     * Return true if overflow menu will be show other wise return false
     **/
    protected boolean isShowOverFlowMenu() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int i = item.getItemId();
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        saveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    private void saveInstanceState(Bundle outState) {

    }

    /**
     * Save all current header state into bundle. Title,logo,navigation
     * icon,overflow menu status,chilFragment
     */
    private void saveHeaderValues(Bundle outState) {

        outState.putString(TITLE, this.title);

        outState.putBoolean(IS_CHILD_FRAGMENT, isChildFragment());
    }

    private void restoreHeaderValues(Bundle savedInstanceState) {


        childFragment = savedInstanceState.getBoolean(IS_CHILD_FRAGMENT,
                childFragment);
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (this.onFragmentDialogDismissListener != null) {
            this.onFragmentDialogDismissListener.onDismiss(this);
        }
    }

    @Override
    public void onDestroyView() {

        networkManager.cancel();


        super.onDestroyView();
//        getNetworkManager().cancel();
    }

    /**
     * Return hidable container height.
     **/
    private int getScrollableOffset() {

        return getScrollableOffset();


    }

    public boolean isChildFragment() {
        return childFragment;
    }

    public void setChildFragment(boolean childFragment) {
        this.childFragment = childFragment;
    }

    public void setTitle(String title) {


        this.title = title;
        setFragmentTitle(title);
//        changeTitle();
    }

    public boolean isAutoResetHeader() {
        return isAutoResetHeader;
    }

    /**
     * set auto hide header.
     *
     * @param isAutoResetHeader (true or false)
     **/
    public void setAutoResetHeader(boolean isAutoResetHeader) {
        this.isAutoResetHeader = isAutoResetHeader;
    }

    private void changeTitle() {

        setTitle(title);

    }

    /**
     * Called from child class to show loader if exist in xml layout.
     */
    protected void showLoader() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Called from child class to hide loader if exist in xml layout.
     */
    protected void hideLoader() {

        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    public DineoutNetworkManager getNetworkManager() {
        return networkManager;
    }
//
    public ImageLoader getImageLoader() {
        return imageRequestManager.getImageLoader();
    }

    /***
     * override this and return true if child is handling backpress (Toolbar and
     * device back skipTourButton)
     **/
    public boolean onPopBackStack() {
        return false;
    }

    /**
     * Called for visible fragment only
     *
     * @param connected (true if connectivity available)
     **/
    public void onNetworkConnectionChanged(boolean connected) {

        // /if connected remove the error view and retry fail requests again
        if (connected) {
            onRemoveErrorView();
//            retryFailedRequests();
        }

        // call child fragment {@code onNetworkConnectionChanged}
        FragmentManager childManager = getChildFragmentManager();
        // /send to child fragment
        List<Fragment> fragments = childManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null
                        && fragment instanceof MasterDataFragment) {
                    ((MasterDataFragment) fragment)
                            .onNetworkConnectionChanged(connected);
                }
            }
        }
    }

    /**
     * Retry fail request to fetch data and clear the map. If they failed again
     * will added again in volley call back
     */
    protected final void retryFailedRequests() {
        if (this.failedRequestsMap != null) {
            Set<Map.Entry<Integer, Map.Entry<Request<?>, VolleyError>>> entries = this.failedRequestsMap
                    .entrySet();
            for (Map.Entry<Integer, Map.Entry<Request<?>, VolleyError>> entry : entries) {
                Map.Entry<Request<?>, VolleyError> value = entry.getValue();
                retryFailedRequest(entry.getKey(), value.getKey(),
                        value.getValue());
            }
            this.failedRequestsMap.clear();
        }
    }

    /**
     * Should be implemented for retrying failed requests. It is invoked when
     * app connects to network or explicit retryFailedRequests() is invoked
     *
     * @param identifier Request Identifier
     * @param oldRequest Failed Request. Don't try to reuse.
     * @param error      Failed error
     */
    protected void retryFailedRequest(Integer identifier, Request<?> oldRequest,
                                      VolleyError error) {

    }

    /***
     * Add fail request to fail requests map
     *
     * @param request that will added
     * @param error   volley error due this request is get failed
     */
    private void addFailedRequest(Request<?> request, VolleyError error) {
        Map.Entry<Request<?>, VolleyError> failedEntry = new AbstractMap.SimpleImmutableEntry<Request<?>, VolleyError>(
                request, error);
        if (this.failedRequestsMap == null) {
            this.failedRequestsMap = new HashMap<>();
        }
        this.failedRequestsMap.put(request.getIdentifier(), failedEntry);
    }

    private void removeAnyFailedRequests(int identifier) {
        if (this.failedRequestsMap != null) {
            this.failedRequestsMap.remove(identifier);
        }
    }

    protected boolean shouldShowNetworkErrorView(Request<?> request,
                                                 VolleyError error) {
        final String oldUrl = successfulRequests.get(request.getIdentifier());
        final String newUrl = request.getUrl();
        if (!TextUtils.isEmpty(oldUrl)) {
            successfulRequests.put(request.getIdentifier(), newUrl);
        }
        return !newUrl.equals(oldUrl);
    }

    /**
     * Call this function to show specific type of error screen
     *
     * @param errorType one of {@code NetworkErrorView.ERROR_CONNECTIVITY,
     *                  NetworkErrorView.ERROR_SERVER_INTERNAL,
     *                  NetworkErrorView.ERROR_HEAVY_TRAFFIC}
     ***/
    protected void showNetworkErrorView(int errorType) {

//        if (networkErrorView != null) {
//            networkErrorView.setVisibility(View.VISIBLE);
//				if (networkErrorView instanceof NetworkErrorView) {
//					String returnmessage = ((NetworkErrorView) networkErrorView)
//							.setErrorType(errorType, getNetworkMessage(), getErrorMessage(),
//							getUptime());
//					setExceptionType(returnmessage);
//				}

//        }
    }

    public void onNetworkConnectivityChanged(boolean isConnected) {

    }

//    protected boolean shouldRemoveNetworkErrorView(Request<?> request) {
//        return true;
//    }

    /**
     * remove error view, Can called whenever you want to remove error view.
     ***/
    protected void onRemoveErrorView() {


//        if (networkErrorView != null) {
//            networkErrorView.setVisibility(View.GONE);
//        }
    }

    // pop

    /***
     * Called when request is served from cache and Same request was adde into
     * successful requests. Child class can overrride this function and can
     * decide whether cache response will discard ot not.
     *
     * @param request
     * @param response (cache reeponse)
     **/
    protected boolean shouldDiscardRepeatCachedResponse(Request<?> request,
                                                        Response<?> response) {
        hideLoader();
        return true;
    }

    /**
     * return true if request has the valid response, child call can overrride
     * this if their request does not have successful key to decide whether its
     * valid or fail response.
     *
     * @param request        request
     * @param responseObject (Actual data)
     * @param response       http response header.
     **/
    protected boolean isRequestSuccessful(Request<JSONObject> request,
                                          JSONObject responseObject, Response<JSONObject> response) {
        return !responseObject.has("successful")
                || responseObject.optBoolean("successful");
    }

    /**
     * Show force upgrade dialog if resp_code value is not latest and old
     *
     * @param responseHeader http request header.
     **/
    private boolean showUpGradeDialog(Map<String, String> responseHeader) {

        if (responseHeader != null) {
            String errorCode = responseHeader.get("resp_code");
            String errorReason = responseHeader.get("resp_user_message");
            if (!TextUtils.isEmpty(errorCode)
                    && !TextUtils.isEmpty(errorReason)) {
                if (!errorCode.equalsIgnoreCase("latest")
                        && !errorCode.equalsIgnoreCase("old")) {
                    // show force upgrade dialog
                }
            }
        }
        // }
        // }
        return false;
    }


    public void showLoadingDialog(final boolean dismissDialogOnBackPress) {
        // Check if Progress Dialog is NULL
//        if (progressDialog == null) {
//            // Instantiate Progress Dialog
//            progressDialog = DOLoaderDialog.newInstance(dismissDialogOnBackPress);
//        }
//
//        // Set Cancellable
//        progressDialog.setCancelable(dismissDialogOnBackPress);
//
//        // Attach Progress Dialog to Fragment Manager
//        if (!progressDialog.isVisible()) {
//            progressDialog.show(getFragmentManager(), "progress");
//        }
        //MasterDataFragment.showFragment(getActivity().getSupportFragmentManager(),progressDialog);
    }

    public void setFragmentTitle(String title) {
        if (mToolbar != null)
            mToolbar.setTitle(title);
    }

    public void hideLoadingDialog() {
        // Check if Progress Dialog is NULL
//        if (progressDialog != null && progressDialog.isVisible()) {
//            // Remove Progress Dialog
//            progressDialog.dismissAllowingStateLoss();
//        }
    }

    public void dismissDialogWithDelay(long ms) {
        if (getActivity() == null) {
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismissAllowingStateLoss();
            }
        }, ms);
    }

    public static interface OnFragmentDialogDismissListener {
        public void onDismiss(MasterDataFragment fragment);
    }
}
