package in.co.dineout.xoppin.dineoutcollection.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;
import in.co.dineout.xoppin.dineoutcollection.helper.LoginHelper;
import in.co.dineout.xoppin.dineoutcollection.helper.ObjectMapperFactory;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTaskModel;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTasks;
import in.co.dineout.xoppin.dineoutcollection.model.MreModel;

public class DashboardActivity extends AppCompatActivity {
    private static final String TAG = DashboardActivity.class.getSimpleName();

    private ArrayList<AssignedTaskModel> assignedTaskModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);

        MreModel user = LoginHelper.getUser(this);
        ((TextView) findViewById(R.id.tv_name)).setText(user.getFirstName() + " " + user.getLastName());
        ((TextView) findViewById(R.id.tv_email)).setText(user.getEmail());
        ((TextView) findViewById(R.id.tv_assigned_city)).setText(user.getAssignedCity());

        ((TextView) findViewById(R.id.tv_pending_synced)).setText("" + DatabaseManager.getInstance().getAllUnSyncedRestaurants().size());
        ((TextView) findViewById(R.id.tv_synced)).setText("" + DatabaseManager.getInstance().getAllSyncedRestaurants().size());

        findViewById(R.id.btn_assigned_tasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null == assignedTaskModels) {
                    Intent intent = new Intent(DashboardActivity.this, TasksActivity.class);
                    intent.setAction(TasksActivity.ACTION_FRESH);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(DashboardActivity.this, TasksActivity.class);
                    intent.setAction(TasksActivity.ACTION_FETCHED);
                    intent.putExtra(TasksActivity.KEY_DATA, assignedTaskModels);
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.btn_pending).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SyncStateListActivity.class);
                i.setAction(SyncStateListActivity.ACTION_SHOW_UNSYNCED);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_synced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, SyncStateListActivity.class);
                i.setAction(SyncStateListActivity.ACTION_SHOW_SYNCED);
                startActivity(i);
            }
        });

        findViewById(R.id.btn_new_rest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, RestaurantFormActivity.class);
                intent.setAction(RestaurantFormActivity.ACTION_NEW_RESTAURANT);
                startActivity(intent);
            }
        });

        initiateRequestForAssignedTasks();

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
                    ((TextView) findViewById(R.id.tv_assigned_count)).setText("" + assignedTasks.getTask_list().size());
                    assignedTaskModels = assignedTasks.getTask_list();
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        };


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET, url, null, respListener, errorListener) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = LoginHelper.getDefaultHeaders(DashboardActivity.this);
                headers.putAll(super.getHeaders());
                return headers;
            }
        };

        DineoutCollectApp.getInstance().addToRequestQueue(jsonObjReq, TAG_REQUEST_ASSIGNED_TASK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(1, 1, 1, "Logout");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case 1:
                LoginHelper.logout(DashboardActivity.this);
                startActivity(new Intent(DashboardActivity.this, MainActivity.class));
                finish();
                break;
            default:

        }
        return true;
    }
}
