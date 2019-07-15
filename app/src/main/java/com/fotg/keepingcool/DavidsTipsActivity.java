package com.fotg.keepingcool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class DavidsTipsActivity extends AppCompatActivity {

    ImageView babysealBackground;
    Animation babysealAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davids_tips);

        babysealAnim = AnimationUtils.loadAnimation(this, R.anim.babyseal_anim);

        babysealBackground = findViewById(R.id.babysealBackground);

        babysealBackground.animate().translationY(-430).setDuration(0);
    }
}
