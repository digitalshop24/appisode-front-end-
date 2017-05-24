package com.example.romanchuk.appisode.json;

import android.util.Log;

import com.example.romanchuk.appisode.models.EpisodeItem;
import com.example.romanchuk.appisode.models.NotificationItem;
import com.example.romanchuk.appisode.models.SearchItem;
import com.example.romanchuk.appisode.models.ShowDetailItem;
import com.example.romanchuk.appisode.models.ShowItem;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.models.SubscriptionsItem;
import com.example.romanchuk.appisode.myapplication.MyApplication;
import com.example.romanchuk.appisode.tools.EpisodeItemKeys;
import com.example.romanchuk.appisode.tools.NotificationsItemKeys;
import com.example.romanchuk.appisode.tools.ShowDetailKeys;
import com.example.romanchuk.appisode.tools.ShowsItemKeys;
import com.example.romanchuk.appisode.tools.SubscriptionsItemKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class Parser {
    private static final String PARSER_SHOWS_ID_TAG = "PARSER_SHOWS_ID_TAG";
    private static final String PARSER_SHOWS_NEW_TAG = "PARSER_SHOWS_NEW_TAG";
    private static final String PARSER_SHOWS_POP_TAG = "PARSER_SHOWS_POP_TAG";
    private static final String PARSER_SEARCH_TAG = "PARSER_SEARCH_TAG";
    private static final String PARSER_SUBS_TAG = "PARSER_SUBS_TAG";
    private static final String PARSER_NOTIF_TAG = "PARSER_NOTIF_TAG";
    private static final String PARSER_CONFIRMATION_TAG = "PARSER_CONFIRMATION_TAG";

    public static ShowDetailItem parseShowsIdJSON(JSONObject jsonObject) {
        ShowDetailItem model = new ShowDetailItem();
        if (jsonObject != null && jsonObject.length() > 0) {
            try {
                if (jsonObject.length() > 0) {
                    JSONArray episodesArray;
                    EpisodeItem[] episodeItems = null;

                    Integer id_1 = -1;
                    Integer season_number = -1;
                    Integer current_season_episodes_number = -1;
                    Integer next_episode_number = -1;
                    String status = "NA";
                    EpisodeItem next_episode = null;

                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_ID)) {
                        id_1 = jsonObject.getInt(ShowDetailKeys.KEY_ID);
                    }
                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_SEASON_NUMBER)) {
                        season_number = jsonObject.getInt(ShowDetailKeys.KEY_SEASON_NUMBER);
                    }
                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_CURRENT_SEASON_EPISODES_NUMBER)) {
                        current_season_episodes_number = jsonObject.getInt(ShowDetailKeys.KEY_CURRENT_SEASON_EPISODES_NUMBER);
                    }
                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_STATUS)) {
                        status = jsonObject.getString(ShowDetailKeys.KEY_STATUS);
                    }

                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_NEXT_EPISODE)) {
                        JSONObject nextEpisodeObject = jsonObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                        if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                            next_episode = new EpisodeItem();

                            if (JsonUtils.contains(nextEpisodeObject, ShowDetailKeys.KEY_AIR_DATE)) {
                                next_episode.setAired(nextEpisodeObject.getBoolean(ShowDetailKeys.KEY_AIRED));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, ShowDetailKeys.KEY_NUMBER)) {
                                next_episode_number = nextEpisodeObject.getInt(ShowDetailKeys.KEY_NUMBER);
                                next_episode.setNumber(nextEpisodeObject.getInt(ShowDetailKeys.KEY_NUMBER));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, ShowDetailKeys.KEY_AIR_DATE)) {
                                next_episode.setAir_date(nextEpisodeObject.getString(ShowDetailKeys.KEY_AIR_DATE));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, ShowDetailKeys.KEY_DAYS_LEFT)) {
                                next_episode.setDays_left(nextEpisodeObject.getInt(ShowDetailKeys.KEY_DAYS_LEFT));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, ShowDetailKeys.KEY_HOURS_LEFT)) {
                                next_episode.setHous_left(nextEpisodeObject.getInt(ShowDetailKeys.KEY_HOURS_LEFT));
                            }
                        }
                    }
                    if (JsonUtils.contains(jsonObject, ShowDetailKeys.KEY_EPISODES)) {
                        episodesArray = jsonObject.getJSONArray(ShowDetailKeys.KEY_EPISODES);
                        if (episodesArray.length() > 0) {
                            episodeItems = new EpisodeItem[episodesArray.length()];
                            for (int jIndex = 0; jIndex < episodesArray.length(); jIndex++) {

                                episodeItems[jIndex] = new EpisodeItem();

                                JSONObject innerObject = episodesArray.getJSONObject(jIndex);
                                Integer id = -1;
                                Integer number = -1;
                                String air_date = "NA";
                                boolean aired = false;
                                Integer days_left = -1;
                                Integer hous_left = -1;

                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_AIRED)) {
                                    aired = innerObject.getBoolean(ShowDetailKeys.KEY_AIRED);
                                }
                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_AIR_DATE)) {
                                    air_date = innerObject.getString(ShowDetailKeys.KEY_AIR_DATE);
                                }
                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_ID)) {
                                    id = innerObject.getInt(ShowDetailKeys.KEY_ID);
                                }
                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_NUMBER)) {
                                    number = innerObject.getInt(ShowDetailKeys.KEY_NUMBER);
                                }
                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_DAYS_LEFT)) {
                                    days_left = innerObject.getInt(ShowDetailKeys.KEY_DAYS_LEFT);
                                }
                                if (JsonUtils.contains(innerObject, ShowDetailKeys.KEY_HOURS_LEFT)) {
                                    hous_left = innerObject.getInt(ShowDetailKeys.KEY_HOURS_LEFT);
                                }
                                episodeItems[jIndex].setId(id);
                                episodeItems[jIndex].setNumber(number);
                                episodeItems[jIndex].setAir_date(air_date);
                                episodeItems[jIndex].setAired(aired);
                                episodeItems[jIndex].setDays_left(days_left);
                                episodeItems[jIndex].setHous_left(hous_left);
                                episodeItems[jIndex].setEnable();
                                episodeItems[jIndex].setDetail_air_date();
                            }
                        }
                        else {
                            if (next_episode != null) {
                                episodeItems = new EpisodeItem[1];
                                for (int jIndex = 0; jIndex < 1; jIndex++) {

                                    episodeItems[jIndex] = new EpisodeItem();

                                    episodeItems[jIndex].setId(next_episode.getId());
                                    episodeItems[jIndex].setNumber(next_episode.getNumber());
                                    episodeItems[jIndex].setAir_date(next_episode.getAir_date());
                                    episodeItems[jIndex].setAired(next_episode.getAired());
                                    episodeItems[jIndex].setDays_left(next_episode.getDays_left());
                                    episodeItems[jIndex].setHous_left(next_episode.getHous_left());
                                    episodeItems[jIndex].setEnable();
                                    episodeItems[jIndex].setDetail_air_date();
                                }
                            }
                        }
                    }

                    model = new ShowDetailItem();
                    model.setNext_episode(next_episode);
                    model.setStatus(status);
                    model.setCurrent_season_episodes_number(current_season_episodes_number);
                    model.setSeason_number(season_number);
                    model.setEpisodes(episodeItems);
                    model.setLastEpisode();
                    model.setNext_episodes_number(next_episode_number);
                    model.setId(id_1);
                    model.setSeason_air_date();
                    model.setSeason_air_date_detailed_months();
                    model.setSeason_air_date_detailed_days();
                    model.setSeason_air_date_detailed_hours();
                    model.setSeason_aired();

                }
            } catch (JSONException je) {
                Log.i(PARSER_SHOWS_ID_TAG, "" + je.getLocalizedMessage());
            }
        }
        return model;
    }

    public static SearchItem parseSearchJSON(JSONObject response) {
        SearchItem searchItem = new SearchItem();
        ArrayList<ShowsItem> list = new ArrayList<>();
        Integer totalCount = 0;
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {

                    if (JsonUtils.contains(response, ShowsItemKeys.KEY_TOTAL))
                        totalCount  = response.getInt(ShowsItemKeys.KEY_TOTAL);
                    JSONArray array = response.getJSONArray(ShowsItemKeys.KEY_SHOWS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            ShowsItem model = new ShowsItem();

                            JSONObject innerObject = array.getJSONObject(jIndex);

                            Integer season_number = -1;
                            String status = "NA";
                            String name_original = "NA";
                            String name = "NA";
                            String poster = "NA";
                            Integer id = -1;

                            EpisodeItem next_episode = null;

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_SEASON_NUMBER)) {
                                season_number = innerObject.getInt(ShowsItemKeys.KEY_SEASON_NUMBER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_STATUS)) {
                                status = innerObject.getString(ShowsItemKeys.KEY_STATUS);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME_ORIGINAL)) {
                                name_original = innerObject.getString(ShowsItemKeys.KEY_NAME_ORIGINAL);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME)) {
                                name = innerObject.getString(ShowsItemKeys.KEY_NAME);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_POSTER)) {
                                poster = innerObject.getString(ShowsItemKeys.KEY_POSTER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_ID)) {
                                id = innerObject.getInt(ShowsItemKeys.KEY_ID);
                            }

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NEXT_EPISODE)) {
                                JSONObject nextEpisodeObject = innerObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                                if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                                    next_episode = new EpisodeItem();
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                        next_episode.setNumber(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                        next_episode.setAir_date(nextEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                        next_episode.setDays_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                        next_episode.setHous_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                                    }
                                }
                            }

                            model.setId(id);
                            model.setNext_episode(next_episode);
                            model.setStatus(status);
                            model.setSeason_number(season_number);
                            model.setName_original(name_original);
                            model.setName(name);
                            model.setPoster(poster);
                            model.setEpisode_air_date();
                            model.setEpisode_air_date_detailed_months();
                            model.setEpisode_air_date_detailed_days();
                            model.setEpisode_air_date_detailed_hours();
                            model.setEpisode_aired();
                            model.setStatus();
                            list.add(model);
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_SEARCH_TAG, "" + je.getLocalizedMessage());
            }
        }
        searchItem.setShowsItems(list);
        searchItem.setTotalCount(totalCount);
        return searchItem;
    }

    public static ArrayList<ShowsItem> parseShowsNewJSON(JSONObject response) {
        ArrayList<ShowsItem> list = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    JSONArray array = response.getJSONArray(ShowsItemKeys.KEY_SHOWS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            ShowsItem model = new ShowsItem();

                            JSONObject innerObject = array.getJSONObject(jIndex);

                            Integer season_number = -1;
                            String status = "NA";
                            String name_original = "NA";
                            String name = "NA";
                            String poster = "NA";
                            Integer id = -1;

                            EpisodeItem next_episode = null;

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_SEASON_NUMBER)) {
                                season_number = innerObject.getInt(ShowsItemKeys.KEY_SEASON_NUMBER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_STATUS)) {
                                status = innerObject.getString(ShowsItemKeys.KEY_STATUS);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME_ORIGINAL)) {
                                name_original = innerObject.getString(ShowsItemKeys.KEY_NAME_ORIGINAL);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME)) {
                                name = innerObject.getString(ShowsItemKeys.KEY_NAME);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_POSTER)) {
                                poster = innerObject.getString(ShowsItemKeys.KEY_POSTER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_ID)) {
                                id = innerObject.getInt(ShowsItemKeys.KEY_ID);
                            }

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NEXT_EPISODE)) {
                                JSONObject nextEpisodeObject = innerObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                                if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                                    next_episode = new EpisodeItem();
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                        next_episode.setNumber(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                        next_episode.setAir_date(nextEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                        next_episode.setDays_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                        next_episode.setHous_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                                    }
                                }
                            }

                            model.setId(id);
                            model.setNext_episode(next_episode);
                            model.setStatus(status);
                            model.setSeason_number(season_number);
                            model.setName_original(name_original);
                            model.setName(name);
                            model.setPoster(poster);
                            model.setEpisode_air_date();
                            model.setEpisode_air_date_detailed_months();
                            model.setEpisode_air_date_detailed_days();
                            model.setEpisode_air_date_detailed_hours();
                            model.setEpisode_aired();
                            list.add(model);
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_SHOWS_NEW_TAG, "" + je.getLocalizedMessage());
            }
        }
        return list;
    }

    public static ArrayList<ShowsItem> parseShowsPopularJSON(JSONObject response) {
        ArrayList<ShowsItem> list = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    JSONArray array = response.getJSONArray(ShowsItemKeys.KEY_SHOWS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            ShowsItem model = new ShowsItem();

                            JSONObject innerObject = array.getJSONObject(jIndex);

                            Integer season_number = -1;
                            String status = "NA";
                            String name_original = "NA";
                            String name = "NA";
                            String poster = "NA";
                            Integer id = -1;

                            EpisodeItem next_episode = null;

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_SEASON_NUMBER)) {
                                season_number = innerObject.getInt(ShowsItemKeys.KEY_SEASON_NUMBER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_STATUS)) {
                                status = innerObject.getString(ShowsItemKeys.KEY_STATUS);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME_ORIGINAL)) {
                                name_original = innerObject.getString(ShowsItemKeys.KEY_NAME_ORIGINAL);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NAME)) {
                                name = innerObject.getString(ShowsItemKeys.KEY_NAME);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_POSTER)) {
                                poster = innerObject.getString(ShowsItemKeys.KEY_POSTER);
                            }
                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_ID)) {
                                id = innerObject.getInt(ShowsItemKeys.KEY_ID);
                            }

                            if (JsonUtils.contains(innerObject, ShowsItemKeys.KEY_NEXT_EPISODE)) {
                                JSONObject nextEpisodeObject = innerObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                                if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                                    next_episode = new EpisodeItem();
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                        next_episode.setNumber(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                        next_episode.setAir_date(nextEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                        next_episode.setDays_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                                    }
                                    if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                        next_episode.setHous_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                                    }
                                }
                            }

                            model.setId(id);
                            model.setNext_episode(next_episode);
                            model.setStatus(status);
                            model.setSeason_number(season_number);
                            model.setName_original(name_original);
                            model.setName(name);
                            model.setPoster(poster);
                            model.setEpisode_air_date();
                            model.setEpisode_air_date_detailed_months();
                            model.setEpisode_air_date_detailed_days();
                            model.setEpisode_air_date_detailed_hours();
                            model.setEpisode_aired();
                            list.add(model);
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_SHOWS_POP_TAG, "" + je.getLocalizedMessage());
            }
        }
        return list;
    }

    public static ShowsItem parseSubsIdJSON(JSONObject jsonObject) {
        ShowsItem model = new ShowsItem();

        if (jsonObject != null && jsonObject.length() > 0) {
            try {
                if (jsonObject.length() > 0) {

                    Integer season_number = -1;
                    String status = "NA";
                    String name_original = "NA";
                    String name = "NA";
                    String poster = "NA";
                    Integer id = -1;

                    EpisodeItem next_episode = null;

                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_SEASON_NUMBER)) {
                        season_number = jsonObject.getInt(ShowsItemKeys.KEY_SEASON_NUMBER);
                    }
                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_STATUS)) {
                        status = jsonObject.getString(ShowsItemKeys.KEY_STATUS);
                    }
                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_NAME_ORIGINAL)) {
                        name_original = jsonObject.getString(ShowsItemKeys.KEY_NAME_ORIGINAL);
                    }
                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_NAME)) {
                        name = jsonObject.getString(ShowsItemKeys.KEY_NAME);
                    }
                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_POSTER)) {
                        poster = jsonObject.getString(ShowsItemKeys.KEY_POSTER);
                    }
                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_ID)) {
                        id = jsonObject.getInt(ShowsItemKeys.KEY_ID);
                    }

                    if (JsonUtils.contains(jsonObject, ShowsItemKeys.KEY_NEXT_EPISODE)) {
                        JSONObject nextEpisodeObject = jsonObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                        if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                            next_episode = new EpisodeItem();
                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                next_episode.setNumber(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                next_episode.setAir_date(nextEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                next_episode.setDays_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                            }
                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                next_episode.setHous_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                            }
                        }
                    }

                    model.setId(id);
                    model.setNext_episode(next_episode);
                    model.setStatus(status);
                    model.setSeason_number(season_number);
                    model.setName_original(name_original);
                    model.setName(name);
                    model.setPoster(poster);
                    model.setEpisode_air_date();
                    model.setEpisode_air_date_detailed_months();
                    model.setEpisode_air_date_detailed_days();
                    model.setEpisode_air_date_detailed_hours();
                    model.setEpisode_aired();
                }
            }catch (JSONException je) {
                Log.i(PARSER_SHOWS_ID_TAG, "" + je.getLocalizedMessage());
            }
        }
        return model;
    }

    public static ArrayList<SubscriptionsItem> parseSubscriptionsJSON(JSONObject response) {
        ArrayList<SubscriptionsItem> list = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    JSONArray array = response.getJSONArray(SubscriptionsItemKeys.KEY_ITEMS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            SubscriptionsItem model = new SubscriptionsItem();
                            ShowItem show = null;
                            JSONObject innerObject = array.getJSONObject(jIndex);

                            Integer id = -1;
                            String subtype = "NA";
                            Integer episodes_interval = 0;

                            EpisodeItem next_notification_episode = null;

                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_SUBTYPE)) {
                                subtype = innerObject.getString(SubscriptionsItemKeys.KEY_SUBTYPE);
                            }
                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_EPISODES_INTERVAL)) {
                                episodes_interval = innerObject.getInt(SubscriptionsItemKeys.KEY_EPISODES_INTERVAL);
                            }
                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_ID)) {
                                id = innerObject.getInt(SubscriptionsItemKeys.KEY_ID);
                            }

                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_NEXT_NOTIFICATION_EPISODE)) {
                                JSONObject nextNotifyEpisodeObject = innerObject.getJSONObject(SubscriptionsItemKeys.KEY_NEXT_NOTIFICATION_EPISODE);
                                if (nextNotifyEpisodeObject != null && nextNotifyEpisodeObject.length() > 0) {
                                    next_notification_episode = new EpisodeItem();

                                    if (JsonUtils.contains(nextNotifyEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                        next_notification_episode.setNumber(nextNotifyEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                                    }
                                    if (JsonUtils.contains(nextNotifyEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                        next_notification_episode.setAir_date(nextNotifyEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                                    }
                                    if (JsonUtils.contains(nextNotifyEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                        next_notification_episode.setDays_left(nextNotifyEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                                    }
                                    if (JsonUtils.contains(nextNotifyEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                        next_notification_episode.setHous_left(nextNotifyEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                                    }
                                }
                            }

                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_SHOW)) {
                                JSONObject showObject = innerObject.getJSONObject(SubscriptionsItemKeys.KEY_SHOW);
                                if (showObject != null && showObject.length() > 0) {

                                    show = new ShowItem();

                                    Integer show_id = showObject.getInt(ShowsItemKeys.KEY_ID);

                                    Integer season_number = -1;
                                    Integer current_season_episodes_number = 1;
                                    String status = "NA";
                                    String name_original = "NA";
                                    String name = "NA";
                                    String poster = "NA";
                                    String subscription_image = "NA";

                                    EpisodeItem next_episode = null;

                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_SEASON_NUMBER)) {
                                        season_number = showObject.getInt(ShowsItemKeys.KEY_SEASON_NUMBER);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_CURRENT_SEASON_EPISODES_NUMBER)) {
                                        current_season_episodes_number = showObject.getInt(ShowsItemKeys.KEY_CURRENT_SEASON_EPISODES_NUMBER);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_STATUS)) {
                                        status = showObject.getString(ShowsItemKeys.KEY_STATUS);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_NAME_ORIGINAL)) {
                                        name_original = showObject.getString(ShowsItemKeys.KEY_NAME_ORIGINAL);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_NAME)) {
                                        name = showObject.getString(ShowsItemKeys.KEY_NAME);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_POSTER)) {
                                        poster = showObject.getString(ShowsItemKeys.KEY_POSTER);
                                    }
                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_SUBSCRIPTION_IMAGE)) {
                                        subscription_image = showObject.getString(ShowsItemKeys.KEY_SUBSCRIPTION_IMAGE);
                                    }

                                    if (JsonUtils.contains(showObject, ShowsItemKeys.KEY_NEXT_EPISODE)) {
                                        JSONObject nextEpisodeObject = showObject.getJSONObject(ShowsItemKeys.KEY_NEXT_EPISODE);
                                        if (nextEpisodeObject != null && nextEpisodeObject.length() > 0) {
                                            next_episode = new EpisodeItem();
                                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_NUMBER)) {
                                                next_episode.setNumber(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_NUMBER));
                                            }
                                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_AIR_DATE)) {
                                                next_episode.setAir_date(nextEpisodeObject.getString(EpisodeItemKeys.KEY_AIR_DATE));
                                            }
                                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_DAYS_LEFT)) {
                                                next_episode.setDays_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_DAYS_LEFT));
                                            }
                                            if (JsonUtils.contains(nextEpisodeObject, EpisodeItemKeys.KEY_HOURS_LEFT)) {
                                                next_episode.setHous_left(nextEpisodeObject.getInt(EpisodeItemKeys.KEY_HOURS_LEFT));
                                            }
                                        }
                                    }

                                    show.setNext_episode(next_episode);
                                    show.setStatus(status);
                                    show.setId(show_id);
                                    show.setSeason_number(season_number);
                                    show.setName_original(name_original);
                                    show.setName(name);
                                    show.setPoster(poster);
                                    show.setSubscription_image(subscription_image);
                                    show.setCurrent_season_episodes_number(current_season_episodes_number);
                                    show.setEpisode_air_date();
                                    show.setEpisode_air_date_detailed_months();
                                    show.setEpisode_air_date_detailed_days();
                                    show.setEpisode_air_date_detailed_hours();
                                    show.setEpisode_aired();
                                }
                            }

                            model.setId(id);
                            model.setSubtype(subtype);
                            model.setEpisodes_interval(episodes_interval);
                            model.setNext_notification_episode(next_notification_episode);
                            model.setShow(show);
                            model.setName();
                            model.setShowSchedule();
                            model.setName_original();
                            model.setStatus();
                            list.add(model);
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_SUBS_TAG, "" + je.getLocalizedMessage());
            }
        }
        return list;
    }

    public static Integer getSubscriptionsCountJSON(JSONObject response) {
        Integer count = 0;
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    JSONArray array = response.getJSONArray(SubscriptionsItemKeys.KEY_ITEMS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        MyApplication.setTotalSubs(lenArray);
                        MyApplication.clearSubsId();
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            JSONObject innerObject = array.getJSONObject(jIndex);

                            if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_SHOW)) {
                                JSONObject showObject = innerObject.getJSONObject(SubscriptionsItemKeys.KEY_SHOW);
                                if (showObject != null && showObject.length() > 0) {

                                    Integer show_id = showObject.getInt(ShowsItemKeys.KEY_ID);

                                    MyApplication.addSubsId(show_id);
                                    count++;
                                }
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_SUBS_TAG, "" + je.getLocalizedMessage());
            }
        }
        return count;
    }

    public static ArrayList<NotificationItem> parseNotificationsJSON(JSONObject response) {
        ArrayList<NotificationItem> list = new ArrayList<>();
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    JSONArray array = response.getJSONArray(NotificationsItemKeys.KEY_SHOWS);

                    int lenArray = array.length();
                    if (lenArray > 0) {
                        for (int jIndex = 0; jIndex < lenArray; jIndex++) {

                            NotificationItem model = new NotificationItem();
                            JSONObject innerObject = array.getJSONObject(jIndex);

                            Integer id = -1;
                            Integer show_id = -1;
                            String message = "NA";
                            String image = "NA";

                            if (JsonUtils.contains(innerObject, NotificationsItemKeys.KEY_MESSAGE)) {
                                message = innerObject.getString(NotificationsItemKeys.KEY_MESSAGE);
                            }
                            if (JsonUtils.contains(innerObject, NotificationsItemKeys.KEY_IMAGE)) {
                                image = innerObject.getString(NotificationsItemKeys.KEY_IMAGE);
                            }
                            if (JsonUtils.contains(innerObject, NotificationsItemKeys.KEY_ID)) {
                                id = innerObject.getInt(NotificationsItemKeys.KEY_ID);
                            }
                            if (JsonUtils.contains(innerObject, NotificationsItemKeys.KEY_SHOW_ID)) {
                                show_id = innerObject.getInt(NotificationsItemKeys.KEY_SHOW_ID);
                            }

                            model.setId(id);
                            model.setShow_id(show_id);
                            model.setImage(image);
                            model.setMessage(message);
                            model.setTitle("Appisode");
                            list.add(model);
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_NOTIF_TAG, "" + je.getLocalizedMessage());
            }
        }
        return list;
    }

    public static String parseCheckConfirmationJSON(JSONObject response) {
        String auth_token = "no_auth_token";
        if (response != null && response.length() > 0) {
            try {
                if (response.length() > 0) {
                    if (JsonUtils.contains(response, "auth_token")) {
                        auth_token = response.getString("auth_token");
                    }

                    if (JsonUtils.contains(response, "subscriptions")) {
                        JSONArray array = response.getJSONArray("subscriptions");
                        int lenArray = array.length();
                        if (lenArray > 0) {
                            MyApplication.setTotalSubs(lenArray);
                            MyApplication.clearSubsId();
                            for (int jIndex = 0; jIndex < lenArray; jIndex++) {
                                JSONObject innerObject = array.getJSONObject(jIndex);
                                if (JsonUtils.contains(innerObject, SubscriptionsItemKeys.KEY_SHOW)) {
                                    JSONObject showObject = innerObject.getJSONObject(SubscriptionsItemKeys.KEY_SHOW);
                                    if (showObject != null && showObject.length() > 0) {

                                        Integer show_id = showObject.getInt(ShowsItemKeys.KEY_ID);

                                        MyApplication.addSubsId(show_id);
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(PARSER_CONFIRMATION_TAG, "" + je.getLocalizedMessage());
            }
        }
        return auth_token;
    }

}