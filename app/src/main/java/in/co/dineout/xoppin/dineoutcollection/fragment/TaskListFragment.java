package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.DineoutCollectApp;
import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.activity.RestaurantFormActivity;
import in.co.dineout.xoppin.dineoutcollection.adapter.TasksListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.helper.LoginHelper;
import in.co.dineout.xoppin.dineoutcollection.helper.ObjectMapperFactory;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTaskModel;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTasks;
import in.co.dineout.xoppin.dineoutcollection.model.Restaurant;

/**
 * Created by suraj on 06/02/16.
 */
public class TaskListFragment extends Fragment {
    private static final String TAG = TaskListFragment.class.getSimpleName();
    public static final String TAG2 = TaskListFragment.class.getCanonicalName();

    private static final String KEY_MODE = "KEY_MODE";
    private static final String KEY_DATA = "KEY_DATA";


    private ListView lvAssignedTasks;
    private TasksListAdapter tasksListAdapter;
    private ArrayList<AssignedTaskModel> assignedTaskModels;

    private enum Mode {
        FROM_LIST, FETCH_NEW
    }

    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MODE, Mode.FETCH_NEW);
        fragment.setArguments(bundle);
        return fragment;
    }


    public static TaskListFragment newInstance(ArrayList<AssignedTaskModel> assignedTaskModels) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_MODE, Mode.FROM_LIST);
        bundle.putSerializable(KEY_DATA, assignedTaskModels);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assignedTaskModels = new ArrayList<>(10);
        tasksListAdapter = new TasksListAdapter(getActivity(), assignedTaskModels);

        lvAssignedTasks = (ListView) view.findViewById(R.id.lv_tasks_list);
        lvAssignedTasks.setAdapter(tasksListAdapter);

        if (getArguments().getSerializable(KEY_MODE) == Mode.FROM_LIST) {
            (view.findViewById(R.id.inform_text)).setVisibility(View.GONE);
            (view.findViewById(R.id.lv_tasks_list)).setVisibility(View.VISIBLE);
            assignedTaskModels.addAll((ArrayList<AssignedTaskModel>) getArguments().getSerializable(KEY_DATA));
            tasksListAdapter.notifyDataSetChanged();
        } else {
            initiateRequestForAssignedTasks();
        }

        lvAssignedTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                initiateRequestForRestaurantDetail(assignedTaskModels.get(position).getR_id());
            }
        });

    }

    private void initiateRequestForAssignedTasks() {
        String TAG_REQUEST_ASSIGNED_TASK = "TAG_REQUEST_ASSIGNED_TASK";

        String url = "http://laravel.dineoutdeals.in/index.php/api/task-assigned";

        Response.Listener<JSONObject> respListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                //parse and update list
                AssignedTasks assignedTasks = null;
                try {
                    assignedTasks = ObjectMapperFactory.getObjectMapper()
                            .readValue(response.getJSONObject("data").toString(), AssignedTasks.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null != assignedTasks && assignedTasks.getTask_list() != null && assignedTasks.getTask_list().size() > 0) {
                    if (null != getView()) {
                        (getView().findViewById(R.id.inform_text)).setVisibility(View.GONE);
                        (getView().findViewById(R.id.lv_tasks_list)).setVisibility(View.VISIBLE);

                        assignedTaskModels.addAll(assignedTasks.getTask_list());
                        tasksListAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (null != getView()) {
                        (getView().findViewById(R.id.inform_text)).setVisibility(View.VISIBLE);
                        ((TextView) getView().findViewById(R.id.inform_text)).setText("No Assigned Tasks found");
                    }
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (null != getView()) {
                    (getView().findViewById(R.id.inform_text)).setVisibility(View.VISIBLE);
                    ((TextView) getView().findViewById(R.id.inform_text)).setText("Some Error occurred, Contact Tech Support");
                }
            }
        };


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, respListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = LoginHelper.getDefaultHeaders(getActivity());
                headers.putAll(super.getHeaders());
                return headers;
            }
        };

        DineoutCollectApp.getInstance().addToRequestQueue(jsonObjReq, TAG_REQUEST_ASSIGNED_TASK);
    }

    private void initiateRequestForRestaurantDetail(final int restaurantId) {
        String TAG_REQUEST_RESTAURANT_DETAIL = "TAG_REQUEST_RESTAURANT_DETAIL";

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading Restaurant..");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);

        String url = "http://laravel.dineoutdeals.in/index.php/api/resturant-details/" + restaurantId;

        Response.Listener<JSONObject> respListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (null != getActivity() && null != progressDialog) {
                    progressDialog.dismiss();
                }
                Log.d(TAG, response.toString());
                //parse and update list
                Restaurant restaurant = null;
                try {
                    restaurant = ObjectMapperFactory.getObjectMapper()
                            .readValue(response.getJSONObject("data").toString(), Restaurant.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (null != restaurant) {
                    if (null != getActivity()) {
                        if (DatabaseManager.getInstance().getRestaurantDetailsModelForRestaurantId(restaurant.getR_id()) != null) {
                            Intent i = new Intent(getActivity(), RestaurantFormActivity.class);
                            i.setAction(RestaurantFormActivity.ACTION_UPDATE_RESTAURANT_FROM_ASSIGNED);
                            i.putExtra(RestaurantFormActivity.KEY_RESTAURANT_DETAILS_DATA_ID, restaurant.getR_id());
                            startActivity(i);
                        } else {
                            Intent i = new Intent(getActivity(), RestaurantFormActivity.class);
                            i.setAction(RestaurantFormActivity.ACTION_UPDATE_ASSIGNED_RESTAURANT);
                            i.putExtra(RestaurantFormActivity.KEY_RESTAURANT_DATA, restaurant);
                            startActivity(i);
                        }
                    }
                } else {
                    Toast.makeText(getActivity(), "Error occured while fetching restaurant for ID "
                            + restaurantId + " contact tech support if problem persists", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                if (null != getView()) {
                    (getView().findViewById(R.id.inform_text)).setVisibility(View.VISIBLE);
                    ((TextView) getView().findViewById(R.id.inform_text)).setText("Some Error occurred, Contact Tech Support");
                }
            }
        };


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, respListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = LoginHelper.getDefaultHeaders(getActivity());
                headers.putAll(super.getHeaders());
                return headers;
            }
        };

        DineoutCollectApp.getInstance().addToRequestQueue(jsonObjReq, TAG_REQUEST_RESTAURANT_DETAIL);
        progressDialog.show();
    }

}
