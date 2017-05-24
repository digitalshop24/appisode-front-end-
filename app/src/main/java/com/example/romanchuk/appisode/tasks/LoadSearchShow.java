package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.listeners.ShowsPopularLoadedListener;
import com.example.romanchuk.appisode.models.ShowsItem;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadSearchShow extends AsyncTask<Void, Void, ShowsItem> {
    private ShowsPopularLoadedListener myComponent;
    private Integer showsId = -1;
    private Context context;
    private ProgressDialog dialog;

    public LoadSearchShow(Context context, ShowsPopularLoadedListener myComponent, int showsId) {
        this.context = context;
        this.showsId = showsId;
        this.myComponent = myComponent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    @Override
    protected ShowsItem doInBackground(Void... params) {

        return JsonUtils.loadSubsId(showsId);
    }

    @Override
    protected void onPostExecute(ShowsItem showsItem) {
        super.onPostExecute(showsItem);
        if (myComponent != null) {
            myComponent.onSearchShowsLoaded(showsItem);
        }
    }
}
