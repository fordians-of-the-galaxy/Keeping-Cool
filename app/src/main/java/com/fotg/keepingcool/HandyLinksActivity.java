package com.fotg.keepingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HandyLinksActivity extends AppCompatActivity {

    ImageView oxfam;
    ImageView veganKind;
    ImageView extinction;
    ImageView wwf;
    ImageView greenpeace;
    ImageView ourplanet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handy_links);


        oxfam = findViewById(R.id.oxfam);
        veganKind = findViewById(R.id.vegankind);
        extinction = findViewById(R.id.extinction);
        wwf = findViewById(R.id.wwf);
        greenpeace = findViewById(R.id.greenpeace);
        ourplanet = findViewById(R.id.ourplanet);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.useful_links);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.news_feed:
                        Intent feed_intent = new Intent(getApplicationContext(), ListPostsActivity.class);
                        startActivity(feed_intent);
                        break;
//                    case R.id.useful_links:
//                        Intent links_intent = new Intent(getApplicationContext(), UsefulLinksActivity.class);
//                        startActivity(links_intent);
//                    case R.id.calendar:
//                        Intent events_intent = new Intent(getApplicationContext(), EventsActivity.class);
//                        startActivity(events_intent);
                    case R.id.bindr:
                        Intent bindr_intent = new Intent(getApplicationContext(), BindrActivity.class);
                        startActivity(bindr_intent);
                    break;
                    case R.id.tips:
                        Intent tips_intent = new Intent(getApplicationContext(), DavidsTipsActivity.class);
                        startActivity(tips_intent);
                        break;
                }
                return true;
            }
        });

        oxfam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.oxfam.org.uk/what-we-do/issues-we-work-on/climate-change");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        veganKind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.thevegankind.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        extinction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://rebellion.earth//");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        wwf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.wwf.org.uk//");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        greenpeace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.greenpeace.org.uk//");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        ourplanet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://www.ourplanet.com/en/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }
}
