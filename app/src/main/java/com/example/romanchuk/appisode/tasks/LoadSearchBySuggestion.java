package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.listeners.SearchLoadedListener;
import com.example.romanchuk.appisode.models.SearchItem;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadSearchBySuggestion extends AsyncTask<Void, Void, SearchItem> {
    private SearchLoadedListener myComponent;
    private Integer page;
    private String query;
    private Context context;
    private ProgressDialog dialog;
    private boolean loadMore = false;

    public LoadSearchBySuggestion(Context context, SearchLoadedListener myComponent, int page, String query, boolean loadMore) {
        this.context = context;
        this.query = query;
        this.page = page;
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
    protected SearchItem doInBackground(Void... params) {

        SearchItem searchItem = JsonUtils.loadSearch(page, 25, query);
        return searchItem;
    }

    @Override
    protected void onPostExecute(SearchItem searchItem) {
        super.onPostExecute(searchItem);
        if (!loadMore)
            dialog.dismiss();
        if (myComponent != null) {
            myComponent.onSearchLoadedBySuggestion(searchItem.getShowsItems(), searchItem.getTotalCount());
        }
    }
}
