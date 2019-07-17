package com.fotg.keepingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BindrActivity extends AppCompatActivity {


    ImageButton bindrMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bindr);

        bindrMap = findViewById(R.id.bindrMap);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.bindr);

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
//                        break;
                    case R.id.profile:
                        Intent profile_intent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profile_intent);
                        break;
                    case R.id.tips:
                        Intent tips_intent = new Intent(getApplicationContext(), DavidsTipsActivity.class);
                        startActivity(tips_intent);
                        break;
                }
                return true;
            }
        });

        bindrMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Uri uri = Uri.parse("https://bindoctor.herokuapp.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
