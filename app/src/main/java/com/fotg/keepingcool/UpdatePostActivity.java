package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
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
//        Tags tags = new Tags();

        Chip filterChip_fashion = findViewById(R.id.chip_fashion);
        Chip filterChip_waste = findViewById(R.id.chip_waste);
        Chip filterChip_oceans = findViewById(R.id.chip_oceans);
        Chip filterChip_rainforest = findViewById(R.id.chip_rainforest);
        Chip filterChip_carbon = findViewById(R.id.chip_carbon);
        Chip filterChip_diet = findViewById(R.id.chip_diet);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postsRef = db.getReference("/posts");
        final DatabaseReference tagsRef = postsRef.child("tags");
        final DatabaseReference fashionRef = tagsRef.child("a");

        postsRef.addValueEventListener(new ValueEventListener() {
//        tagsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                This constructor is making the "tags" object run default constructor. Therefore setting everything to false.
//                Need to set so using other constructor and passing in actual values of the children in the DB on construction.

                Tags tags = dataSnapshot.getValue(Tags.class);
                filterChip_fashion.setChecked(tags.getChip_fashion());
                System.out.println(tags);
                System.out.println("**********************");
                System.out.println(tags.getChip_fashion());
                System.out.println(tags.getChip_carbon());
                System.out.println(tags.getChip_diet());
                System.out.println(tags.getChip_oceans());
                System.out.println(tags.getChip_rainforest());
                System.out.println(tags.getChip_waste());
                System.out.println("**********************");
                System.out.println();
                System.out.println("**********************");
//                This works to set the dispaly. Just need the state from the database to be passed into setchecked function.
                filterChip_fashion.setChecked(true);

//                filterChip_fashion.setChecked(tags.getChip_fashion());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        filterChip_fashion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (isChecked) {
//                    tags.setChip_fashion(true);
//                } else {
//                    tags.setChip_fashion(false);
//                }
//            }
//        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postsRef.child(postId).child("body").setValue(body.getText().toString());
//                postsRef.child(postId).child("tags").setValue(tags);

                Intent showListPostsActivity = new Intent(getApplicationContext(), ListPostsActivity.class);
                startActivity(showListPostsActivity);
            }
        });
    }
}

