package com.example.romanchuk.appisode.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import com.example.romanchuk.appisode.MainActivity;
import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.json.Requestor;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class CheckAuth extends AsyncTask<Void, Void, Integer> {
    private Activity activity;
    private ProgressDialog dialog;
    private String auth_token;

    public CheckAuth(Activity activity, String auth_token) {
        this.activity = activity;
        this.auth_token = auth_token;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        dialog = new ProgressDialog(activity);
//        dialog.setMessage("Please wait...");
//        dialog.setCancelable(false);
//        dialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        return JsonUtils.UsersCheckAuth(auth_token);
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
//        if (dialog.isShowing()) {
//            dialog.dismiss();
//        }
        if (result == 200) {
            new CheckSubscriptions(activity).execute();
            Requestor.setToken(auth_token);
            Intent myIntent = new Intent(activity, MainActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(myIntent);
            activity.finish();
        }
        if (result == 401) {
            Toast toast = Toast.makeText(activity, this.activity.getResources().getString(R.string.unauthorized), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        }
    }
}
