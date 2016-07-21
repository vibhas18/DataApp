package in.co.dineout.xoppin.dineoutcollection.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import in.co.dineout.xoppin.dineoutcollection.R;

/**
 * Created by prateek.aggarwal on 7/20/16.
 */
public class SearchView extends LinearLayout implements AdapterView.OnItemSelectedListener,
       View.OnClickListener {
    private SearchCallback mCallback;
    private EditText mSearch;
    private String mSearchType;
    private RadioGroup mPriorityGroup;
    private Spinner mTypeDrown;

    public SearchView(Context context) {
        super(context,null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View v = LayoutInflater.from(getContext()).inflate(R.layout.search_view,this);
        mTypeDrown = (Spinner)v.findViewById(R.id.type_dropdown);
        mTypeDrown.setOnItemSelectedListener(this);
        mPriorityGroup = (RadioGroup)v.findViewById(R.id.priority_group);
        mSearch  = (EditText)v.findViewById(R.id.search_value);

        v.findViewById(R.id.search_task).setOnClickListener(this);
        v.findViewById(R.id.clear_task).setOnClickListener(this);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr,0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);




    }

    public void setSearchCallback(SearchCallback callback){
        this.mCallback = callback;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.search_task){
            search();
        }else if(v.getId() == R.id.clear_task){

            mSearch.setText("");
            mPriorityGroup.clearCheck();

            mCallback.clearSearch();
        }
    }

    private void search(){

        String query = mSearch.getText().toString().trim();
        String priority = mPriorityGroup.getCheckedRadioButtonId() == R.id.normal_radio ? "normal"
                :mPriorityGroup.getCheckedRadioButtonId() == R.id.high_radio ? "high":"";
        int type = mTypeDrown.getSelectedItem().toString().equalsIgnoreCase("name") ?1 :2;
        if(type == 1){
            mCallback.searchByNameAndPriority(query,priority);
        }else{
            mCallback.searchByLocalityAndPriority(query,priority);

        }
    }


    public interface SearchCallback{

        public void searchByLocalityAndPriority(String query,String priority);
        public void searchByNameAndPriority(String query,String priority);
//        public void searchByPriority(String priority);
        public void clearSearch();

    }
}
