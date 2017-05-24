package com.example.romanchuk.appisode.listeners;

import com.example.romanchuk.appisode.models.ShowsItem;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public interface ShowsPopularLoadedListener {
    public void onShowsLoaded(ArrayList<ShowsItem> listMovies);
    public void onSearchShowsLoaded(ShowsItem showsItem);
}
