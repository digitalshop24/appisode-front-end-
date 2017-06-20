package com.example.romanchuk.appisode.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.auth.Step4Activity;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.tools.Utils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class Register extends AsyncTask<Void, Void, Boolean> {
    private String phone_number;
    private Activity activity;
    private ProgressDialog dialog;

    public Register(Activity activity, String phone_number) {
        this.activity = activity;
        this.phone_number = phone_number;
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

        boolean registerOk = JsonUtils.Register(phone_number);
        return registerOk;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        if (result) {
            Utils.SavePhoneNumber(activity, phone_number);
            Intent myIntent = new Intent(activity, Step4Activity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(myIntent);
//            activity.finish();
        }
    }
}
