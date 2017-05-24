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

public class LoadShowsPopular extends AsyncTask<Void, Void, ArrayList<ShowsItem>> {
    private ShowsPopularLoadedListener myComponent;
    private Integer page;
    private Integer showsId = -1;
    private Context context;
    private ProgressDialog dialog;
    private boolean loadMore = false;

    public LoadShowsPopular(Context context, ShowsPopularLoadedListener myComponent, int page, int showsId, boolean loadMore) {
        this.context = context;
        this.page = page;
        this.showsId = showsId;
        this.loadMore = loadMore;
        this.myComponent = myComponent;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (!loadMore){
            dialog = new ProgressDialog(context);
            dialog.setMessage(this.context.getResources().getString(R.string.progress_bar));
            dialog.setCancelable(false);
            dialog.show();
        }
    }
    @Override
    protected ArrayList<ShowsItem> doInBackground(Void... params) {

        ArrayList<ShowsItem> list = JsonUtils.loadShowsPopular(page, 25);
        if (showsId != -1) {
            ShowsItem showsItem = JsonUtils.loadSubsId(showsId);
            if (showsItem != null)
                list.add(0, showsItem);
        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<ShowsItem> list) {
        super.onPostExecute(list);
        if (!loadMore)
            dialog.dismiss();
        if (myComponent != null && list.size() > 0) {
            myComponent.onShowsLoaded(list);
        }
    }
}
