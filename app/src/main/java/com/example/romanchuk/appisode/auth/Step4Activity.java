package com.example.romanchuk.appisode.auth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.romanchuk.appisode.R;
import com.example.romanchuk.appisode.tasks.CheckConfirmation;
import com.example.romanchuk.appisode.tools.InternetConnection;
import com.example.romanchuk.appisode.tools.Utils;

import java.util.concurrent.ExecutionException;

public class Step4Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = Step4Activity.class.getSimpleName();
    public static class SMSMonitor extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(TAG, "перехват смс");
//            Toast toast = Toast.makeText(context, "перехват смс", Toast.LENGTH_LONG);
//            toast.setGravity(Gravity.BOTTOM, 0, 100);
//            toast.show();
            if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                String msgBody = "";
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                if (bundle != null){
                    try{
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for(int i=0; i<msgs.length; i++){
                            msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                            msgBody = msgs[i].getMessageBody();
                        }
                        if (msgBody.length() > 0) {
                            Utils.SaveCodeConfirmation(context, msgBody);
                            String confirmation = Utils.GetCodeConfirmation(context);
                            if (!confirmation.equals("no_confirmation")) {
                                etConfirmation.setText(msgBody);
                            }
                        }
                    }catch(Exception e){
                        Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
    }

    Button btnStep3;
    static EditText etConfirmation;
    TextView textSing2_1, textSing2_2, textSing2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_step4);

        btnStep3 = (Button) findViewById(R.id.btnStep3);
        etConfirmation = (EditText) findViewById(R.id.etPhoneNumber);
        textSing2 = (TextView) findViewById(R.id.textSing2);
        textSing2_1 = (TextView) findViewById(R.id.textSing2_1);
        textSing2_2 = (TextView) findViewById(R.id.textSing2_2);
        btnStep3.setOnClickListener(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "bebas-neue-bold.ttf");
        btnStep3.setTypeface(custom_font);
        etConfirmation.setTypeface(custom_font);
        textSing2.setTypeface(custom_font);
        textSing2_1.setTypeface(custom_font);
        textSing2_2.setTypeface(custom_font);
        etConfirmation.setHint(getResources().getString(R.string.input_code).toUpperCase());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStep3:
//               Utils.SaveAuthToken(this, "YQcSoshbHsLX4lX6eJYWPCXgqrY");
//               Utils.SavePhoneNumber(this, "+375298823743");
//              Utils.SavePushToken(this, "eyF2cD8UY6Y:APA91bG6OPXswi-_S5XwaNrt6w9s9qwnZcTU-_600bbQagCqgNb83g0WgtDoqm7rIKSWHQ97TAO8wjjjqb_3VkoMho769cOd_PhVhWHfmi6Usf5dRevKqXUp_p48xHkkIA5PgAtWHm4N");
                String phone_number = Utils.GetPhoneNumber(this);
                if (!phone_number.equals("no_phone_number")) {

                    if (!TextUtils.isEmpty(etConfirmation.getText())) {
                        String confirmation = String.valueOf(etConfirmation.getText());
//                        Intent myIntent = new Intent(this, Step5Activity.class);
//                myIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
//                        startActivity(myIntent);
                        if (InternetConnection.checkConnection(this)) {
                            try {
                                String result = new CheckConfirmation(this, phone_number, confirmation).execute().get();
                                if (result.equals("no_auth_token")) {
                                    Toast toast = Toast.makeText(this, getResources().getString(R.string.invalid_code), Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                                    toast.show();
                                }
                            } catch (InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.show();
                        }
                    }
                    else
                    {
                        Toast toast = Toast.makeText(this, getResources().getString(R.string.input_code), Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 100);
                        toast.show();
                    }
                }
                break;
            default:
                break;
        }
    }
}
