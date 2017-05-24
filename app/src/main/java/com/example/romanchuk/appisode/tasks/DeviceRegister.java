package com.example.romanchuk.appisode.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;


/**
 * Created by romanchuk on 21.01.2017.
 */

public class DeviceRegister extends AsyncTask<Void, Void, Boolean> {
    private Activity activity;
    private String push_token;
    private ProgressDialog dialog;

    public DeviceRegister(Activity activity, String push_token) {
        this.activity = activity;
        this.push_token = push_token;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(activity);
        dialog.setMessage(this.activity.getResources().getString(R.string.progress_bar));
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected Boolean doInBackground(Void... params) {

        boolean registerOk = JsonUtils.DeviceRegister(this.push_token);
        return registerOk;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
