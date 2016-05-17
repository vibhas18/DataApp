package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dineout.android.volley.Request;
import com.dineout.android.volley.Response;
import com.dineout.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.TasksListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DataDatabaseUtils;
import in.co.dineout.xoppin.dineoutcollection.model.ImageStatusModel;
import in.co.dineout.xoppin.dineoutcollection.model.dbmodels.RestaurantDetailsModel;

/**
 * Created by suraj on 06/02/16.
 * Modified by Prateek 12/05/16
 */
public class TaskListFragment extends MasterDataFragment implements Response.Listener<JSONObject>,Response.ErrorListener,TasksListAdapter.TaskDetailCallback
{
    private RecyclerView lvAssignedTasks;
    private TasksListAdapter tasksListAdapter;
    private final int ASSIGNED_TASK_REQUEST = 101;
    private final int DETAIL_REQUEST = 102;


    public static TaskListFragment newInstance() {
        TaskListFragment fragment = new TaskListFragment();
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

        lvAssignedTasks = (RecyclerView) view.findViewById(R.id.lv_tasks_list);
        lvAssignedTasks.setLayoutManager(new LinearLayoutManager(getContext()));

        tasksListAdapter = new TasksListAdapter(getActivity(),null,this);
        lvAssignedTasks.setAdapter(tasksListAdapter);
        initiateRequestForAssignedTasks();
    }

    private void initiateRequestForAssignedTasks() {

        showLoadingDialog(false);
        getNetworkManager().jsonRequestGet(ASSIGNED_TASK_REQUEST, "task-assigned",
                null, this, this, false);
    }


    private void initiateRequestForRestaurantDetail(final int restaurantId) {

        String url = "resturant-details/" + restaurantId;
        showLoadingDialog(false);
        getNetworkManager().jsonRequestGet(DETAIL_REQUEST, url,
                null, this, this, false);
    }

    @Override
    public void onErrorResponse(Request request, VolleyError error) {

        hideLoadingDialog();
    }

    @Override
    public void onResponse(Request<JSONObject> request, JSONObject responseObject, Response<JSONObject> response) {

        if(getActivity() == null)
            return;
        hideLoadingDialog();
        if(request.getIdentifier() == ASSIGNED_TASK_REQUEST && responseObject != null){

            if(responseObject.optInt("status") == 1){
                JSONArray array = responseObject.optJSONObject("data").optJSONArray("task_list");
                tasksListAdapter.updateAdapter(array);
            }

        }else if(request.getIdentifier() == DETAIL_REQUEST && responseObject != null){

            if(responseObject.optInt("status") == 1){
                JSONObject data = responseObject.optJSONObject("data");
                RestaurantDetailsModel model = new RestaurantDetailsModel(data.toString());
                model.setMode(DataDatabaseUtils.PENDING);
                saveImage(data);
                DataDatabaseUtils.getInstance(getContext()).
                        saveRestaurantForEditing(model.getRestaurantId()
                                , model.getRestaurantName(), model.getRestaurantJSONString());

                RestaurantFormFragment fragment = RestaurantFormFragment.newInstance(model);
                addToBackStack(getActivity(),fragment);
            }
        }
    }

    private void saveImage(JSONObject data){

        String id = data.optString("r_id");
        Type listType = new TypeToken<List<ImageStatusModel>>() {}.getType();
        List<ImageStatusModel> profileImages = new Gson().fromJson(data.optJSONArray("profile_image").toString(), listType);
        List<ImageStatusModel> menuImages = new Gson().fromJson(data.optJSONArray("menu_image").toString(), listType);

        DataDatabaseUtils.getInstance(getContext()).saveMenuImage(menuImages,id);
        DataDatabaseUtils.getInstance(getContext()).saveProfileImage(profileImages,id);

    }

    @Override
    public void showTaskDetail(int id) {

        initiateRequestForRestaurantDetail(id);
    }


}
