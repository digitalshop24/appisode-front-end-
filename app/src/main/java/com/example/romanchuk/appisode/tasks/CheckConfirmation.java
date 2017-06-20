package com.example.romanchuk.appisode.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.auth.Step5Activity;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.json.Requestor;
import com.example.romanchuk.appisode.tools.Utils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class CheckConfirmation extends AsyncTask<Void, Void, String> {
    private String phone_number, confirmation;
    private Activity activity;
    private ProgressDialog dialog;

    public CheckConfirmation(Activity activity, String phone_number, String confirmation) {
        this.activity = activity;
        this.phone_number = phone_number;
        this.confirmation = confirmation;
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
    protected String doInBackground(Void... params) {

        return JsonUtils.CheckConfirmation(phone_number, confirmation);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        if (!result.equals("no_auth_token")) {
            Utils.SaveAuthToken(activity, result);
            Requestor.setToken(result);

            Intent myIntent = new Intent(activity, Step5Activity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(myIntent);
            activity.finish();
        }
    }
}
