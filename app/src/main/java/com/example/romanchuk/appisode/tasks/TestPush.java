package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.Requestor;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class TestPush extends AsyncTask<Void, Void, Void> {
    private String pushToken, message, push_type;
    private Context context;
    private ProgressDialog dialog;

    public TestPush(Context context, String pushToken, String message, String push_type) {
        this.context = context;
        this.pushToken = pushToken;
        this.message = message;
        this.push_type = push_type;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setMessage(this.context.getResources().getString(R.string.progress_bar));
        dialog.setCancelable(false);
        dialog.show();
    }
    @Override
    protected Void doInBackground(Void... params) {

        Requestor.testPush(pushToken, message, push_type);
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
