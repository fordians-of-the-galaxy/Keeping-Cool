package com.fotg.keepingcool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DavidsTipsActivity extends AppCompatActivity {

    Button button2;
    Button button3;
    Button button4;
    Button button5;
    Button button6;
    Button button7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_davids_tips);

        button2 = findViewById(R.id.circleButton2);
        button3 = findViewById(R.id.circleButton3);
        button4 = findViewById(R.id.circleButton4);
        button5 = findViewById(R.id.circleButton5);
        button6= findViewById(R.id.circleButton6);
        button7 = findViewById(R.id.circleButton7);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.tips);

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
                    case R.id.bindr:
                        Intent bindr_intent = new Intent(getApplicationContext(), BindrActivity.class);
                        startActivity(bindr_intent);
                        break;
                }
                return true;
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips2();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips3();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips4();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips5();
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips6();
            }
        });

        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDavidsTips7();
            }
        });
    }


    public void openDavidsTips2() {
        Intent intent = new Intent(this, DavidsTips2Activity.class);
        startActivity(intent);
    }
    public void openDavidsTips3() {
        Intent intent = new Intent(this, DavidsTips3Activity.class);
        startActivity(intent);
    }
    public void openDavidsTips4() {
        Intent intent = new Intent(this, DavidsTips4Activity.class);
        startActivity(intent);
    }
    public void openDavidsTips5() {
        Intent intent = new Intent(this, DavidsTips5Activity.class);
        startActivity(intent);
    }
    public void openDavidsTips6() {
        Intent intent = new Intent(this, DavidsTips6Activity.class);
        startActivity(intent);
    }
    public void openDavidsTips7() {
        Intent intent = new Intent(this, DavidsTips7Activity.class);
        startActivity(intent);
    }


}
