package in.co.dineout.xoppin.dineoutcollection.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.adapter.RestaurantDetailListAdapter;
import in.co.dineout.xoppin.dineoutcollection.database.DatabaseManager;

public class SyncStateListActivity extends AppCompatActivity {

    public static final String ACTION_SHOW_SYNCED = "ACTION_SHOW_SYNCED";
    public static final String ACTION_SHOW_UNSYNCED = "ACTION_SHOW_UNSYNCED";

    private RestaurantDetailListAdapter restaurantDetailListAdapter;

    boolean longClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_state_list);

        ListView listView = (ListView) findViewById(R.id.lv_list);

        if (getIntent().getAction().equalsIgnoreCase(ACTION_SHOW_SYNCED)) {
            restaurantDetailListAdapter = new RestaurantDetailListAdapter(SyncStateListActivity.this,
                    DatabaseManager.getInstance().getAllSyncedRestaurants());
        } else {
            restaurantDetailListAdapter = new RestaurantDetailListAdapter(SyncStateListActivity.this,
                    DatabaseManager.getInstance().getAllUnSyncedRestaurants());
        }

        listView.setAdapter(restaurantDetailListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (longClick == true) {
                    return;
                }
                Intent i = new Intent(SyncStateListActivity.this, RestaurantFormActivity.class);
                i.setAction(RestaurantFormActivity.ACTION_UPDATE_RESTAURANT);
                i.putExtra(RestaurantFormActivity.KEY_RESTAURANT_DETAILS_DATA_ID, restaurantDetailListAdapter.getItem(position).getId());
                startActivity(i);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                longClick = true;

                if (getIntent().getAction().equalsIgnoreCase(ACTION_SHOW_SYNCED)) {
                    longClick = false;
                    return true;
                }

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SyncStateListActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Confirm Syncing...");

                // Setting Dialog Message
                alertDialog.setMessage("Are you sure you want Sync this? Once it is sent to sync it cannot be edited.");

                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("Sync", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (restaurantDetailListAdapter.getItem(position).sendToSync(SyncStateListActivity.this)) {
                            Toast.makeText(getApplicationContext(), "Sync Requested", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Cannot Sync Enter Mandatory data first", Toast.LENGTH_SHORT).show();
                        }



                        longClick = false;
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                        longClick = false;
                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
                return false;
            }
        });

    }
}
