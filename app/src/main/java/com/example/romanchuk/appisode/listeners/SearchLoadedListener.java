package com.example.romanchuk.appisode.listeners;

import com.example.romanchuk.appisode.models.ShowsItem;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public interface SearchLoadedListener {
    public void onSearchLoaded(ArrayList<ShowsItem> listMovies, Integer totalCount);
    public void onSearchLoadedBySuggestion(ArrayList<ShowsItem> listMovies, Integer totalCount);
}
