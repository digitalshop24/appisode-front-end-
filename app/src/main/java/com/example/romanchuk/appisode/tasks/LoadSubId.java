package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.models.ShowsItem;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadSubId extends AsyncTask<Void, Void, ShowsItem> {
    private Integer showId;
    private Integer page;
    private Context context;
    private ProgressDialog dialog;

    public LoadSubId(Context context, int showId) {
        this.context = context;
        this.showId = showId;
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
    protected ShowsItem doInBackground(Void... params) {

        ShowsItem showsItem = JsonUtils.loadSubsId(showId);
        return showsItem;
    }

    @Override
    protected void onPostExecute(ShowsItem showsItem) {
        super.onPostExecute(showsItem);
        dialog.dismiss();
    }
}
