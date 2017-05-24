package com.example.romanchuk.appisode.models;

import java.util.ArrayList;

/**
 * Created by romanchuk on 24.02.2017.
 */

public class SearchItem {
    ArrayList<ShowsItem> showsItems;
    Integer totalCount;

    public ArrayList<ShowsItem> getShowsItems() {
        return showsItems;
    }

    public void setShowsItems(ArrayList<ShowsItem> showsItems) {
        this.showsItems = showsItems;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
