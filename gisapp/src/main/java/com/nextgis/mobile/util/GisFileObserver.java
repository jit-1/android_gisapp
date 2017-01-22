package com.nextgis.mobile.util;

import android.os.FileObserver;
import android.util.Log;

import com.nextgis.maplib.util.SettingsConstants;
import com.nextgis.mobile.activity.MainActivity;

import java.io.File;

/**
 * An android FileObserver to check for incoming Gis Files
 * Created by arka on 22/1/17.
 */

public class GisFileObserver extends FileObserver {
    private static String TAG = "GisFileObserver";
    private MainActivity mainActivity;

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
    }

    @Override
    public void onEvent(int event, String path) {
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
                break;
            case FileObserver.MOVED_FROM:
                Log.d(TAG, "MOVED_FROM:" + SettingsConstants.WORKING_DIR + path);
                break;
            case FileObserver.MOVED_TO:
                Log.d(TAG, "MOVED_TO:" + SettingsConstants.WORKING_DIR + path);
                mainActivity.createLocalLayer(path);
                break;
            case FileObserver.MOVE_SELF:
                Log.d(TAG, "MOVE_SELF:" + SettingsConstants.WORKING_DIR + path);
                break;
            default:
                // just ignore
                break;
        }
    }

    public void close() {
        super.finalize();
    }
}
