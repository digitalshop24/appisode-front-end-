package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class TaskUnsubscribe extends AsyncTask<Void, Void, Void> {
    private Integer subscription_id, show_id;
    private Context context;
    private ProgressDialog dialog;

    public TaskUnsubscribe(Context context, int subscription_id, int show_id) {
        this.context = context;
        this.subscription_id = subscription_id;
        this.show_id = show_id;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog = new ProgressDialog(context);
//        dialog.setMessage("Please wait...");
//        dialog.setCancelable(false);
//        dialog.show();
    }
    @Override
    protected Void doInBackground(Void... params) {

        JsonUtils.Unsubscribe(subscription_id, show_id);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
    }
}
