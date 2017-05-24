package com.example.romanchuk.appisode.json;

import android.net.Uri;
import android.util.Log;

import com.example.romanchuk.appisode.tools.ApiPath;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by romanchuk on 21.01.2017.
 */

public class Requestor {
    public static final String SHOWID_TAG = "REQUESTOR_SHOWID_TAG";
    public static final String SHOWSNEW_TAG = "REQUESTOR_SHOWSNEW_TAG";
    public static final String GETNOTIFICATIONS_TAG = "GETNOTIFICATIONS_TAG";
    public static final String SHOWSPOP_TAG = "REQUESTOR_SHOWSPOP_TAG";
    public static final String SUBS_TAG = "REQUESTOR_SUBS_TAG";
    public static final String CHECKAUTH_TAG = "REQUESTOR_CHECKAUTH_TAG";
    public static final String CONFIRM_TAG = "REQUESTOR_CONFIRM_TAG";
    public static final String SUBSSEASON_TAG = "SUBSSEASON_TAG";
    public static final String READ_NOTIF_TAG = "READ_NOTIF_TAG";
    public static final String SUBSEPISODE_TAG = "SUBSEPISODE_TAG";
    public static final String UNSUBSCRIBE_TAG = "UNSUBSCRIBE_TAG";
    public static final String REGISTER_TAG = "REGISTER_TAG";
    public static final String REGISTER_DEVICE_TAG = "REGISTER_DEVICE_TAG";
    public static final String UNREGISTER_DEVICE_TAG = "UNREGISTER_DEVICE_TAG";

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        Requestor.token = token;
    }

    public static String token = "no_auth_token";

    public static JSONObject ShowId(Integer id) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            Request request = new Request.Builder()
                    .url(ApiPath.ShowsId + id)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SHOWID_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject ShowsNew(int page, int per_page) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("shows")
                    .appendPath("new")
                    .appendQueryParameter("page", String.valueOf(page))
                    .appendQueryParameter("per_page", String.valueOf(per_page));
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SHOWSNEW_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject Search(int page, int per_page, String query) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("shows")
                    .appendPath("search")
                    .appendQueryParameter("query", String.valueOf(query))
                    .appendQueryParameter("page", String.valueOf(page))
                    .appendQueryParameter("per_page", String.valueOf(per_page));
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SHOWSNEW_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject ShowsPopular(int page, int per_page) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("shows")
                    .appendPath("popular")
                    .appendQueryParameter("page", String.valueOf(page))
                    .appendQueryParameter("per_page", String.valueOf(per_page));
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SHOWSPOP_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject Subscriptions(int page, int per_page) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("subscriptions")
                    .appendQueryParameter("page", String.valueOf(page))
                    .appendQueryParameter("per_page", String.valueOf(per_page));
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SUBS_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static int UsersCheckAuth(String auth_token) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(ApiPath.UsersCheckAuth)
                    .addHeader("Auth-token", auth_token)
                    .build();
            Response response = client.newCall(request).execute();
            return response.code();

        } catch (IOException e) {
            Log.e(CHECKAUTH_TAG, "" + e.getLocalizedMessage());
        }
        return -1;
    }

    public static JSONObject UsersCheckConfirmation(String phone_number, String confirmation) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.UsersCheckConfirmation).newBuilder();

            urlBuilder.addQueryParameter("phone", phone_number);
            urlBuilder.addQueryParameter("confirmation", confirmation);

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(CONFIRM_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject SubscribeSeason(int show_id) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.Subscribe).newBuilder();

            urlBuilder.addQueryParameter("show_id", String.valueOf(show_id));
            urlBuilder.addQueryParameter("subtype", "season");

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SUBSSEASON_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject SubscribeEpisode(int show_id, int episode_id) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.Subscribe).newBuilder();

            urlBuilder.addQueryParameter("show_id", String.valueOf(show_id));
            urlBuilder.addQueryParameter("episode_id", String.valueOf(episode_id));
            urlBuilder.addQueryParameter("subtype", "episode");

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SUBSEPISODE_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject Unsubscribe(int subscription_id, int show_id) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.Unsubscribe).newBuilder();

            urlBuilder.addQueryParameter("subscription_id", String.valueOf(subscription_id));
            urlBuilder.addQueryParameter("show_id", String.valueOf(show_id));

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(UNSUBSCRIBE_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static boolean UsersRegister(String phone_number) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.UsersRegister).newBuilder();

            urlBuilder.addQueryParameter("phone", phone_number);

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.code() == 201;
        } catch (IOException e) {
            Log.e(REGISTER_TAG, "" + e.getLocalizedMessage());
        }
        return false;
    }

    public static boolean DeviceRegister(String push_token) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.UsersDevice).newBuilder();
            urlBuilder.addQueryParameter("token", String.valueOf(push_token));

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 201)
                return true;

        } catch (IOException e) {
            Log.e(REGISTER_DEVICE_TAG, "" + e.getLocalizedMessage());
        }
        return false;
    }

    public static boolean DeviceUnRegister() {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.Unsubscribe).newBuilder();

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .delete(body)
                    .build();
            Response response = client.newCall(request).execute();
            if (response.code() == 200)
                return true;

        } catch (IOException e) {
            Log.e(UNREGISTER_DEVICE_TAG, "" + e.getLocalizedMessage());
        }
        return false;
    }

    public static JSONObject GetNotifications() {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("notifications");
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(GETNOTIFICATIONS_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

    public static JSONObject MarkAsReadNotification(int notification_id) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();

            HttpUrl.Builder urlBuilder = HttpUrl.parse(ApiPath.Notifications).newBuilder();

            urlBuilder.addQueryParameter("notification_id", String.valueOf(notification_id));

            String sUrl = urlBuilder.build().toString();

            JSONObject jsonObject = new JSONObject();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());

            Request request = new Request.Builder()
                    .url(sUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(READ_NOTIF_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }
    public static JSONObject testPush(String pushToken, String message, String push_type) {
        try {
            OkHttpClient client = new OkHttpClient.Builder()
                    .readTimeout(55, TimeUnit.SECONDS)//timeout
                    .writeTimeout(55, TimeUnit.SECONDS)//timeout
                    .build();
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.appisode.ru")
                    .appendPath("api")
                    .appendPath("v1")
                    .appendPath("users")
                    .appendPath("test_push")
                    .appendQueryParameter("token", pushToken)
                    .appendQueryParameter("message", message)
                    .appendQueryParameter("push_type", push_type);
            String myUrl = builder.build().toString();

            Request request = new Request.Builder()
                    .url(myUrl)
                    .addHeader("Auth-token", token)
                    .addHeader("Accept-Language", "ru-ru,ru;q=0.8,en-us;q=0.5,en;q=0.3")
                    .build();
            Response response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (IOException | JSONException e) {
            Log.e(SHOWSPOP_TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }

}
