package com.fotg.keepingcool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class DavidsTipsActivity extends AppCompatActivity {

    ImageView babysealBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davids_tips);

        babysealBackground = findViewById(R.id.babysealBackground);

    }
}
