package com.nextgis.mobile.util;

import android.os.FileObserver;
import android.util.Log;

import com.nextgis.maplib.util.SettingsConstants;
import com.nextgis.mobile.activity.MainActivity;

import java.util.LinkedList;
import java.util.Queue;

/**
 * An android FileObserver to check for incoming Gis Files
 * Created by arka on 22/1/17.
 */

public class GisFileObserver extends FileObserver {
    private static String TAG = "GisFileObserver";
    private MainActivity mainActivity;
    private Queue<String> gisFilesQueue;

    static final int mask = (FileObserver.CREATE |
            FileObserver.DELETE |
            FileObserver.DELETE_SELF |
            FileObserver.MODIFY |
            FileObserver.MOVED_FROM |
            FileObserver.MOVED_TO |
            FileObserver.MOVE_SELF);

    public GisFileObserver(MainActivity activity) {
        super(SettingsConstants.WORKING_DIR, mask);
        this.mainActivity = activity;
        gisFilesQueue = new LinkedList<>();
    }

    @Override
    public void onEvent(int event, final String path) {
        switch(event){
            case FileObserver.CREATE:
                Log.d(TAG, "CREATE:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.DELETE:
                Log.d(TAG, "DELETE:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.DELETE_SELF:
                Log.d(TAG, "DELETE_SELF:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.MODIFY:
                Log.d(TAG, "MODIFY:" + SettingsConstants.WORKING_DIR + path);
                if(mainActivity.isActivityInForeground()) {
                    createLocalLayer(SettingsConstants.WORKING_DIR + path);
                } else {
                    // Add layers received to queue if activity is in foreground
                    if(path.endsWith(".zip") || path.endsWith("ngrc") ||
                            path.endsWith("geojson") || path.endsWith("ngfp")) {
                        gisFilesQueue.add(SettingsConstants.WORKING_DIR + path);
                    }
                }
                break;
            case FileObserver.MOVED_FROM:
                Log.d(TAG, "MOVED_FROM:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.MOVED_TO:
                Log.d(TAG, "MOVED_TO:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.MOVE_SELF:
                Log.d(TAG, "MOVE_SELF:" + SettingsConstants.WORKING_DIR + path);
                break;
            default:
                // just ignore
                break;
        }
    }

    /**
     * Load layers synced while in background if app is in foreground
     */
    public void emptyQueue() {
        while(!gisFilesQueue.isEmpty() && mainActivity.isActivityInForeground()) {
            createLocalLayer(gisFilesQueue.remove());
        }
    }

    /**
     * Make MainActivity load synced GIS layers
     * @param path
     *          the path of the file received
     */
    private void createLocalLayer(final String path) {
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mainActivity.createLocalLayer(path);
            }
        });
    }

    public void close() {
        super.finalize();
    }
}
