package com.zebra.duress_button_sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.zebra.duress_button_sample.R;
import com.zebra.duress_button_sample.databinding.ActivityEmergencyOverlayBinding;

import java.util.Timer;
import java.util.TimerTask;

public class EmergencyOverlayActivity extends AppCompatActivity {

    private ActivityEmergencyOverlayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmergencyOverlayBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.overlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        setShowWhenLocked(true);
        setTurnScreenOn(true);

        Handler  mHandler = new Handler();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mHandler.post(() -> {
                    if(  ((ColorDrawable)binding.getRoot().getBackground()).getColor() != Color.RED)
                        binding.getRoot().setBackgroundColor(Color.RED);
                    else
                        binding.getRoot().setBackgroundColor(Color.YELLOW);

                });
            }
        }, 1000, 500);

    }
}