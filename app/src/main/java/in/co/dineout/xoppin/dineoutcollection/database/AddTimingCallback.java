package in.co.dineout.xoppin.dineoutcollection.database;

import java.util.HashMap;

import in.co.dineout.xoppin.dineoutcollection.model.TimingModel;

/**
 * Created by prateek.aggarwal on 7/20/16.
 */
public interface AddTimingCallback {

    public void addSlots(HashMap<String,TimingModel> update);

    public void deleteTiming(String key);
}
