package com.example.romanchuk.appisode.tasks;

import android.os.AsyncTask;

import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.models.NotificationItem;

import java.util.ArrayList;


/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadNotifications extends AsyncTask<Void, Void, ArrayList<NotificationItem>> {

    public LoadNotifications() {
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected ArrayList<NotificationItem> doInBackground(Void... params) {

        ArrayList<NotificationItem> list = JsonUtils.loadNotifications();
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<NotificationItem> list) {
        super.onPostExecute(list);
    }
}
