package in.co.dineout.xoppin.dineoutcollection.views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.GenericListSingleSelectFragment;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.GenericModel;
import in.co.dineout.xoppin.dineoutcollection.model.RestContactModel;

/**
 * Created by Suraj on 22-02-2016.
 */
public class GcrrContactViewHelper implements View.OnClickListener {

    public static View getGcrrContactView(final Context context, RestContactModel restContactModel) {
        final View view = LayoutInflater.from(context).inflate(R.layout.view_gcrr_contact, null, false);
        if (restContactModel != null) {
            populateGcrrView(view, restContactModel);
        }

        ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
        iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != context) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setTitle("Delete Contact");
                    alertDialogBuilder.setMessage("Are you sure. This action cannot be reverted");
                    alertDialogBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            view.setVisibility(View.GONE);
                        }
                    });
                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alertDialogBuilder.create().show();
                }
            }
        });

        ((TextView) view.findViewById(R.id.tv_gcrr_type)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                GenericListSingleSelectFragment fragment = GenericListSingleSelectFragment.newInstance((ArrayList) StaticDataHandler.getInstance().getStaticDataModel().getCity(), "Select City");
                fragment.setCallbacks(new GenericListSingleSelectFragment.Callbacks() {
                    @Override
                    public void onItemClicked(GenericModel object) {

                        if (null != object) {
                            CityModel cityModel = (CityModel) object;
                            ((TextView)v).setText(cityModel.getName());
                        } else {
                            Toast.makeText(context, "City Not Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment, GenericListSingleSelectFragment.TAG2)
                        .addToBackStack(GenericListSingleSelectFragment.TAG2)
                        .commitAllowingStateLoss();
            }
        });

        return view;
    }

    private static void populateGcrrView(View view, RestContactModel restContactModel) {
        if (!TextUtils.isEmpty(restContactModel.getFirst_name())) {
            ((EditText) view.findViewById(R.id.et_first_name)).setText(restContactModel.getFirst_name());
        }

        if (!TextUtils.isEmpty(restContactModel.getLast_name())) {
            ((EditText) view.findViewById(R.id.et_last_name)).setText(restContactModel.getLast_name());
        }

        if (!TextUtils.isEmpty(restContactModel.getPhone_no())) {
            ((EditText) view.findViewById(R.id.et_phone)).setText(restContactModel.getPhone_no());
        }

        if (!TextUtils.isEmpty(restContactModel.getAu_email())) {
            ((EditText) view.findViewById(R.id.et_email)).setText(restContactModel.getAu_email());
        }

        if (!TextUtils.isEmpty(restContactModel.getGcrr_type())) {
            ((TextView) view.findViewById(R.id.tv_gcrr_type)).setText(restContactModel.getGcrr_type());
        }
    }

    public static RestContactModel getRestContactFromView(View view) {
        if(view.getVisibility() == View.GONE) {
            return null;
        }

        String firstName = ((EditText) view.findViewById(R.id.et_first_name)).getText().toString();
        String lastName = ((EditText) view.findViewById(R.id.et_last_name)).getText().toString();
        String email = ((EditText) view.findViewById(R.id.et_email)).getText().toString();
        String phone = ((EditText) view.findViewById(R.id.et_phone)).getText().toString();
        String gcrrType = ((TextView) view.findViewById(R.id.tv_gcrr_type)).getText().toString();

        if (TextUtils.isEmpty(firstName)
                && TextUtils.isEmpty(lastName)
                && TextUtils.isEmpty(email)
                && TextUtils.isEmpty(phone)) {
            return null;
        }

        RestContactModel restContactModel = new RestContactModel();
        restContactModel.setFirst_name(firstName);
        restContactModel.setLast_name(lastName);
        restContactModel.setAu_email(email);
        restContactModel.setPhone_no(phone);
        restContactModel.setGcrr_type(gcrrType);

        return restContactModel;
    }


    @Override
    public void onClick(final View v) {

    }
}
