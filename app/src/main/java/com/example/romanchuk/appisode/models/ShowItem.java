package com.example.romanchuk.appisode.models;

import com.example.romanchuk.appisode.tools.DateTimeManager;

/**
 * Created by romanchuk on 05.01.2017.
 */

public class ShowItem {
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

    public EpisodeItem getLast_season_episode() {
        return last_season_episode;
    }

    public void setLast_season_episode(EpisodeItem last_season_episode) {
        this.last_season_episode = last_season_episode;
    }

    public String getSubscription_image() {
        return subscription_image;
    }

    public void setSubscription_image(String subscription_image) {
        this.subscription_image = subscription_image;
    }
    public void setEpisode_aired()
    {
        if (this.status.equals("airing")){
            if (this.next_episode != null) {
                this.episode_number_aired = String.valueOf(next_episode.getNumber());
                this.episode_aired = " СЕРИЯ ВЫХОДИТ";
            }
            else {
                this.episode_number_aired = "";
                this.episode_aired = "СЕРИЯ ВЫХОДИТ";
            }
        }
        if (this.status.equals("hiatus") && this.next_episode != null){
            this.episode_number_aired = String.valueOf(next_episode.getNumber());
            this.episode_aired = " СЕРИЯ ВЫХОДИТ";
            this.episode_aired = "АНОНСИРОВАН " + this.season_number + " СЕЗОН";
        }
        if (this.status.equals("hiatus") && this.next_episode == null){
            this.episode_number_aired = "";
//            this.episode_aired = "ЗАВЕРШЕН " + this.season_number + " СЕЗОН";
            this.episode_aired = "СЕРИАЛ ЗАКРЫТ";
        }
        if (this.status.equals("closed")){
            this.episode_number_aired = "";
            this.episode_aired = "СЕРИАЛ ЗАВЕРШЕН";
        }
    }

    public String getEpisode_number_aired() {
        return episode_number_aired;
    }

    public String getEpisode_aired() {
        return episode_aired;
    }


    public String getEpisode_air_date() {
        return episode_air_date;
    }

    public void setEpisode_air_date()    {
        this.episode_air_date = "СЕРИАЛ ЗАВЕРШЕН";
        if (next_episode == null) {

            if (season_number == -1)
                this.episode_air_date = "еще не вышел";
            else
                this.episode_air_date = DateTimeManager.DeclOfNumJustText5(season_number) + " " + season_number + " " + DateTimeManager.DeclOfNumJustText4(season_number);
            return;
        }

        this.episode_air_date = DateTimeManager.getDay(next_episode.getAir_date()) + " " +
                DateTimeManager.getMonthName(DateTimeManager.getMonth(next_episode.getAir_date())) + " " +
                DateTimeManager.getYear(next_episode.getAir_date());
    }

    public String getEpisode_air_date_detailed_hours() {
        return episode_air_date_detailed_hours;
    }

    public void setEpisode_air_date_detailed_hours() {
        this.episode_air_date_detailed_hours = "0";
    }

    public String getEpisode_air_date_detailed_months() {
        return episode_air_date_detailed_months;
    }

    public void setEpisode_air_date_detailed_months() {
        this.episode_air_date_detailed_months = "0";
        if (next_episode == null)
            return;

        int m = (int)Math.floor((double) (next_episode.getDays_left() / 30));

        this.episode_air_date_detailed_months = String.valueOf(m);
    }

    public String getEpisode_air_date_detailed_days() {
        return episode_air_date_detailed_days;
    }

    public void setEpisode_air_date_detailed_days()    {
        this.episode_air_date_detailed_days = "0";
        if (next_episode == null)
            return;

        int m = (int)Math.floor((double) (next_episode.getDays_left() / 30));
        int d = next_episode.getDays_left() - (m * 30);

        if (d < 0)
            this.episode_air_date_detailed_days = "0";
        else
            this.episode_air_date_detailed_days = String.valueOf(d);
    }


    private int id;
    private String poster;
    private String name;
    private String name_original;
    private String status;
    private int season_number;
    private EpisodeItem next_episode;
    private int current_season_episodes_number;
    private EpisodeItem last_season_episode;
    private String subscription_image;

    private String episode_air_date_detailed_months;
    private String episode_air_date_detailed_days;
    private String episode_air_date_detailed_hours;

    private String episode_number_aired;
    private String episode_aired;
    private String episode_air_date;
}
