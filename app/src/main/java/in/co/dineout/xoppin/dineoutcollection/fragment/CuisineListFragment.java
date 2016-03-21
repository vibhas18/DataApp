package in.co.dineout.xoppin.dineoutcollection.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.handler.StaticDataHandler;
import in.co.dineout.xoppin.dineoutcollection.model.CuisineModel;


public class CuisineListFragment extends BaseActionModeFragment {
    private static final String TAG = CuisineListFragment.class.getSimpleName();
    public static final String TAG2 = CuisineListFragment.class.getCanonicalName();

    public enum Mode {
        PRIMARY, SECONDARY
    }

    private static final String KEY_CUISINES_LIST = "KEY_CUISINES_LIST";
    private ArrayList<CuisineModel> selectedCuisines;

    private Callbacks callbacks;

    public static CuisineListFragment newInstance(final ArrayList<CuisineModel> selectedCuisines, Mode mode) {
        CuisineListFragment fragment = new CuisineListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_CUISINES_LIST, selectedCuisines);
        if (mode == Mode.PRIMARY) {
            args.putString(KEY_TITLE, "Select Primary Cuisines");
        } else {
            args.putString(KEY_TITLE, "Select Secondary Cuisines");
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuisine_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedCuisines = (ArrayList<CuisineModel>) getArguments().getSerializable(KEY_CUISINES_LIST);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new SelectAdapter(getActivity(), StaticDataHandler.getInstance().getStaticDataModel().getCuisine()));

        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActionMode().finish();
            }
        });

        view.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != callbacks) {
                    callbacks.onCuisineSelected(selectedCuisines);
                    getActionMode().finish();
                }
            }
        });

    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public interface Callbacks {
        void onCuisineSelected(ArrayList<CuisineModel> cuisines);
    }

    private class SelectAdapter extends ArrayAdapter<CuisineModel> {
        public SelectAdapter(Context context, List<CuisineModel> objects) {
            super(context, R.layout.row_checkable_text, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_checkable_text, parent, false);
            }

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setText(getItem(position).getCuisine_name());

            if (selectedCuisines != null && selectedCuisines.size() > 0) {
                if (selectedCuisines.contains(getItem(position))) {
                    checkbox.setChecked(true);
                } else {
                    checkbox.setChecked(false);
                }
            }

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (selectedCuisines.contains(getItem(position))) {
                        selectedCuisines.remove(getItem(position));
                    } else {
                        selectedCuisines.add(getItem(position));
                    }
                }
            });

            return convertView;
        }
    }

}
