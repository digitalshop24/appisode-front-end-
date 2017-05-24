package com.example.romanchuk.appisode.tasks;

import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class CheckSubscriptions extends AsyncTask<Void, Void, Integer> {
    private Context context;

    public CheckSubscriptions(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected Integer doInBackground(Void... params) {

        Integer count = JsonUtils.subscriptionsCount(0, 500);
        return count;
    }

    @Override
    protected void onPostExecute(Integer count) {
        super.onPostExecute(count);
    }
}
