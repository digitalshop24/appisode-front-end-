package com.example.romanchuk.appisode.auth;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.romanchuk.appisode.R;

public class Step2Activity extends AppCompatActivity implements View.OnClickListener{

    private GestureDetectorCompat gestureObject;

    Button btnStep3;
    TextView textSing2_1, textSing2_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_step2);

        gestureObject = new  GestureDetectorCompat(this, new Step2Activity.LearnGesture());

        btnStep3 = (Button) findViewById(R.id.btnStep3);
        textSing2_1 = (TextView) findViewById(R.id.textSing2_1);
        textSing2_2 = (TextView) findViewById(R.id.textSing2_2);
        btnStep3.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "bebas-neue-bold.ttf");

        btnStep3.setTypeface(custom_font);
        textSing2_1.setTypeface(custom_font);
        textSing2_2.setTypeface(custom_font);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStep3:
                Intent myIntent = new Intent(this, Step3Activity.class);
//                myIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
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
            Intent intent = new Intent(Step2Activity.this, Step3Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideleft,R.anim.slideleftout);
        }

        else
        if (event2.getX() > event1.getX()){
            Intent intent = new Intent(Step2Activity.this, Step1Activity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slideright,R.anim.sliderightout);

        }
        return true;

    }

}
}

