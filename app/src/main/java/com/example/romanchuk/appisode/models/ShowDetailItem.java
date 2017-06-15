package com.example.romanchuk.appisode.models;

import com.example.romanchuk.appisode.tools.DateTimeManager;

/**
 * Created by romanchuk on 08.01.2017.
 */

public class ShowDetailItem {

    public String getSeason_air_date() {
        return season_air_date;
    }

    public void setSeason_air_date()    {
        this.season_air_date = "The series is over";
        if (last_episode == null) {
            if (season_number == -1)
                this.season_air_date = "Not yet released";
            else
                this.season_air_date = DateTimeManager.DeclOfNumJustText5(season_number) + " " + season_number + " " + DateTimeManager.DeclOfNumJustText4(season_number);
            return;
        }

        this.season_air_date = DateTimeManager.getDay(last_episode.getAir_date()) + " " +
                DateTimeManager.getMonthName(DateTimeManager.getMonth(last_episode.getAir_date())) + " " +
                DateTimeManager.getYear(last_episode.getAir_date());
    }

    public String getSeason_air_date_detailed_hours() {
        return season_air_date_detailed_hours;
    }

    public void setSeason_air_date_detailed_hours() {
        this.season_air_date_detailed_hours = "0";
        if (last_episode == null)
            return;

        int h = last_episode.getHous_left();
        if (h > 0){
            if (h > 24)
                h = h % 24;
        }
        else{
            if (h < -24)
                h = (-1 * h) % 24;
        }
        this.season_air_date_detailed_hours = String.valueOf(h);
    }

    public String getSeason_air_date_detailed_months() {
        return season_air_date_detailed_months;
    }

    public void setSeason_air_date_detailed_months() {
        this.season_air_date_detailed_months = "0";
        if (last_episode == null)
            return;

        int m = (int)Math.floor((double) (last_episode.getDays_left() / 30));

        this.season_air_date_detailed_months = String.valueOf(m);
    }

    public String getSeason_air_date_detailed_days() {
        return season_air_date_detailed_days;
    }

    public void setSeason_air_date_detailed_days()    {
        this.season_air_date_detailed_days = "0";
        if (last_episode == null)
            return;

        int m = (int)Math.floor((double) (last_episode.getDays_left() / 30));
        int d = last_episode.getDays_left() - (m * 30);

        if (d < 0)
            this.season_air_date_detailed_days = "0";
        else
            this.season_air_date_detailed_days = String.valueOf(d);
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_original() {
        return name_original;
    }

    public void setName_original(String name_original) {
        this.name_original = name_original;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public EpisodeItem getNext_episode() {
        return next_episode;
    }

    public void setNext_episode(EpisodeItem next_episode) {
        this.next_episode = next_episode;
    }

    public int getCurrent_season_episodes_number() {
        return current_season_episodes_number;
    }

    public void setCurrent_season_episodes_number(int current_season_episodes_number) {
        this.current_season_episodes_number = current_season_episodes_number;
    }

    public SubscriptionItem getSubscription() {
        return subscription;
    }

    public void setSubscription(SubscriptionItem subscription) {
        this.subscription = subscription;
    }

    public EpisodeItem[] getEpisodes() {
        return episodes;
    }

    public void setEpisodes(EpisodeItem[] episodes) {
        this.episodes = episodes;
    }

    public String getSeason_aired() {
        return season_aired;
    }

    public String getSeason_number_aired() {
        return season_number_aired;
    }

    public void setLastEpisode()
    {
        if (this.episodes != null && this.episodes.length > 0){
            this.last_episode = this.episodes[episodes.length - 1];
        }
    }

    public void setSeason_aired()
    {
        if (this.status.equals("airing")){
            this.season_number_aired = season_number + " season";
            this.season_aired = "Comes out";
        }
        if (this.status.equals("closed")){
            this.season_number_aired = "";
            this.season_aired = "The series is over";
        }
    }

    public int getNext_season_number() {
        int next_season_number = 1;
        if (this.status.equals("hiatus") && this.next_episode == null) {
            next_season_number = this.season_number + 1;
            if (this.season_number == -1)
                next_season_number = 1;
        }
        return next_season_number;
    }

    public boolean isShowAnnounced() {
        if (this.status.equals("hiatus") && this.next_episode != null) {
            return true;
        }
        return false;
    }

    public boolean isShowHiatus() {
        if (this.status.equals("hiatus") && this.next_episode == null) {
            return true;
        }
        return false;
    }

    public boolean isShowClosed() {
        if (this.status.equals("closed")) {
            return true;
        }
        return false;
    }

    private int id;

    public int getSelected_episode_id() {
        return selected_episode_id;
    }

    public void setSelected_episode_id(int selected_episode_id) {
        this.selected_episode_id = selected_episode_id;
    }

    private int selected_episode_id;
    private String poster;
    private String name;
    private String name_original;
    private String status;
    private int season_number ;
    private EpisodeItem next_episode;

    public int getNext_episodes_number() {
        return next_episodes_number;
    }

    public void setNext_episodes_number(int next_episodes_number) {
        this.next_episodes_number = next_episodes_number;
    }

    private int next_episodes_number;
    private int current_season_episodes_number;
    private SubscriptionItem subscription;
    private EpisodeItem[] episodes;

    private EpisodeItem last_episode;

    private String season_number_aired;
    private String season_aired;
    private String season_air_date;
    private String season_air_date_detailed_months;
    private String season_air_date_detailed_days;
    private String season_air_date_detailed_hours;

}
