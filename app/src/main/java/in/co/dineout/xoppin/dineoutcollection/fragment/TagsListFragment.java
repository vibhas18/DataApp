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
import in.co.dineout.xoppin.dineoutcollection.model.TagModel;


/**
 * Created by suraj on 23/02/16.
 */
public class TagsListFragment extends BaseActionModeFragment {
    private static final String TAG = TagsListFragment.class.getSimpleName();
    public static final String TAG2 = TagsListFragment.class.getCanonicalName();

    private static final String KEY_TAGS_LIST = "KEY_TAGS_LIST";
    private ArrayList<TagModel> tagModels;

    private Callbacks callbacks;

    public static TagsListFragment newInstance(final ArrayList<TagModel> tagModels) {
        TagsListFragment fragment = new TagsListFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_TAGS_LIST, tagModels);
        args.putString(KEY_TITLE, "Select Tags");
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

        tagModels = (ArrayList<TagModel>) getArguments().getSerializable(KEY_TAGS_LIST);

        ListView listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(new SelectAdapter(getActivity(), StaticDataHandler.getInstance().getStaticDataModel().getTags()));

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
                    callbacks.onTagSelected(tagModels);
                    getActionMode().finish();
                }
            }
        });

    }

    public void setCallbacks(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    public interface Callbacks {
        void onTagSelected(ArrayList<TagModel> tagModels);
    }

    private class SelectAdapter extends ArrayAdapter<TagModel> {
        public SelectAdapter(Context context, List<TagModel> objects) {
            super(context, R.layout.row_checkable_text, objects);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_checkable_text, parent, false);
            }

            CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            checkbox.setText(getItem(position).getTag_name());

            if (tagModels != null && tagModels.size() > 0) {
                if (tagModels.contains(getItem(position))) {
                    checkbox.setChecked(true);
                } else {
                    checkbox.setChecked(false);
                }
            }

            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tagModels.contains(getItem(position))) {
                        tagModels.remove(getItem(position));
                    } else {
                        tagModels.add(getItem(position));
                    }
                }
            });

            return convertView;
        }
    }

}
