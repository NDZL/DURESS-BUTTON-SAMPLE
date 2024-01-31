package com.zebra.duress_button_sample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
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

        binding.overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
                finish();
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