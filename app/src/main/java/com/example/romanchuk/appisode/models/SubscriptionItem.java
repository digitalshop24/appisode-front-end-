package com.example.romanchuk.appisode.models;

/**
 * Created by romanchuk on 08.01.2017.
 */

public class SubscriptionItem {
    private int id;
    private String subtype;
    private String episodes_interval;
    private EpisodeItem next_notification_episode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getEpisodes_interval() {
        return episodes_interval;
    }

    public void setEpisodes_interval(String episodes_interval) {
        this.episodes_interval = episodes_interval;
    }

    public EpisodeItem getNext_notification_episode() {
        return next_notification_episode;
    }

    public void setNext_notification_episode(EpisodeItem next_notification_episode) {
        this.next_notification_episode = next_notification_episode;
    }
}
