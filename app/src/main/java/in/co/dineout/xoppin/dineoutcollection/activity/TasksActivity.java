package in.co.dineout.xoppin.dineoutcollection.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.fragment.TaskListFragment;
import in.co.dineout.xoppin.dineoutcollection.model.AssignedTaskModel;

/**
 * Created by suraj on 16/02/16.
 */
public class TasksActivity extends AppCompatActivity {
    private static final String TAG = TasksActivity.class.getSimpleName();

    public static final String ACTION_FETCHED = "ACTION_FETCHED";
    public static final String ACTION_FRESH = "ACTION_FRESH";

    public static final String KEY_DATA = "KEY_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);

        if (getIntent().getAction().equalsIgnoreCase(ACTION_FETCHED)) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_assigned_tasks,
                            TaskListFragment.newInstance((ArrayList<AssignedTaskModel>) getIntent().getSerializableExtra(KEY_DATA)),
                            TaskListFragment.TAG2)
                    .commitAllowingStateLoss();
        } else {
            getSupportFragmentManager().beginTransaction().add(R.id.fl_assigned_tasks, TaskListFragment.newInstance(), TaskListFragment.TAG2)
                    .commitAllowingStateLoss();
        }


    }
}
