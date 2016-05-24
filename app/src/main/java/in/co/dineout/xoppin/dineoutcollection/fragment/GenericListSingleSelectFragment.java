package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.model.CityModel;
import in.co.dineout.xoppin.dineoutcollection.model.GenericModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.AreaModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.LocalityModel;

/**
 * Created by suraj on 19/02/16.
 */
public class GenericListSingleSelectFragment extends BaseActionModeFragment {
    private static final String TAG = GenericListSingleSelectFragment.class.getSimpleName();
    public static final String TAG2 = GenericListSingleSelectFragment.class.getCanonicalName();

    private static final String KEY_DATA_LIST = "KEY_DATA_LIST";
    private Callbacks callbacks;

    public static GenericListSingleSelectFragment newInstance(ArrayList<GenericModel> objects, String title) {
        GenericListSingleSelectFragment fragment = new GenericListSingleSelectFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_DATA_LIST, objects);
        args.putString(KEY_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_generic_list_single_select, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ArrayList<GenericModel> objectArrayList = (ArrayList<GenericModel>) getArguments().getSerializable(KEY_DATA_LIST);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        if (objectArrayList != null) {
            MyAdapter adapter = new MyAdapter(getActivity(), objectArrayList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (null != callbacks) {
                        callbacks.onItemClicked(objectArrayList.get(position));
                        getActionMode().finish();
                    }
                }
            });


        }else{
            Toast.makeText(getActivity(),"No Area available for this city",Toast.LENGTH_SHORT).show();
        }

    }

    private static class MyAdapter extends ArrayAdapter<GenericModel> {
        public MyAdapter(Context context, ArrayList<GenericModel> objects) {
            super(context, R.layout.row_generic_list_sigle_select, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_generic_list_sigle_select, parent, false);
            }
            TextView textView = (TextView) convertView.findViewById(R.id.textView);

            if (getItem(position) instanceof AreaModel) {
                AreaModel areaModel = (AreaModel) getItem(position);
                textView.setText(areaModel.getName());
            }

            if (getItem(position) instanceof LocalityModel) {
                LocalityModel localityModel = (LocalityModel) getItem(position);
                textView.setText(localityModel.getName());
            }

            if (getItem(position) instanceof CityModel) {
                CityModel cityModel = (CityModel) getItem(position);
                textView.setText(cityModel.getName());
            }

            return convertView;
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public static interface Callbacks {
        void onItemClicked(GenericModel object);
    }

}
