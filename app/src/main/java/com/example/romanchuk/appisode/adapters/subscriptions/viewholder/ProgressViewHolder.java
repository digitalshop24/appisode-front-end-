package com.example.romanchuk.appisode.adapters.subscriptions.viewholder;

/**
 * Created by romanchuk on 20.01.2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.romanchuk.appisode.R;

public class ProgressViewHolder extends RecyclerView.ViewHolder{
    public ProgressBar progressBar;
    public ProgressViewHolder(final View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
    }
}
