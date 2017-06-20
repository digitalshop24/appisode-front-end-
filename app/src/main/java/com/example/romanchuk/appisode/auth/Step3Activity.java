package com.example.romanchuk.appisode.auth;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.tasks.Register;
import com.example.romanchuk.appisode.tools.InternetConnection;

public class Step3Activity extends AppCompatActivity implements View.OnClickListener {
    private GestureDetectorCompat gestureObject;

    Button btnStep3;
    EditText etPhoneNumber;
    TextView textSing2_1, textSing2_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_step3);

        gestureObject = new GestureDetectorCompat(this, new Step3Activity.LearnGesture());

        btnStep3 = (Button) findViewById(R.id.btnStep3);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        textSing2_1 = (TextView) findViewById(R.id.textSing2_1);
        textSing2_2 = (TextView) findViewById(R.id.textSing2_2);
        btnStep3.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "bebas-neue-bold.ttf");

        btnStep3.setTypeface(custom_font);
        etPhoneNumber.setTypeface(custom_font);
        textSing2_1.setTypeface(custom_font);
        textSing2_2.setTypeface(custom_font);
        etPhoneNumber.setHint(getResources().getString(R.string.input_phone).toUpperCase());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStep3:
                if (!TextUtils.isEmpty(etPhoneNumber.getText())) {
                    String phone_number = String.valueOf(etPhoneNumber.getText());

//                    Intent myIntent = new Intent(this, Step4Activity.class);
//                myIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                    startActivity(myIntent);
                    if (InternetConnection.checkConnection(this)) {
                        new Register(this, phone_number).execute();
                    } else {
                        Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(this, getResources().getString(R.string.input_phone), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.show();
                }
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
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if (event2.getX() < event1.getX()) {

            } else if (event2.getX() > event1.getX()) {
                Intent intent = new Intent(Step3Activity.this, Step2Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slideright, R.anim.sliderightout);
//                finish();
            }
            return true;

        }

    }
}
