package com.example.romanchuk.appisode.auth;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romanchuk.appisode.MainActivity;
import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.tasks.DeviceRegister;
import com.example.romanchuk.appisode.tools.InternetConnection;
import com.example.romanchuk.appisode.tools.Utils;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.android.gms.gcm.GoogleCloudMessaging;
//import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

public class Step5Activity extends AppCompatActivity implements View.OnClickListener{

    Button btnStep3;
    TextView textSing2_1, textSing2_2, textSing2;
    String push_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_step5);

        btnStep3 = (Button) findViewById(R.id.btnStep3);
        textSing2 = (TextView) findViewById(R.id.textSing2);
        textSing2_1 = (TextView) findViewById(R.id.textSing2_1);
        textSing2_2 = (TextView) findViewById(R.id.textSing2_2);
        btnStep3.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "bebas-neue-bold.ttf");
        btnStep3.setTypeface(custom_font);
        textSing2.setTypeface(custom_font);
        textSing2_1.setTypeface(custom_font);
        textSing2_2.setTypeface(custom_font);
        push_token = Utils.GetPushToken(this);

        if (push_token.equals("no_push_token")) {
            new Thread(new Runnable() {
                public void run() {
                    String senderID = getString(R.string.gcm_defaultSenderId);
                    senderID = "890062303664";
                    try {
                        push_token = FirebaseInstanceId.getInstance().getToken(senderID, FirebaseMessaging.INSTANCE_ID_SCOPE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    InstanceID instanceID = InstanceID.getInstance(getApplicationContext());
//                        String push_token = instanceID.getToken(senderID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
//                    Utils.SavePushToken(getApplicationContext(), push_token);
                }
            }).start();
        }

        if (InternetConnection.checkConnection(this)) {
            new DeviceRegister(this, push_token).execute();
        } else {
            Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = null;
        switch (v.getId()) {
            case R.id.btnStep3:

                myIntent = new Intent(this, MainActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(myIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
