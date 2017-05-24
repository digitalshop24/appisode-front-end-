package com.example.romanchuk.appisode.json;

import com.example.romanchuk.appisode.models.NotificationItem;
import com.example.romanchuk.appisode.models.SearchItem;
import com.example.romanchuk.appisode.models.ShowDetailItem;
import com.example.romanchuk.appisode.models.ShowsItem;
import com.example.romanchuk.appisode.models.SubscriptionsItem;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class JsonUtils {

    public static ShowDetailItem loadShowsId(int showId) {

        JSONObject response = Requestor.ShowId(showId);
        return Parser.parseShowsIdJSON(response);
    }

    public static SearchItem loadSearch(int page, int per_page, String query) {

        JSONObject response = Requestor.Search(page, per_page, query);
        return Parser.parseSearchJSON(response);
    }

    public static ArrayList<ShowsItem> loadShowsNew(int page, int per_page) {

        JSONObject response = Requestor.ShowsNew(page, per_page);
        return Parser.parseShowsNewJSON(response);
    }

    public static ArrayList<ShowsItem> loadShowsPopular(int page, int per_page) {

        JSONObject response = Requestor.ShowsPopular(page, per_page);
        return Parser.parseShowsPopularJSON(response);
    }

    public static ShowsItem loadSubsId(int showId) {

        JSONObject response = Requestor.ShowId(showId);
        return Parser.parseSubsIdJSON(response);
    }

    public static ArrayList<NotificationItem> loadNotifications() {

        JSONObject response = Requestor.GetNotifications();
        return Parser.parseNotificationsJSON(response);
    }

    public static ArrayList<SubscriptionsItem> loadSubscriptions(int page, int per_page) {

        JSONObject response = Requestor.Subscriptions(page, per_page);
        return Parser.parseSubscriptionsJSON(response);
    }

    public static Integer subscriptionsCount(int page, int per_page) {

        JSONObject response = Requestor.Subscriptions(page, per_page);
        return Parser.getSubscriptionsCountJSON(response);
    }

    public static String CheckConfirmation(String phone_number, String confirmation) {

        JSONObject response = Requestor.UsersCheckConfirmation(phone_number, confirmation);
        return Parser.parseCheckConfirmationJSON(response);
    }
    public static boolean Register(String phone_number) {

        return Requestor.UsersRegister(phone_number);
    }

    public static void Unsubscribe(int subscription_id, int show_id) {

        JSONObject response = Requestor.Unsubscribe(subscription_id, show_id);
    }

    public static void SubscribeEpisode(int show_id, int episode_id) {

        JSONObject response = Requestor.SubscribeEpisode(show_id, episode_id);
    }

    public static void SubscribeSeason(int show_id) {

        JSONObject response = Requestor.SubscribeSeason(show_id);
    }

    public static void MarkAsReadNotification(int notification_id) {

        JSONObject response = Requestor.MarkAsReadNotification(notification_id);
    }

    public static Integer UsersCheckAuth(String auth_token) {

        return Requestor.UsersCheckAuth(auth_token);
    }
    public static boolean DeviceRegister(String push_token) {

        return Requestor.DeviceRegister(push_token);
    }
    public static boolean DeviceUnRegister() {

        return Requestor.DeviceUnRegister();
    }

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key) ? true : false;
    }
}
