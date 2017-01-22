package com.nextgis.mobile.util;


import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;
import android.content.Context;

import com.nextgis.maplib.api.IGeometryCache;
import com.nextgis.maplib.api.IGeometryCacheItem;
import com.nextgis.maplib.datasource.GeometryRTree;
import com.nextgis.maplib.datasource.ngw.SyncAdapter;
import com.nextgis.maplib.map.MapBase;
import com.nextgis.maplib.map.MapContentProviderHelper;
import com.nextgis.maplib.util.SettingsConstants;
import com.nextgis.mobile.activity.MainActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Sync GIS data available from peers
 * Created by arka on 18/1/17.
 */
public class SyncGIS {

    private ArrayList<File> zipGisFiles;
    private File path;
    private MainActivity activity;

    /**
     * @param path
     *          path where incoming GIS files from peers will reside
     */
    public SyncGIS(MainActivity activity, File path) {
        this.activity = activity;
        this.path = path;
    }

    /**
     * Find all gis files collected from peers
     */
    private void findGisFiles () {
        zipGisFiles = new ArrayList<>();

        for(File file: path.listFiles()) {
            if(!file.isDirectory() && file.getName().endsWith(".zip")) {
                Log.d("YOLO Found", "Found " + file.getAbsolutePath());
                zipGisFiles.add(file);
            }
        }
    }

    /**
     * sync GIS information from peers
     */
    public void syncGisFiles() {
        findGisFiles();
        for (File file : zipGisFiles) {
            activity.createLocalLayer(file.getAbsolutePath());
        }
    }
}
