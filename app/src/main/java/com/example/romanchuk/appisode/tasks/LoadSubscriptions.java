package com.example.romanchuk.appisode.tasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.json.JsonUtils;
import com.example.romanchuk.appisode.listeners.SubscriptionsLoadedListener;
import com.example.romanchuk.appisode.models.SubscriptionsItem;

import java.util.ArrayList;


/**
 * Created by romanchuk on 21.01.2017.
 */

public class LoadSubscriptions extends AsyncTask<Void, Void, ArrayList<SubscriptionsItem>> {
    private SubscriptionsLoadedListener myComponent;
    private Integer page;
    private Context context;
    private ProgressDialog dialog;
    private boolean loadMore = false;

    public LoadSubscriptions(Context context, SubscriptionsLoadedListener myComponent, int page, boolean loadMore) {
        this.context = context;
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
    protected ArrayList<SubscriptionsItem> doInBackground(Void... params) {

        ArrayList<SubscriptionsItem> list = JsonUtils.loadSubscriptions(page, 225);
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<SubscriptionsItem> list) {
        super.onPostExecute(list);
        if (!loadMore)
            dialog.dismiss();
        if (myComponent != null && list.size() > 0) {
            myComponent.onSubscriptionsLoaded(list);
        }
    }
}
