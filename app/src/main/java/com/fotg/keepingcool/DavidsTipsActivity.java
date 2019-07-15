package com.fotg.keepingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DavidsTipsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davids_tips);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tips);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news_feed:
                        Intent feed_intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                        startActivity(feed_intent);
//                    case R.id.useful_links:
//                        Intent links_intent = new Intent(getApplicationContext(), UsefulLinksActivity.class);
//                        startActivity(links_intent);
//                    case R.id.calendar:
//                        Intent events_intent = new Intent(getApplicationContext(), EventsActivity.class);
//                        startActivity(events_intent);
//                    case R.id.bindr:
//                        Intent bindr_intent = new Intent(getApplicationContext(), BindrActivity.class);
//                        startActivity(bindr_intent);
                }
                return true;
            }
        });

    }
}
