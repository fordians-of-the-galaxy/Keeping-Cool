package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;

import com.fotg.keepingcool.models.User;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ShowPostActivity extends AppCompatActivity {

    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_BODY = "com.fotg.keepingcool.BODY";

    //Setting up database
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference postsRef = db.getReference("/posts");
    final DatabaseReference userRef = db.getReference("/users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        //Toolbar view
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Database functions and fetching the body from intent
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String postId = getIntent().getStringExtra(ListPostsActivity.POST_ID);
        String body = getIntent().getStringExtra(ListPostsActivity.POST_BODY);

        //View elements
        ImageButton deleteButton = findViewById(R.id.deleteButton);
        ImageButton editButton = findViewById(R.id.editButton);
        Button commentButton = findViewById(R.id.commentButton);
        ImageButton upvoteButton = findViewById(R.id.upvoteButton);
        TextView upvotesNumber = findViewById(R.id.votesNumberText);
        TextView titleText = findViewById(R.id.titleText);
        TextView bodyText = findViewById(R.id.bodyText);
        TextView userNameText = findViewById(R.id.userNameText);
        TextView timestampText = findViewById(R.id.timestampText);

        //Text views for the category tags
        TextView fashion = findViewById(R.id.fashionText);
        TextView waste = findViewById(R.id.wasteText);
        TextView oceans = findViewById(R.id.oceansText);
        TextView rainforests = findViewById(R.id.rainforestsText);
        TextView carbon = findViewById(R.id.carbonText);
        TextView diet = findViewById(R.id.dietText);

        final DatabaseReference postRef = db.getReference("/posts/" + postId);

        //Display tags if they are stored as true in the database for the post
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                titleText.setText((String) dataSnapshot.child("title").getValue());
                bodyText.setText((String) dataSnapshot.child("body").getValue());

                Long longTimestamp = (Long) dataSnapshot.child("time").child("time").getValue();
                Date postTimestamp = new Date(longTimestamp);
                PrettyTime time_display = new PrettyTime();
                timestampText.setText(time_display.format(postTimestamp));

                if ((boolean) dataSnapshot.child("tags").child("Fashion").getValue()) {
                    fashion.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("tags").child("Carbon").getValue()) {
                    carbon.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("tags").child("Diet").getValue()) {
                    diet.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("tags").child("Oceans").getValue()) {
                    oceans.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("tags").child("Rainforest").getValue()) {
                    rainforests.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("tags").child("Waste").getValue()) {
                    waste.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Fetch user name from database and display it on the post
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user;
                String name;

                user = dataSnapshot.child(uid).getValue(User.class);
                name = user.getName();
                userNameText.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        //User can edit or delete if it is their own post
        if (uid.equals(Authentication.getUID())) {
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deletePost(postId);
                    Intent intent = new Intent(ShowPostActivity.this, ListPostsActivity.class);
                    ShowPostActivity.this.startActivity(intent);
                }
            });

            editButton.setVisibility(View.VISIBLE);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ShowPostActivity.this, UpdatePostActivity.class);
                    intent.putExtra(POST_BODY, body);
                    intent.putExtra(POST_ID, postId);
                    ShowPostActivity.this.startActivity(intent);
                }
            });
        }

        //User is taken to create comment activity
        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowPostActivity.this, CommentPostActivity.class);
                intent.putExtra(POST_BODY, body);
                intent.putExtra(POST_ID, postId);
                ShowPostActivity.this.startActivity(intent);
            }
        });


        //Get list for user id's and store it in an array list
        //Display number of votes
        final DatabaseReference upvotesRef = db.getReference("/posts/" + postId + "/upvotes/");

        Map<String, String> upvotes = new HashMap<>();

        upvotesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upvotes.clear();
                for (DataSnapshot upvote : dataSnapshot.getChildren()) {
                    String vote = upvote.getValue().toString();
                    String key = upvote.getKey();
                    upvotes.put(vote, key);
                }
                upvotesNumber.setText(upvotes.size() + " votes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Method for upvoting
        upvoteButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(!upvotes.containsKey(uid)){
                postRef.child("upvotes").push().setValue(uid);
                } else {
                    String voteId = upvotes.get(uid);
                    upvotesRef.child(voteId).removeValue();

                }

            }
        });
    }

    //Method to delete post
    private void deletePost (String id){
        postsRef.child(id).removeValue();
    }


}
