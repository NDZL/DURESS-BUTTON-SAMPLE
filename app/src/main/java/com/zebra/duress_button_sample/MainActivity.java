package com.zebra.duress_button_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

/*README
https://developer.android.com/training/articles/direct-boot
* TO RUN AND DEBUG IN WORK PROFILE
* - INSTALLING: EDIT ANDROID STUDIO RUN CONFIGURATION AND SET "INSTALL FOR ALL USERS" FLAG
* - DEBUGGING: MANUALLY START THE BADGED-WORK PROFILE APP, THEN ATTACH DEBUGGER FROM ANDROID STUDIO
* - TO APPLY CHANGES / UPDATING THE APP: ISSUE COMMANDLINE adb uninstall com.ndzl.targetelevator, THEN REINSTALL with android studio
* */


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvOut = findViewById(R.id.tvout);
        tvOut.setText(tvOut.getText()+"\n"+BuildConfig.VERSION_NAME);

/*        //REGISTER  RECEIVER
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.LOCKED_BOOT_COMPLETED");

        filter.addAction("com.ndzl.DW");
        filter.addCategory("android.intent.category.DEFAULT");



        registerReceiver(new IntentsReceiver(), filter);

 */

/*
        //FOR AUDIO CAPTURE TEST
        Intent fgsi = new Intent(getApplicationContext(), BA_FGS.class);
        try {
            getApplicationContext().startForegroundService(fgsi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
*/

    }

}