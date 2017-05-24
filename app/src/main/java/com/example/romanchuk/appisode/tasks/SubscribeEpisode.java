package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class SubscribeEpisode extends AsyncTask<Void, Void, Void> {
    private Integer show_id, episode_id;
    private Context context;
    private ProgressDialog dialog;

    public SubscribeEpisode(Context context, int show_id, int episode_id) {
        this.context = context;
        this.show_id = show_id;
        this.episode_id = episode_id;
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

        JsonUtils.SubscribeEpisode(show_id, episode_id);
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
