package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class SubscribeSeason extends AsyncTask<Void, Void, Void> {
    private Integer show_id;
    private Context context;
    private ProgressDialog dialog;

    public SubscribeSeason(Context context, int show_id) {
        this.context = context;
        this.show_id = show_id;
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

        JsonUtils.SubscribeSeason(show_id);
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
