package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.Tags;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class CreatePostActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText body = findViewById(R.id.postBody);
        Tags tags = new Tags();
        Date currentDateTime = new Date();

        Chip filterChip_fashion = findViewById(R.id.chip_fashion);
        Chip filterChip_waste = findViewById(R.id.chip_waste);
        Chip filterChip_oceans = findViewById(R.id.chip_oceans);
        Chip filterChip_rainforest = findViewById(R.id.chip_rainforest);
        Chip filterChip_carbon = findViewById(R.id.chip_carbon);
        Chip filterChip_diet = findViewById(R.id.chip_diet);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference postRef = database.getReference("/posts");
        String key = postRef.child("posts").push().getKey();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

        Button submitBtn = findViewById(R.id.postSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Post post = new Post(body.getText().toString(), currentDateTime, tags, uid);

                postRef.child(key).setValue(post);

                Intent showListPostsActivity = new Intent(getApplicationContext(), ListPostsActivity.class);
                startActivity(showListPostsActivity);
            }
        });
    }

}
