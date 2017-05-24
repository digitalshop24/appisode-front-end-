package com.example.romanchuk.appisode.myapplication;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class MyApplication extends Application {


    private static ArrayList<Integer> SubsId;

    public static Integer getTotalSubs() {
        return TotalSubs;
    }

    public static void setTotalSubs(Integer totalSubs) {
        TotalSubs = totalSubs;
    }

    public static ArrayList<Integer> getSubsId() {
        return SubsId;
    }

    public static boolean showInSubs(Integer showId) {
        if (SubsId != null) {
            for (int i = 1; i <= SubsId.size(); i++) {
                int id = SubsId.get(i - 1);
                if (showId == id)
                    return true;
            }
        }
        return false;
    }

    public static void removeSubsId(Integer showId) {
        if (SubsId != null && SubsId.contains(showId)) {
            SubsId.remove(SubsId.indexOf(showId));
        }
    }

    public static void addSubsId(Integer subsId) {
        if (SubsId == null)
            SubsId = new ArrayList<>();
        SubsId.add(subsId);
    }

    public static void clearSubsId() {
        if (SubsId == null)
            SubsId = new ArrayList<>();
        SubsId.clear();
    }

    private static Integer TotalSubs;
    private static MyApplication sInstance;

    public static MyApplication getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

}
