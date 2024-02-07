package com.zebra.duress_button_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zebra.duress_button_sample.R;
import com.zebra.duress_button_sample.databinding.ActivityEmergencyOverlayBinding;

import java.util.Timer;
import java.util.TimerTask;

public class EmergencyOverlayActivity extends AppCompatActivity {

    private ActivityEmergencyOverlayBinding binding;
    String TAG = "EmergencyOverlayActivity";

    static int counter =0;
    Timer timer = new Timer();

    Timer toneTimer = new Timer();

    ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyOverlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("emergency_type");
            binding.overlayButton.setText("ONGOING EMERGENCY! "+value);
        }

        toneTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                tg.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 200);
            }
        }, 0, 1000);

        binding.overlayButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                timer.cancel();
                toneTimer.cancel();
                finish();
                return false;
            }
        });

        setShowWhenLocked(true);
        setTurnScreenOn(true);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                runOnUiThread( () -> {
                    try {
                        if( counter++ % 2 == 0){
                            binding.getRoot().setBackgroundColor(Color.RED);
                            Log.d(TAG, "setBackgroundColor(Color.RED)");
                        }
                        else {
                            binding.getRoot().setBackgroundColor(Color.YELLOW);
                            Log.d(TAG, "setBackgroundColor(Color.YELLOW)");
                        }
                        binding.getRoot().invalidate();
                        Thread.sleep(10);
                    } catch (Exception e) {}

                });
            }
        }, 1000, 500);

    }
}