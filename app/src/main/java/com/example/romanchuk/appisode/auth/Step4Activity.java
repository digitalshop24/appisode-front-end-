package com.example.romanchuk.appisode.auth;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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


public class Step4Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int PERMISSION_REQUEST_CODE = 1;

    private static final String TAG = Step4Activity.class.getSimpleName();

    public class SMSMonitor extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e(TAG, "перехват смс");

            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                String msgBody = "";
                Bundle bundle = intent.getExtras();
                SmsMessage[] msgs = null;
                if (bundle != null) {
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            msgBody = msgs[i].getMessageBody();
                        }
                        if (msgBody.length() > 0) {
                            Utils.SaveCodeConfirmation(context, msgBody);
                            String confirmation = Utils.GetCodeConfirmation(context);
                            if (!confirmation.equals("no_confirmation")) {
                                etConfirmation.setText(msgBody);
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Exception caught", e.getMessage());
                    }
                }
            }
        }
    }

    Button btnStep3;
    static EditText etConfirmation;
    TextView textSing2_1, textSing2_2, textSing2;

    private void registerSmsListener() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        /* filter.setPriority(999); This is optional. */
        SMSMonitor receiver = new SMSMonitor();
        registerReceiver(receiver, filter);
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant = ActivityCompat.checkSelfPermission(this, permission);
        if ( grant != PackageManager.PERMISSION_GRANTED) {
        }
        String[] permission_list = new String[1];
        permission_list[0] = permission;
        ActivityCompat.requestPermissions(this, permission_list, 1);
        Toast toast = Toast.makeText(this, "разрешение на перехват", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 100);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_step4);
        registerSmsListener();
        requestSmsPermission();

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
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStep3:

                String phone_number = Utils.GetPhoneNumber(this);
                if (!phone_number.equals("no_phone_number")) {

                    if (!TextUtils.isEmpty(etConfirmation.getText())) {
                        String confirmation = String.valueOf(etConfirmation.getText());

                        if (InternetConnection.checkConnection(this)) {
//                            try {
                            new CheckConfirmation(this, phone_number, confirmation).execute();
//                                String result = new CheckConfirmation(this, phone_number, confirmation).execute().get();
//                                if (result.equals("no_auth_token")) {
//                                    Toast toast = Toast.makeText(this, getResources().getString(R.string.invalid_code), Toast.LENGTH_LONG);
//                                    toast.setGravity(Gravity.BOTTOM, 0, 100);
//                                    toast.show();
//                                }
//                            } catch (InterruptedException | ExecutionException e) {
//                                e.printStackTrace();
//                            }

                        } else {
                            Toast toast = Toast.makeText(this, getResources().getString(R.string.no_connection), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.BOTTOM, 0, 100);
                            toast.show();
                        }
                    } else {
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
