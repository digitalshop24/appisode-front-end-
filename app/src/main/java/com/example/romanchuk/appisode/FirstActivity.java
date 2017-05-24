package com.example.romanchuk.appisode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.widget.Toast;

import com.example.romanchuk.appisode.auth.Step1Activity;
import com.example.romanchuk.appisode.tasks.CheckAuth;
import com.example.romanchuk.appisode.tools.InternetConnection;
import com.example.romanchuk.appisode.tools.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        String auth_token = Utils.GetAuthToken(this);
//        Utils.SavePushToken(this, "cDLsD8k1Z24:APA91bG9YmUGsSkRCLq9dEWdMSKoOZPg0TwOieJK1rX3q4WiF5WmU8gpKR_U6Dur1nZSwjuqqRn1Gr92xu0M1jLrWjSN_hVSCGvsg0ySlKuxOfxK9d_UWOcGrHD-vNGzsrk2Hn2QLRDE");
        String push_token = Utils.GetPushToken(this);
//        push_token = "no_push_token";
        if (push_token.equals("no_push_token")) {
            new Thread(new Runnable() {
                public void run() {
                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                    String senderID = getString(R.string.gcm_defaultSenderId);
                    senderID = "890062303664";
                    Utils.SavePushToken(getApplicationContext(), refreshedToken);
                }
            }).start();
        }
        if (!auth_token.equals("no_auth_token")) {
            if (InternetConnection.checkConnection(this)) {
                new CheckAuth(this, auth_token).execute();
            } else {
                Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        } else {
            Intent myIntent = new Intent(this, Step1Activity.class);
            startActivity(myIntent);
            finish();
        }
    }
}
