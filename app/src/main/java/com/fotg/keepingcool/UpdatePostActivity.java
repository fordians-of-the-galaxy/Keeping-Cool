package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.Tags;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdatePostActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button updateBtn = findViewById(R.id.postUpdate);
        EditText body = findViewById(R.id.updateBody);
        EditText title = findViewById(R.id.updateTitle);
        Intent intent = getIntent();
        String postTitle = intent.getStringExtra(PostAdapter.POST_TITLE);
        String postBody = intent.getStringExtra(PostAdapter.POST_BODY);
        String postId = intent.getStringExtra(PostAdapter.POST_ID);
        body.setText(postBody);
        title.setText(postTitle);
        Tags tags = new Tags();

        Chip filterChip_fashion = findViewById(R.id.chip_fashion);
        Chip filterChip_waste = findViewById(R.id.chip_waste);
        Chip filterChip_oceans = findViewById(R.id.chip_oceans);
        Chip filterChip_rainforest = findViewById(R.id.chip_rainforest);
        Chip filterChip_carbon = findViewById(R.id.chip_carbon);
        Chip filterChip_diet = findViewById(R.id.chip_diet);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postsRef = db.getReference("/posts");
        final DatabaseReference postRef = db.getReference("/posts/" + postId + "/tags");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.news_feed);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tips:
                        Intent tips_intent = new Intent(getApplicationContext(), DavidsTipsActivity.class);
                        startActivity(tips_intent);
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

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filterChip_fashion.setChecked((boolean) dataSnapshot.child("Fashion").getValue());
                filterChip_carbon.setChecked((boolean) dataSnapshot.child("Carbon").getValue());
                filterChip_diet.setChecked((boolean) dataSnapshot.child("Diet").getValue());
                filterChip_oceans.setChecked((boolean) dataSnapshot.child("Oceans").getValue());
                filterChip_rainforest.setChecked((boolean) dataSnapshot.child("Rainforest").getValue());
                filterChip_waste.setChecked((boolean) dataSnapshot.child("Waste").getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        filterChip_fashion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_fashion(true);
                } else {
                    tags.setChip_fashion(false);
                }
            }
        });

        filterChip_waste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_waste(true);
                } else {
                    tags.setChip_waste(false);
                }
            }
        });

        filterChip_oceans.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_oceans(true);
                } else {
                    tags.setChip_oceans(false);
                }
            }
        });


        filterChip_rainforest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_rainforest(true);
                } else {
                    tags.setChip_rainforest(false);
                }
            }
        });

        filterChip_carbon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_carbon(true);
                } else {
                    tags.setChip_carbon(false);
                }
            }
        });


        filterChip_diet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tags.setChip_diet(true);
                } else {
                    tags.setChip_diet(false);
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postsRef.child(postId).child("body").setValue(body.getText().toString());
                postsRef.child(postId).child("tags").setValue(tags);

                Intent showListPostsActivity = new Intent(getApplicationContext(), ListPostsActivity.class);
                startActivity(showListPostsActivity);
            }
        });
    }
}

