package com.fotg.keepingcool;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import com.fotg.keepingcool.models.Comment;
import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import org.ocpsoft.prettytime.PrettyTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



public class ShowPostActivity extends AppCompatActivity {

    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_BODY = "com.fotg.keepingcool.BODY";
    public static final String POST_UID = "com.fotg.keepingcool.UID";
    public static final String POST_TITLE = "com.fotg.keepingcool.TITLE";

    LayoutInflater mInflator;
    ArrayList<Comment> commentList;
    User user;


    //Setting up database
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference postsRef = db.getReference("/posts");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);

        //Toolbar view
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //Database functions and fetching the body from intent

        String postId = checkPostId();
        String uid = checkUID();

        String body = getIntent().getStringExtra(ListPostsActivity.POST_BODY);
        String title = getIntent().getStringExtra(ListPostsActivity.POST_TITLE);


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

        ListView commentView = findViewById(R.id.commentBox);

        //Text views for the category tags
        TextView fashion = findViewById(R.id.fashionText);
        TextView waste = findViewById(R.id.wasteText);
        TextView oceans = findViewById(R.id.oceansText);
        TextView rainforests = findViewById(R.id.rainforestsText);
        TextView carbon = findViewById(R.id.carbonText);
        TextView diet = findViewById(R.id.dietText);


        commentList = new ArrayList<Comment>();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = db.getReference("/users");
        final DatabaseReference postRef = db.getReference("/posts/" + postId);
        final DatabaseReference commentRef = db.getReference("/posts/" + postId + "/comments");

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.news_feed);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tips:
                        Intent tips_intent = new Intent(getApplicationContext(), DavidsTipsActivity.class);
                        startActivity(tips_intent);
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

        //Display tags if they are stored as true in the database for the post
        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mInflator = (LayoutInflater) ShowPostActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
                String name;

                user = dataSnapshot.child(uid).getValue(User.class);
                name = user.getName();
                userNameText.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


       commentRef.addValueEventListener(new ValueEventListener() {

           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               commentList.clear();

               for (DataSnapshot comment : dataSnapshot.getChildren()) {
                   Comment newComment = comment.getValue(Comment.class);
                   newComment.setCommentId(comment.getKey());
                   commentList.add(newComment);
               }

               CommentAdapter commentAdapter = new CommentAdapter(ShowPostActivity.this, commentList);
               System.out.println(commentList);
               commentView.setAdapter(commentAdapter);
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
                    intent.putExtra(POST_TITLE, title);
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
                intent.putExtra(POST_UID, uid);
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
                if(upvotes.containsKey(uid)) {
                    upvoteButton.setColorFilter(Color.parseColor("#00c2c7"));
                } else {
                    upvoteButton.setColorFilter(Color.BLACK);
                }if(upvotes.size() == 1) {
                    upvotesNumber.setText(upvotes.size() + " vote");
                } else {
                    upvotesNumber.setText(upvotes.size() + " votes");
                }

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

    private String checkPostId() {
        if(getIntent().getStringExtra(ListPostsActivity.POST_ID) != null) {
            return getIntent().getStringExtra(ListPostsActivity.POST_ID);
        } else {
            return getIntent().getStringExtra(CommentPostActivity.POST_ID);
        }
    }

    private String checkUID() {
        if(getIntent().getStringExtra(ListPostsActivity.POST_UID) != null) {
            return getIntent().getStringExtra(ListPostsActivity.POST_UID);
        } else {
            return getIntent().getStringExtra(CommentPostActivity.POST_UID);
        }
    }

}
