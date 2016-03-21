package in.co.dineout.xoppin.dineoutcollection.fragment.steps;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Date;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.RestaurantFormActivity;
import in.co.dineout.xoppin.dineoutcollection.model.OpenTimingDaysModel;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;
import in.co.dineout.xoppin.dineoutcollection.model.TimingModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

public class TimingsFragment extends BaseStepFragment {
    private static final String TAG = TimingsFragment.class.getSimpleName();
    public static final String TAG2 = TimingsFragment.class.getCanonicalName();

    private static final String HOURS = "HOURS";
    private static final String MINUTES = "MINUTES";
    private static final String DIALOG_ID = "DIALOG_ID";

    private static final int SUNDAY_OPEN = 0;
    private static final int SUNDAY_CLOSE = 1;
    private static final int MONDAY_OPEN = 2;
    private static final int MONDAY_CLOSE = 3;
    private static final int TUESDAY_OPEN = 4;
    private static final int TUESDAY_CLOSE = 5;
    private static final int WEDNESDAY_OPEN = 6;
    private static final int WEDNESDAY_CLOSE = 7;
    private static final int THURSDAY_OPEN = 8;
    private static final int THURSDAY_CLOSE = 9;
    private static final int FRIDAY_OPEN = 10;
    private static final int FRIDAY_CLOSE = 11;
    private static final int SATURDAY_OPEN = 12;
    private static final int SATURDAY_CLOSE = 13;

    private TextView tv_monday_open;
    private TextView tv_monday_close;
    private TextView tv_tuesday_open;
    private TextView tv_tuesday_close;
    private TextView tv_wednesday_open;
    private TextView tv_wednesday_close;
    private TextView tv_thursday_open;
    private TextView tv_thursday_close;
    private TextView tv_friday_open;
    private TextView tv_friday_close;
    private TextView tv_saturday_open;
    private TextView tv_saturday_close;
    private TextView tv_sunday_open;
    private TextView tv_sunday_close;

    private Switch sw_monday;
    private Switch sw_tuesday;
    private Switch sw_wed;
    private Switch sw_thu;
    private Switch sw_fri;
    private Switch sw_sat;
    private Switch sw_sun;


    public TimingsFragment() {
        // Required empty public constructor
    }

    public static TimingsFragment newInstance() {
        TimingsFragment fragment = new TimingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        populateViewFromData();
    }

    private void initViews(final View view) {
        tv_monday_open = (TextView) view.findViewById(R.id.tv_monday_open);
        tv_monday_close = (TextView) view.findViewById(R.id.tv_monday_close);
        tv_tuesday_open = (TextView) view.findViewById(R.id.tv_tuesday_open);
        tv_tuesday_close = (TextView) view.findViewById(R.id.tv_tuesday_close);
        tv_wednesday_open = (TextView) view.findViewById(R.id.tv_wednesday_open);
        tv_wednesday_close = (TextView) view.findViewById(R.id.tv_wednesday_close);
        tv_thursday_open = (TextView) view.findViewById(R.id.tv_thursday_open);
        tv_thursday_close = (TextView) view.findViewById(R.id.tv_thursday_close);
        tv_friday_open = (TextView) view.findViewById(R.id.tv_friday_open);
        tv_friday_close = (TextView) view.findViewById(R.id.tv_friday_close);
        tv_saturday_open = (TextView) view.findViewById(R.id.tv_saturday_open);
        tv_saturday_close = (TextView) view.findViewById(R.id.tv_saturday_close);
        tv_sunday_open = (TextView) view.findViewById(R.id.tv_sunday_open);
        tv_sunday_close = (TextView) view.findViewById(R.id.tv_sunday_close);

        sw_monday = (Switch) view.findViewById(R.id.sw_monday);
        sw_tuesday = (Switch) view.findViewById(R.id.sw_tuesday);
        sw_wed = (Switch) view.findViewById(R.id.sw_wed);
        sw_thu = (Switch) view.findViewById(R.id.sw_thu);
        sw_fri = (Switch) view.findViewById(R.id.sw_fri);
        sw_sat = (Switch) view.findViewById(R.id.sw_sat);
        sw_sun = (Switch) view.findViewById(R.id.sw_sun);

        tv_sunday_open.setOnClickListener(onClickListener);
        tv_sunday_close.setOnClickListener(onClickListener);
        tv_monday_open.setOnClickListener(onClickListener);
        tv_monday_close.setOnClickListener(onClickListener);
        tv_tuesday_open.setOnClickListener(onClickListener);
        tv_tuesday_close.setOnClickListener(onClickListener);
        tv_wednesday_open.setOnClickListener(onClickListener);
        tv_wednesday_close.setOnClickListener(onClickListener);
        tv_thursday_open.setOnClickListener(onClickListener);
        tv_thursday_close.setOnClickListener(onClickListener);
        tv_friday_open.setOnClickListener(onClickListener);
        tv_friday_close.setOnClickListener(onClickListener);
        tv_saturday_open.setOnClickListener(onClickListener);
        tv_saturday_close.setOnClickListener(onClickListener);

        sw_monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_monday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_monday).setVisibility(View.GONE);
                }
            }
        });
        sw_tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_tuesday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_tuesday).setVisibility(View.GONE);
                }
            }
        });
        sw_wed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_wednesday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_wednesday).setVisibility(View.GONE);
                }
            }
        });
        sw_thu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_thursday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_thursday).setVisibility(View.GONE);
                }
            }
        });
        sw_fri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_friday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_friday).setVisibility(View.GONE);
                }
            }
        });
        sw_sat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_saturday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_saturday).setVisibility(View.GONE);
                }
            }
        });
        sw_sun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    view.findViewById(R.id.ll_sunday).setVisibility(View.VISIBLE);
                } else {
                    view.findViewById(R.id.ll_sunday).setVisibility(View.GONE);
                }
            }
        });

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_monday_open:
                    showTimePickerDialog((TextView) v, MONDAY_OPEN);
                    break;
                case R.id.tv_monday_close:
                    showTimePickerDialog((TextView) v, MONDAY_CLOSE);
                    break;
                case R.id.tv_tuesday_open:
                    showTimePickerDialog((TextView) v, TUESDAY_OPEN);
                    break;
                case R.id.tv_tuesday_close:
                    showTimePickerDialog((TextView) v, TUESDAY_CLOSE);
                    break;
                case R.id.tv_wednesday_open:
                    showTimePickerDialog((TextView) v, WEDNESDAY_OPEN);
                    break;
                case R.id.tv_wednesday_close:
                    showTimePickerDialog((TextView) v, WEDNESDAY_CLOSE);
                    break;
                case R.id.tv_thursday_open:
                    showTimePickerDialog((TextView) v, THURSDAY_OPEN);
                    break;
                case R.id.tv_thursday_close:
                    showTimePickerDialog((TextView) v, THURSDAY_CLOSE);
                    break;
                case R.id.tv_friday_open:
                    showTimePickerDialog((TextView) v, FRIDAY_OPEN);
                    break;
                case R.id.tv_friday_close:
                    showTimePickerDialog((TextView) v, FRIDAY_CLOSE);
                    break;
                case R.id.tv_saturday_open:
                    showTimePickerDialog((TextView) v, SATURDAY_OPEN);
                    break;
                case R.id.tv_saturday_close:
                    showTimePickerDialog((TextView) v, SATURDAY_CLOSE);
                    break;
                case R.id.tv_sunday_open:
                    showTimePickerDialog((TextView) v, SUNDAY_OPEN);
                    break;
                case R.id.tv_sunday_close:
                    showTimePickerDialog((TextView) v, SUNDAY_CLOSE);
                    break;
            }
        }
    };

    @Override
    public void onStepChanged() {

    }

    @Override
    public void saveDataForStep() {
        Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();
        if (restaurant.getOpen_timing() == null) {
            restaurant.setOpen_timing(new OpenTimingDaysModel());
        }

        if (sw_monday.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_sunday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_sunday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_sunday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_sunday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setSunday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setSunday(timingModel);
        }

        if (sw_tuesday.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_monday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_monday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_monday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_monday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setMonday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setMonday(timingModel);
        }

        if (sw_wed.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_tuesday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_tuesday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_tuesday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_tuesday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setTuesday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setTuesday(timingModel);
        }

        if (sw_thu.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_wednesday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_wednesday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_wednesday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_wednesday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setWednesday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setWednesday(timingModel);
        }

        if (sw_fri.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_thursday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_thursday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_thursday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_thursday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setThursday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setThursday(timingModel);
        }

        if (sw_sat.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_friday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_friday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_friday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_friday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setFriday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setFriday(timingModel);
        }

        if (sw_sun.isChecked()) {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("open");
            if (!TextUtils.isEmpty(tv_saturday_open.getText().toString().trim())) {
                timingModel.setSt_time(tv_saturday_open.getText().toString().trim());
            } else {
                timingModel.setSt_time("00:00:00");
            }
            if (!TextUtils.isEmpty(tv_saturday_close.getText().toString().trim())) {
                timingModel.setEn_time(tv_saturday_close.getText().toString().trim());
            } else {
                timingModel.setEn_time("00:00:00");
            }
            restaurant.getOpen_timing().setSaturday(timingModel);
        } else {
            TimingModel timingModel = new TimingModel();
            timingModel.setState("closed");
            restaurant.getOpen_timing().setSaturday(timingModel);
        }
        ((RestaurantFormActivity)getActivity()).saveRestaurantModel();
    }

    @Override
    public void populateViewFromData() {
        Restaurant restaurant = ((RestaurantFormActivity) getActivity()).getRestaurant();
        if(null == restaurant.getOpen_timing()) {
            Log.wtf(TAG, "null openTiming");
            return;
        }

        if (restaurant.getOpen_timing().getSunday().getState().equalsIgnoreCase("open")) {
            sw_sun.setChecked(true);
            tv_sunday_open.setText(restaurant.getOpen_timing().getSunday().getSt_time());
            tv_sunday_close.setText(restaurant.getOpen_timing().getSunday().getEn_time());
        } else {
            sw_sun.setChecked(false);
        }

        if (restaurant.getOpen_timing().getMonday().getState().equalsIgnoreCase("open")) {
            sw_monday.setChecked(true);
            tv_monday_open.setText(restaurant.getOpen_timing().getMonday().getSt_time());
            tv_monday_close.setText(restaurant.getOpen_timing().getMonday().getEn_time());
        } else {
            sw_monday.setChecked(false);
        }

        if (restaurant.getOpen_timing().getTuesday().getState().equalsIgnoreCase("open")) {
            sw_tuesday.setChecked(true);
            tv_tuesday_open.setText(restaurant.getOpen_timing().getTuesday().getSt_time());
            tv_tuesday_close.setText(restaurant.getOpen_timing().getTuesday().getEn_time());
        } else {
            sw_tuesday.setChecked(false);
        }

        if (restaurant.getOpen_timing().getWednesday().getState().equalsIgnoreCase("open")) {
            sw_wed.setChecked(true);
            tv_wednesday_open.setText(restaurant.getOpen_timing().getWednesday().getSt_time());
            tv_wednesday_close.setText(restaurant.getOpen_timing().getWednesday().getEn_time());
        } else {
            sw_wed.setChecked(false);
        }

        if (restaurant.getOpen_timing().getThursday().getState().equalsIgnoreCase("open")) {
            sw_thu.setChecked(true);
            tv_thursday_open.setText(restaurant.getOpen_timing().getThursday().getSt_time());
            tv_thursday_close.setText(restaurant.getOpen_timing().getThursday().getEn_time());
        } else {
            sw_thu.setChecked(false);
        }

        if (restaurant.getOpen_timing().getFriday().getState().equalsIgnoreCase("open")) {
            sw_fri.setChecked(true);
            tv_friday_open.setText(restaurant.getOpen_timing().getFriday().getSt_time());
            tv_friday_close.setText(restaurant.getOpen_timing().getFriday().getEn_time());
        } else {
            sw_fri.setChecked(false);
        }

        if (restaurant.getOpen_timing().getSaturday().getState().equalsIgnoreCase("open")) {
            sw_sat.setChecked(true);
            tv_saturday_open.setText(restaurant.getOpen_timing().getSaturday().getSt_time());
            tv_saturday_close.setText(restaurant.getOpen_timing().getSaturday().getEn_time());
        } else {
            sw_sat.setChecked(false);
        }
    }

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        public static TimePickerFragment newInstance(int dialogid, int hours,
                                                     int minutes) {
            TimePickerFragment frag = new TimePickerFragment();
            Bundle args = new Bundle();
            args.putInt(HOURS, hours);
            args.putInt(MINUTES, minutes);
            args.putInt(DIALOG_ID, dialogid);
            frag.setArguments(args);
            return frag;
        }

        @Override
        public Dialog onCreateDialog(Bundle bundle) {
            return new TimePickerDialog(getActivity(), this, getArguments().getInt(HOURS), getArguments().getInt(MINUTES), false);
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            if (getParentFragment() instanceof TimingsFragment) {
                ((TimingsFragment) getParentFragment()).setTime(getArguments().getInt(DIALOG_ID), hourOfDay, minute);
            }
        }

    }

    private void showTimePickerDialog(TextView textView, int dialogId) {
        String dateString = !TextUtils.isEmpty(textView.getText().toString()) ? textView.getText().toString() : "00:00:00";
        Date date = Utils.getFormattedDate(dateString);
        DialogFragment timePickerFragment = new TimePickerFragment().newInstance(dialogId, date.getHours(), date.getMinutes());
        timePickerFragment.show(getChildFragmentManager(), "Select Time");
    }

    private void setTime(int receptioDialogId, int hourOfDay, int minute) {
        Log.i("test", "test");
        switch (receptioDialogId) {
            case SUNDAY_OPEN:
                tv_sunday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case SUNDAY_CLOSE:
                tv_sunday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case MONDAY_OPEN:
                tv_monday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case MONDAY_CLOSE:
                tv_monday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case TUESDAY_OPEN:
                tv_tuesday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case TUESDAY_CLOSE:
                tv_tuesday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case WEDNESDAY_OPEN:
                tv_wednesday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case WEDNESDAY_CLOSE:
                tv_wednesday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case THURSDAY_OPEN:
                tv_thursday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case THURSDAY_CLOSE:
                tv_thursday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case FRIDAY_OPEN:
                tv_friday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case FRIDAY_CLOSE:
                tv_friday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case SATURDAY_OPEN:
                tv_saturday_open.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            case SATURDAY_CLOSE:
                tv_saturday_close.setText(new StringBuilder().append(Utils.pad(hourOfDay)).append(":").append(Utils.pad(minute)).append(":00"));
                break;
            default:
                break;
        }

    }
}
