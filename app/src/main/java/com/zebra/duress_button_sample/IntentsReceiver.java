package com.zebra.duress_button_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.util.Date;

public class IntentsReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(final Context context, Intent intent) {

        //https://developer.android.com/guide/components/broadcast-exceptions
        if (intent != null && intent.getAction().equals("android.intent.action.LOCKED_BOOT_COMPLETED")) {

        }

        //https://developer.android.com/training/articles/direct-boot#notification
        if (intent != null && intent.getAction().equals("android.intent.action.USER_UNLOCKED")) {
            Toast.makeText(context.getApplicationContext(), "USER_UNLOCKED", Toast.LENGTH_LONG).show();
        }

        //BOOT_COMPLETED IS MANIFEST-DECLARED
        if (intent != null && intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            Toast.makeText(context.getApplicationContext(), "BOOT COMPLETED!", Toast.LENGTH_LONG).show();
        }

    }

    public void showContexedToast(Context context, String message) {
        final String msg = message;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText( context, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}