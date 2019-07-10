package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Tags;
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

        Intent intent = getIntent();
        String postBody = intent.getStringExtra(PostAdapter.POST_BODY);
        String postId = intent.getStringExtra(PostAdapter.POST_ID);
        body.setText(postBody);
        Tags tags = new Tags();

        Chip filterChip_fashion = findViewById(R.id.chip_fashion);
        Chip filterChip_waste = findViewById(R.id.chip_waste);
        Chip filterChip_oceans = findViewById(R.id.chip_oceans);
        Chip filterChip_rainforest = findViewById(R.id.chip_rainforest);
        Chip filterChip_carbon = findViewById(R.id.chip_carbon);
        Chip filterChip_diet = findViewById(R.id.chip_diet);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postsRef = db.getReference("/posts");
        DatabaseReference tagsRef = postsRef.child("tags");

        DatabaseReference fashionRef = tagsRef.child("a");

        tagsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Tags tags = dataSnapshot.getValue(Tags.class);
                System.out.println(tags);
                System.out.println("***************************");
            }


//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                postList.clear();
//
//                for(DataSnapshot post: dataSnapshot.getChildren()){
//                    Post newPost = post.getValue(Post.class);
//                    newPost.setPostId(post.getKey());
//                    postList.add(newPost);
//                }
//
//                PostAdapter postAdapter = new PostAdapter(ListPostsActivity.this, postList);
//                Collections.reverse(postList);
//
//                postsView.setAdapter(postAdapter);
//            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        System.out.println("*************************************");
//        System.out.println(myRef.child("tags").child("chip_fashion"));

//        filterChip_fashion.setChecked();

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

