package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.database.AddTimingCallback;
import in.co.dineout.xoppin.dineoutcollection.model.TimingModel;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by prateek.aggarwal on 7/19/16.
 */
public class AddTimingDialog extends MasterDataFragment implements View.OnClickListener,
        RadioGroup.OnCheckedChangeListener,CompoundButton.OnCheckedChangeListener,AdapterView.OnItemSelectedListener{


    private Spinner mDaysDropDown;
    private LinearLayout mSlotContainer;
    private TimingModel timingModel;
    private String selctedDayLabel = "";
    private View mAddSlot,mDelete,mRepeatCheckbox,mDaysGroup;
    private List<String> mSelectedDay = new ArrayList<>();
    private AddTimingCallback mCallback;


    public static AddTimingDialog newInstance(TimingModel model, String day, AddTimingCallback callback){
        AddTimingDialog dialog = new AddTimingDialog();
        dialog.timingModel = model;
        dialog.selctedDayLabel = day;
        dialog.mCallback = callback;
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.timing_dialog,container,false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {

            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDaysDropDown = (Spinner)view.findViewById(R.id.day_spinner);
        mRepeatCheckbox = view.findViewById(R.id.repeat_check);
        mAddSlot = view.findViewById(R.id.day_slots);
        mDaysGroup = view.findViewById(R.id.day_radio_grp);
        mSlotContainer = (LinearLayout)view.findViewById(R.id.slot_container);
        mDelete = view.findViewById(R.id.delete_timing);
        ((CheckBox)mRepeatCheckbox).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_mon)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_sun)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_sat)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_tues)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_wed)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_thursday)).setOnCheckedChangeListener(this);
        ((CheckBox)view.findViewById(R.id.radio_friday)).setOnCheckedChangeListener(this);


        mAddSlot.setOnClickListener(this);
        mDaysDropDown.setOnItemSelectedListener(this);
        mDelete.setOnClickListener(this);
        view.findViewById(R.id.done_adding).setOnClickListener(this);


        if(timingModel != null && !TextUtils.isEmpty(selctedDayLabel)){

            initializeView();
        }else{
            addDaySlots("00:00:00","00:00:00");
        }

    }

    private void initializeView(){
        mDaysDropDown.setSelection(getDayPosition(selctedDayLabel));
        mDaysDropDown.setPrompt(selctedDayLabel);

        JSONArray timingArray = timingModel.getTimings();
        String[] start_time = timingModel.getSt_time();
        String[] end_time = timingModel.getEn_time();

        for(int i=0 ;i<timingArray.length();i++){

            JSONObject values = timingArray.optJSONObject(i);
            addDaySlots(values.optString("st_time","00:00:00"),
                    values.optString("en_time","00:00:00"));
        }
    }

    private int getDayPosition(String selctedDayLabel) {

        String[] array = getResources().getStringArray(R.array.day_array);
        for(int i=1;i<array.length;i++){

            if(selctedDayLabel.equalsIgnoreCase(array[i]))
                return i;
        }
        return 0;
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.delete_timing:
                deleteTiming();
                break;
            case R.id.done_adding:
                doneAndUpdate();
                break;
            case R.id.day_slots:
                addDaySlots("","");
                break;

        }

    }

    private void addDaySlots(String start,String end){

        mSlotContainer.addView(getSlotView(start,end));
        ((Button)mAddSlot).setText("Add slot "+(mSlotContainer.getChildCount()+1));

    }

    private View getSlotView(String opemTiming,String closetiming){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.slot_view,null);
        TextView mOpen = (TextView) v.findViewById(R.id.open_timing);

        TextView mClose = (TextView)v.findViewById(R.id.close_timing);

        mOpen.setOnClickListener(new TiminSelectClickListener(mOpen));
        mClose.setOnClickListener(new TiminSelectClickListener(mClose));

        if(!TextUtils.isEmpty(opemTiming))
            mOpen.setText(opemTiming);

        if(!TextUtils.isEmpty(closetiming))
            mClose.setText(closetiming);

        return v;
    }

    private void doneAndUpdate(){

        TimingModel model = new TimingModel();
        model.setState("open");

        List<TimingModel.Slots> timing = new ArrayList<>();
        String[] openArray = new String[mSlotContainer.getChildCount()];
        String[] closeArray = new String[mSlotContainer.getChildCount()];
        for(int i=0;i<mSlotContainer.getChildCount();i++){

            View v = mSlotContainer.getChildAt(i);
            TextView open = (TextView) v.findViewById(R.id.open_timing);
            TextView close = (TextView)v.findViewById(R.id.close_timing);
            try{



                String openTiming = open.getText().toString();
                String closeTiming = close.getText().toString();

                if(!TextUtils.isEmpty(openTiming) && !TextUtils.isEmpty(closeTiming)){
                    TimingModel.Slots slot = new TimingModel().new Slots();
                    slot.setSt_time(open.getText().toString());
                    slot.setEn_time(close.getText().toString());
                    timing.add(slot);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        model.setTimings(timing);

        HashMap<String,TimingModel> slotMap = new LinkedHashMap<>();
        for(String day : mSelectedDay){
            slotMap.put(day,model);
        }


        dismiss();

        if(mCallback != null)
            mCallback.addSlots(slotMap);
    }

    private void deleteTiming(){

        if(mCallback != null)
            mCallback.deleteTiming(selctedDayLabel);
        dismiss();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


        String day = null;
            switch (buttonView.getId()){

                case R.id.repeat_check:{
                   resetAllCheckBox();
                    mDaysGroup.setVisibility(isChecked?View.VISIBLE:View.GONE);
                    ((CheckBox) ((LinearLayout)mDaysGroup).
                            getChildAt(mDaysDropDown.getSelectedItemPosition()-1)).setChecked(true);

                }
                    break;
                case R.id.radio_mon:
                    day = "Monday";
                    break;
                case R.id.radio_sat:
                    day = "Saturday";
                   break;
                case R.id.radio_sun:
                    day = "Sunday";
                    break;
                case R.id.radio_tues:
                    day = "Tuesday";
                    break;
                case R.id.radio_wed:
                    day = "Wednesday";
                    break;
                case R.id.radio_thursday:
                    day = "Thursday";
                    break;
                case R.id.radio_friday:
                    day = "Friday";
                    break;
            }

        if(!TextUtils.isEmpty(day)){
            if(isChecked && !mSelectedDay.contains(day)){
                mSelectedDay.add(day);
            }else if(!isChecked && mSelectedDay.contains(day)){
                mSelectedDay.remove(day);
            }
        }
    }

    private void resetAllCheckBox(){


        for(int i=0;i<((LinearLayout)mDaysGroup).getChildCount();i++){

            ((CheckBox) ((LinearLayout)mDaysGroup).getChildAt(i)).setChecked(false);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if(position != 0){
            selctedDayLabel = parent.getItemAtPosition(position).toString();
            mAddSlot.setEnabled(true);
            mDelete.setEnabled(true);
            mRepeatCheckbox.setEnabled(true);
            resetAllCheckBox();
            ((CheckBox) ((LinearLayout)mDaysGroup).getChildAt(position-1)).setChecked(true);
        }else{
            selctedDayLabel = "";
            resetAllCheckBox();
            mAddSlot.setEnabled(false);
            mDelete.setEnabled(false);
            mRepeatCheckbox.setEnabled(false);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class TiminSelectClickListener implements View.OnClickListener,TimePickerDialog.OnTimeSetListener{

        private TextView view;

        public TiminSelectClickListener(TextView v){

            this.view = v;
        }

        @Override
        public void onClick(View v) {

            if(!TextUtils.isEmpty(selctedDayLabel))
            showPicker(view);
        }

        private void showPicker(TextView v){

            String dateString = !TextUtils.isEmpty(v.getText().toString()) ? v.getText().toString() : "00:00:00";
            Date date = Utils.getFormattedDate(dateString);

            AlertDialog dialog = new TimePickerDialog(getActivity(),this,date.getHours(),date.getMinutes(),false);

            dialog.show();
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            this.view.setText(hourOfDay+":"+minute+":00");
        }
    }
}
