package com.example.romanchuk.appisode.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class DeviceUnRegister extends AsyncTask<Void, Void, Boolean> {
    private Activity activity;
    private ProgressDialog dialog;

    public DeviceUnRegister(Activity activity) {
        this.activity = activity;
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

        boolean registerOk = JsonUtils.DeviceUnRegister();
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
