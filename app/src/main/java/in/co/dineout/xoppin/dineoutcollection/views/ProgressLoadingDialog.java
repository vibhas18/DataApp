package in.co.dineout.xoppin.dineoutcollection.views;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.MasterDataFragment;

/**
 * Created by prateek.aggarwal on 5/17/16.
 */
public class ProgressLoadingDialog extends MasterDataFragment {

    public static final String TAG = ProgressLoadingDialog.class.getSimpleName();

    private static final String BACK_PRESSED = "ON_BACK_PRESSED";
    private View mView;
    private boolean onBackPressedCancel = false;

    public static ProgressLoadingDialog newInstance(boolean onBackPressedCancel) {
        ProgressLoadingDialog fragment = new ProgressLoadingDialog();

        Bundle args = new Bundle();
        args.putBoolean(BACK_PRESSED, onBackPressedCancel);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            onBackPressedCancel = getArguments().getBoolean(BACK_PRESSED);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Hide Title on Dialog
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        // Set Background
        Drawable d = new ColorDrawable();
        d.setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        getDialog().getWindow().setBackgroundDrawable(d);

        // Set Cancel on Touch
        getDialog().setCanceledOnTouchOutside(false);

        // Inflate View
        mView = inflater.inflate(R.layout.view_screen_loader, container);

        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstance) {
        super.onActivityCreated(savedInstance);

        ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().
                setColorFilter(getActivity().getResources().getColor(R.color.colorPrimary),
                        android.graphics.PorterDuff.Mode.MULTIPLY);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);

        if (onBackPressedCancel)
            getActivity().onBackPressed();
    }
}