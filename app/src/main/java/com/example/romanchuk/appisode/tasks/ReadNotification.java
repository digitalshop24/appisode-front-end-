package com.example.romanchuk.appisode.tasks;

import android.os.AsyncTask;

import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class ReadNotification extends AsyncTask<Void, Void, Void> {
    private Integer notification_id;

    public ReadNotification(int notification_id) {
        this.notification_id = notification_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Void doInBackground(Void... params) {

        JsonUtils.MarkAsReadNotification(notification_id);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
