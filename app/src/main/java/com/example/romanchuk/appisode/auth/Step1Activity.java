package com.example.romanchuk.appisode.auth;

import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.BreakIterator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.support.v4.view.GestureDetectorCompat;
import android.widget.Toast;


import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.tasks.CheckAuth;
import com.example.romanchuk.appisode.tools.InternetConnection;
import com.example.romanchuk.appisode.tools.Utils;

public class Step1Activity extends AppCompatActivity implements View.OnClickListener {

    private GestureDetectorCompat gestureObject;
Button cheatbutton;
    Button btnStep2;
    TextView textSing1_1, textSing1_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_step1);

        gestureObject = new  GestureDetectorCompat(this, new LearnGesture());

        cheatbutton = (Button) findViewById(R.id.cheatbutton);
        btnStep2 = (Button) findViewById(R.id.btnStep2);
        textSing1_1 = (TextView) findViewById(R.id.textSing1_1);
        textSing1_2 = (TextView) findViewById(R.id.textSing1_2);
        btnStep2.setOnClickListener(this);
        cheatbutton.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "bebas-neue-bold.ttf");

        btnStep2.setTypeface(custom_font);
        textSing1_1.setTypeface(custom_font);
        textSing1_2.setTypeface(custom_font);
        String auth_token = Utils.GetAuthToken(this);

        if (!auth_token.equals("no_auth_token")) {
            if (InternetConnection.checkConnection(this)) {
                new CheckAuth(this, auth_token).execute();
            } else {
                Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 100);
                toast.show();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cheatbutton:
                Intent myIntent = new Intent(this, Step2Activity.class);
                startActivity(myIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1,MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() < event1.getX()) {
                Intent myIntent = new Intent(Step1Activity.this, Step2Activity.class);
                finish();
                overridePendingTransition(R.anim.slideleft,R.anim.slideleftout);
                startActivity(myIntent);



            }

            else
            if (event2.getX() > event1.getX()){

            }

            return true;

        }

    }
    }


