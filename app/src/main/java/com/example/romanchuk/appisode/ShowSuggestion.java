package com.example.romanchuk.appisode;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

/**
 * Created by romanchuk on 15.03.2017.
 */

public class  ShowSuggestion implements SearchSuggestion {

    private String mShowName;
    private boolean mIsHistory = false;

    public ShowSuggestion(String suggestion) {
        this.mShowName = suggestion.toLowerCase();
    }

    public ShowSuggestion(Parcel source) {
        this.mShowName = source.readString();
        this.mIsHistory = source.readInt() != 0;
    }

    public void setIsHistory(boolean isHistory) {
        this.mIsHistory = isHistory;
    }

    public boolean getIsHistory() {
        return this.mIsHistory;
    }

    @Override
    public String getBody() {
        return mShowName;
    }

    public static final Creator<ShowSuggestion> CREATOR = new Creator<ShowSuggestion>() {
        @Override
        public ShowSuggestion createFromParcel(Parcel in) {
            return new ShowSuggestion(in);
        }

        @Override
        public ShowSuggestion[] newArray(int size) {
            return new ShowSuggestion[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mShowName);
        dest.writeInt(mIsHistory ? 1 : 0);
    }
}
