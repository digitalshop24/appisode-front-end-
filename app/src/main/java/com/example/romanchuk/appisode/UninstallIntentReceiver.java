package com.example.romanchuk.appisode;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by romanchuk on 14.03.2017.
 */

public class UninstallIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // fetching package names from extras
        String[] packageNames = intent.getStringArrayExtra("android.intent.extra.PACKAGES");

        if(packageNames!=null){
            for(String packageName: packageNames){
                if(packageName!=null && packageName.equals("com.example.romanchuk.appisode")){
                    // User has selected our application under the Manage Apps settings
                    // now initiating background thread to watch for activity
                    new ListenActivities(context).start();

                }
            }
        }
    }

}
