package com.example.romanchuk.appisode.listeners;

import com.example.romanchuk.appisode.models.SubscriptionsItem;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public interface SubscriptionsLoadedListener {
    public void onSubscriptionsLoaded(ArrayList<SubscriptionsItem> listMovies);
}
