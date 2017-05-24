package com.example.romanchuk.appisode.tasks;

import android.os.AsyncTask;

import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.models.ShowDetailItem;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadShowsId extends AsyncTask<Void, Void, ShowDetailItem> {
    private Integer showsId;

    public LoadShowsId(int showsId) {
        this.showsId = showsId;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected ShowDetailItem doInBackground(Void... params) {

        ShowDetailItem showItem = JsonUtils.loadShowsId(showsId);
        return showItem;
    }

    @Override
    protected void onPostExecute(ShowDetailItem showItem) {
        super.onPostExecute(showItem);
    }
}
