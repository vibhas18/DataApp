package in.co.dineout.xoppin.dineoutcollection.fragment.steps;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.RestaurantFormFragment;
import in.co.dineout.xoppin.dineoutcollection.model.RestContactModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;
import in.co.dineout.xoppin.dineoutcollection.views.GcrrContactViewHelper;

public class ContactFragment extends BaseStepFragment implements View.OnFocusChangeListener,TextWatcher {

    private EditText et_restaurant_website;
    private EditText et_restaurant_fb;
    private EditText et_phone;
    private EditText et_mobile;

    private TextView tv_add_gcrr_contacts;

    private LinearLayout ll_gcrr_contact;

    private ArrayList<View> contactViews;

    public ContactFragment() {
    }

    public static ContactFragment create(String key) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_KEY,key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void initView(View view) {
        et_restaurant_website = (EditText) view.findViewById(R.id.et_restaurant_website);
        et_restaurant_fb = (EditText) view.findViewById(R.id.et_restaurant_fb);
        et_phone = (EditText) view.findViewById(R.id.et_phone);
        et_mobile = (EditText) view.findViewById(R.id.et_mobile);


        ll_gcrr_contact = (LinearLayout) view.findViewById(R.id.ll_gcrr_contact);

        tv_add_gcrr_contacts = (TextView) view.findViewById(R.id.tv_add_gcrr_contacts);

        contactViews = new ArrayList<View>(5);
        populateViewFromData();
        et_phone.setOnFocusChangeListener(this);
        et_mobile.setOnFocusChangeListener(this);
        tv_add_gcrr_contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View gcrrContactView = GcrrContactViewHelper.getGcrrContactView(getActivity(), null);
                contactViews.add(gcrrContactView);
                ll_gcrr_contact.addView(gcrrContactView);
//                notifyChanges();
            }
        });
    }

    @Override
    public void onStepChanged() {

    }

    @Override
    public void saveDataForStep() {

        if(getActivity() == null)
            return;
        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();

        if (!TextUtils.isEmpty(et_restaurant_website.getText().toString().trim())) {
            restaurant.setWebsite(et_restaurant_website.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(et_restaurant_fb.getText().toString().trim())) {
            restaurant.setFb_page_url(et_restaurant_fb.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
            restaurant.setMobile_number(et_mobile.getText().toString().trim());
        }

        if (!TextUtils.isEmpty(et_phone.getText().toString().trim())) {
            restaurant.setPhone(et_phone.getText().toString().trim());
        }

        if (null != contactViews && contactViews.size() > 0) {
            ArrayList<RestContactModel> contactModels = new ArrayList<>(5);
            for (View cView : contactViews) {
                contactModels.add(GcrrContactViewHelper.getRestContactFromView(cView));
            }
            restaurant.setContacts(contactModels);
        }
//        ((RestaurantFormActivity)getActivity()).saveRestaurantModel();
    }

    @Override
    public void populateViewFromData() {
        RestaurantDetailsModel restaurant = ((RestaurantFormFragment)getParentFragment()).getRestaurantDetailsModel();
        et_restaurant_website.setText(restaurant.getWebsite());
        et_restaurant_fb.setText(restaurant.getFb_page_url());
        et_mobile.setText(restaurant.getMobile_number());
        et_phone.setText(restaurant.getPhone());

        if (null != restaurant.getContacts()) {
            for (RestContactModel restContactModel : restaurant.getContacts()) {
                View gcrrContactView = GcrrContactViewHelper.getGcrrContactView(getActivity(), restContactModel);
                contactViews.add(gcrrContactView);
                ll_gcrr_contact.addView(gcrrContactView);
            }
        }

//        notifyChanges();
    }

    @Override
    public boolean isDataValid() {

        if(getActivity() == null || getView() == null)
            return false;
        if (TextUtils.isEmpty(et_mobile.getText().toString().trim())) {
            return false;
        } else if (TextUtils.isEmpty(et_phone.getText().toString().trim())) {
                return false;
        }

        saveDataForStep();
        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        notifyChanges();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

            notifyChanges();

    }
}
