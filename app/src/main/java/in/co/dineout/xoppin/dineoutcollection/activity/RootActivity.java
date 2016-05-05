package in.co.dineout.xoppin.dineoutcollection.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import in.co.dineout.xoppin.dineoutcollection.R;

/**
 * Created by prateek.aggarwal on 5/5/16.
 */
public class RootActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_root);
    }
}
