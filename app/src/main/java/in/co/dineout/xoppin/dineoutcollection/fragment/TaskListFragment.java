package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private int mSelectedTask ;


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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setFragmentTitle("Assigned Restaurants");
    }

    private void initiateRequestForAssignedTasks() {

        showLoadingDialog(false);
        getNetworkManager().jsonRequestGet(ASSIGNED_TASK_REQUEST, "task-assigned",
                null, this, this, true);
    }


    private void initiateRequestForRestaurantDetail(final int restaurantId) {

        String url = "resturant-details/" + restaurantId;
        showLoadingDialog(false);

        getNetworkManager().jsonRequestGet(DETAIL_REQUEST, url,
                null, this, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(Request request, VolleyError error) {
                        hideLoadingDialog();
                        RestaurantDetailsModel local = DataDatabaseUtils.getInstance(getContext()).getRestaurantModelForId(mSelectedTask+"");
                        if(local == null){
                            Toast.makeText(getContext(),"Check your network connection",Toast.LENGTH_SHORT).show();
                            return;
                        }else if(local != null && local.getMode() == DataDatabaseUtils.SYNCED){
                            Toast.makeText(getContext(),"Restaurant already synced",Toast.LENGTH_SHORT).show();

                            return;
                        }
                        RestaurantFormFragment fragment = RestaurantFormFragment.newInstance(local);
                        addToBackStack(getActivity(),fragment);
                    }
                }, true);
    }

    @Override
    public void onErrorResponse(Request request, VolleyError error) {

        hideLoadingDialog();
    }

    private JSONArray removeSubmittedEntries(JSONArray src){

        JSONArray target = new JSONArray();

        for(int i=0;i<src.length();i++){

            JSONObject task = src.optJSONObject(i);
            if(task != null){

                String id = task.optString("task_deail_id");
                RestaurantDetailsModel model = DataDatabaseUtils.getInstance(getActivity()).getRestaurantModelForId(id);
                int mode =DataDatabaseUtils.PENDING;
                if(model != null)
                    mode = model.getMode();

                if(mode == DataDatabaseUtils.PENDING){
                    target.put(task);
                }
            }
        }
        return target;
    }

    @Override
    public void onResponse(Request<JSONObject> request, JSONObject responseObject, Response<JSONObject> response) {

        if(getActivity() == null)
            return;
        hideLoadingDialog();
        if(request.getIdentifier() == ASSIGNED_TASK_REQUEST && responseObject != null){



            if(responseObject.optInt("status") == 1){
                JSONArray array = responseObject.optJSONObject("data").optJSONArray("task_list");
                if(response.isCachedResponse())
                    array = removeSubmittedEntries(array);
                tasksListAdapter.updateAdapter(array);
            }

        }else if(request.getIdentifier() == DETAIL_REQUEST && responseObject != null){

            if(responseObject.optInt("status") == 1){
                JSONObject data = responseObject.optJSONObject("data");
                RestaurantDetailsModel model = new RestaurantDetailsModel(data.toString());
                model.setMode(DataDatabaseUtils.PENDING);
                model.setRestaurantId(mSelectedTask+"");
                RestaurantDetailsModel local = DataDatabaseUtils.getInstance(getContext())
                        .getRestaurantModelForId(model.getRestaurantId());
                if(local == null){
                    saveImage(data);
                    DataDatabaseUtils.getInstance(getContext()).
                            saveRestaurantForEditing(model.getRestaurantId()
                                    , model.getRestaurantName(), model.getRestaurantJSONString());
                }else if(local.getMode() != DataDatabaseUtils.SYNCED ){
                    DataDatabaseUtils.getInstance(getContext()).markRestaurantPending(model.getRestaurantId());
                    model = local;
                }
                RestaurantFormFragment fragment = RestaurantFormFragment.newInstance(model);
                addToBackStack(getActivity(),fragment);
            }
        }
    }

    private void saveImage(JSONObject data){

        String id = mSelectedTask+"";
        Type listType = new TypeToken<List<ImageStatusModel>>() {}.getType();
        List<ImageStatusModel> profileImages = new Gson().fromJson(data.optJSONArray("profile_image").toString(), listType);
        List<ImageStatusModel> menuImages = new Gson().fromJson(data.optJSONArray("menu_image").toString(), listType);

        DataDatabaseUtils.getInstance(getContext()).saveMenuImage(menuImages,id);
        DataDatabaseUtils.getInstance(getContext()).saveProfileImage(profileImages,id);

    }

    @Override
    public void showTaskDetail(int id,int taskId) {

        mSelectedTask = taskId;
        initiateRequestForRestaurantDetail(id);
    }


}
