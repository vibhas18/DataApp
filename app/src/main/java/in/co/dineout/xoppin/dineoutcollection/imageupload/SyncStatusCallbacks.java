package in.co.dineout.xoppin.dineoutcollection.imageupload;

/**
 * Created by suraj on 14/03/16.
 */
public interface SyncStatusCallbacks {
    void onSyncCompleted();

    void onProgressUpdate();

    void onErrorReceived();
}
