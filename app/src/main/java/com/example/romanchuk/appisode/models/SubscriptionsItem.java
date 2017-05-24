package com.example.romanchuk.appisode.models;

import com.example.romanchuk.appisode.tools.DateTimeManager;

/**
 * Created by romanchuk on 08.01.2017.
 */

public class SubscriptionsItem {
    private String name;
    private String name_original;
    private int id;
    private String subtype;
    private String subsStatusLeft;
    private String subsCount;
    private String subsStatusRight;
    private int episodes_interval;
    private EpisodeItem next_notification_episode;
    private ShowItem show;
    private float showSchedule;

    public boolean needRotateLine() {
        if (this.show == null || this.show.getStatus().equals("closed") || (this.show.getStatus().equals("hiatus") && this.show.getNext_episode() == null)) {
            return true;
        }
        return false;
    }

    public float getShowSchedule() {
        return showSchedule;
    }

    public void setShowSchedule() {

        this.showSchedule = 0;
        if (this.show == null || this.show.getStatus().equals("closed") || (this.show.getStatus().equals("hiatus") && this.show.getNext_episode() == null)) {
            return;
        }

        float currentEpisodeNumber = 0;
        float currentSeasonEpisodesNumber = this.show.getCurrent_season_episodes_number();
        if (currentSeasonEpisodesNumber == 0)
            currentSeasonEpisodesNumber = 1;
        if (this.show.getNext_episode() != null) {
            currentEpisodeNumber = this.show.getNext_episode().getNumber();
            if (currentEpisodeNumber > 1)
                currentEpisodeNumber--;
        }

        this.showSchedule = currentEpisodeNumber / currentSeasonEpisodesNumber;
    }

    public String getName() {
        return name;
    }

    public void setName() {
        this.name = "name";
        if (show != null)
            this.name = show.getName();
    }

    public String getName_original() {
        return name_original;
    }

    public void setName_original() {
        this.name_original = "name_original";
        if (show != null)
            this.name_original = show.getName_original();
    }

    public ShowItem getShow() {
        return show;
    }

    public void setShow(ShowItem show) {
        this.show = show;
    }

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

    public int getEpisodes_interval() {
        return episodes_interval;
    }

    public void setEpisodes_interval(int episodes_interval) {
        this.episodes_interval = episodes_interval;
    }

    public String getSubsStatusLeft() {
        return subsStatusLeft;
    }

    public void setSubsStatusLeft(String subsStatusLeft) {
        this.subsStatusLeft = subsStatusLeft;
    }

    public String getSubsCount() {
        return subsCount;
    }

    public void setSubsCount(String subsCount) {
        this.subsCount = subsCount;
    }

    public String getSubsStatusRight() {
        return subsStatusRight;
    }

    public void setStatus() {
        if (this.show == null) {
            return;
        }
        if (this.show.getStatus().equals("airing")) {
            if (this.next_notification_episode != null) {
                this.subsCount = String.valueOf(this.next_notification_episode.getDays_left());
                if (this.subtype.equals("episode")) {
                    if (this.episodes_interval != 0) {
                        if (this.episodes_interval == 1) {
                            this.subsStatusLeft = "через";
                            this.subsStatusRight = DateTimeManager.DeclOfNumJustText1(this.next_notification_episode.getDays_left()) + " новая серия";
                            if (this.next_notification_episode.getDays_left() == 0){
                                this.subsCount = "сегодня";
                                this.subsStatusLeft = "";
                                this.subsStatusRight = "новая серия";
                            }
                        }
                        if (this.episodes_interval > 1) {
                            this.subsStatusLeft = "через";
                            this.subsStatusRight = DateTimeManager.DeclOfNumJustText1(this.next_notification_episode.getDays_left()) + " новые серии";
                            if (this.next_notification_episode.getDays_left() > 0)
                                this.subsStatusLeft = "через";
                            else{
                                this.subsCount = "сегодня";
                                this.subsStatusLeft = "";
                                this.subsStatusRight = "новые серии";
                            }
                        }
                    }
                }
                if (this.subtype.equals("season")) {
                    this.subsStatusLeft = "через";
                    this.subsStatusRight = DateTimeManager.DeclOfNumJustText1(this.next_notification_episode.getDays_left()) + " конец сезона";
                }
            }
        }
        if (this.show.getStatus().equals("hiatus")) {
            if (this.show.getNext_episode() != null) {
                if (this.subtype.equals("episode")) {
                    if (this.episodes_interval != 0) {
                        if (this.episodes_interval == 1) {
                            this.subsCount = String.valueOf(this.show.getNext_episode().getDays_left());
                            this.subsStatusLeft = "через";
                            this.subsStatusRight = DateTimeManager.DeclOfNumJustText1(this.show.getNext_episode().getDays_left()) + " новая серия";
                            if (this.next_notification_episode.getDays_left() == 0){
                                this.subsCount = "сегодня";
                                this.subsStatusLeft = "";
                                this.subsStatusRight = "новая серия";
                            }
                        }
                        if (this.episodes_interval > 1) {
                            if (this.next_notification_episode != null) {
                                this.subsCount = String.valueOf(this.next_notification_episode.getDays_left());
                                this.subsStatusRight =
                                        DateTimeManager.DeclOfNumJustText1(this.show.getNext_episode().getDays_left()) +
                                                " новые серии";
                            } else {
                                this.subsCount = String.valueOf(this.show.getNext_episode().getDays_left());
                                this.subsStatusRight =
                                        DateTimeManager.DeclOfNumJustText1(this.show.getNext_episode().getDays_left()) +
                                                " новые серии";
                            }
                            if (this.next_notification_episode.getDays_left() > 0)
                                this.subsStatusLeft = "через";
                            else{
                                this.subsCount = "сегодня";
                                this.subsStatusLeft = "";
                                this.subsStatusRight = "новые серии";
                            }
                        }
                    }
                }
                if (this.subtype.equals("season")) {
                    if (this.show.getNext_episode() != null)
                        this.subsCount = String.valueOf(this.show.getNext_episode().getDays_left());
                    this.subsStatusLeft = "через";
                    if (this.show.getNext_episode() != null)
                        this.subsStatusRight = DateTimeManager.DeclOfNumJustText1(this.show.getNext_episode().getDays_left()) + " новый сезон";
                }
            } else {
//                _iconBellBorder.IsVisible = false;
                this.subsCount = String.valueOf(this.show.getSeason_number() + 1);
                this.subsStatusLeft = "ждем анонса";
                this.subsStatusRight = "сезона";
            }
        }
        if (this.show.getStatus().equals("closed"))
            this.subsStatusLeft = "сериал завершен";
    }

    public EpisodeItem getNext_notification_episode() {
        return next_notification_episode;
    }

    public void setNext_notification_episode(EpisodeItem next_notification_episode) {
        this.next_notification_episode = next_notification_episode;
    }
}
