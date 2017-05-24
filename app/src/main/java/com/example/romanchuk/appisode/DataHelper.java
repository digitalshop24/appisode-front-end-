package com.example.romanchuk.appisode;

import android.content.Context;
import android.widget.Filter;

import com.example.romanchuk.appisode.models.ShowsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by romanchuk on 15.03.2017.
 */

public class DataHelper {
    private static List<ShowSuggestion> sShowSuggestions =
            new ArrayList<>();

    public static void addSuggestions(ArrayList<ShowsItem> listShows)
    {
        sShowSuggestions.clear();
        for (int i = 0; i < listShows.size(); i++) {
            sShowSuggestions.add(new ShowSuggestion(listShows.get(i).getName()));
        }
    }

    public interface OnFindSuggestionsListener {
        void onResults(List<ShowSuggestion> results);
    }

    public static List<ShowSuggestion> getHistory(Context context, int count) {

        List<ShowSuggestion> suggestionList = new ArrayList<>();
        ShowSuggestion showSuggestion;
        for (int i = 0; i < sShowSuggestions.size(); i++) {
            showSuggestion = sShowSuggestions.get(i);
            showSuggestion.setIsHistory(true);
            suggestionList.add(showSuggestion);
            if (suggestionList.size() == count) {
                break;
            }
        }
        return suggestionList;
    }

    public static void resetSuggestionsHistory() {
        for (ShowSuggestion showSuggestion : sShowSuggestions) {
            showSuggestion.setIsHistory(false);
        }
    }

    public static void findSuggestions(Context context, String query, final int limit, final long simulatedDelay,
                                       final OnFindSuggestionsListener listener) {
        new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                try {
                    Thread.sleep(simulatedDelay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                DataHelper.resetSuggestionsHistory();
                List<ShowSuggestion> suggestionList = new ArrayList<>();
                if (!(constraint == null || constraint.length() == 0)) {

                    for (ShowSuggestion suggestion : sShowSuggestions) {
//                        if (suggestion.getBody().toUpperCase()
//                                .startsWith(constraint.toString().toUpperCase())) {
//
//                            suggestionList.add(suggestion);
//                            if (limit != -1 && suggestionList.size() == limit) {
//                                break;
//                            }
//                        }
                        suggestionList.add(suggestion);
                        if (limit != -1 && suggestionList.size() == limit) {
                            break;
                        }
                    }
                }

                FilterResults results = new FilterResults();
//                Collections.sort(suggestionList, new Comparator<ColorSuggestion>() {
//                    @Override
//                    public int compare(ColorSuggestion lhs, ColorSuggestion rhs) {
//                        return lhs.getIsHistory() ? -1 : 0;
//                    }
//                });
                results.values = suggestionList;
                results.count = suggestionList.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                if (listener != null) {
                    listener.onResults((List<ShowSuggestion>) results.values);
                }
            }
        }.filter(query);
    }

    private static void initColorWrapperList(Context context) {


    }

}
