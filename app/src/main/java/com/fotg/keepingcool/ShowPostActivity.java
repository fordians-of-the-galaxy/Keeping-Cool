package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;

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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.Date;

public class ShowPostActivity extends AppCompatActivity {

    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_BODY = "com.fotg.keepingcool.BODY";
    public static final String POST_TITLE = "com.fotg.keepingcool.TITLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton deleteButton = findViewById(R.id.deleteButton);
        ImageButton editButton = findViewById(R.id.editButton);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String postId = getIntent().getStringExtra(ListPostsActivity.POST_ID);
        String body = getIntent().getStringExtra(ListPostsActivity.POST_BODY);
        String title = getIntent().getStringExtra(ListPostsActivity.POST_TITLE);
        Button commentButton = findViewById(R.id.commentButton);
        TextView titleText = findViewById(R.id.titleText);
        TextView bodyText = findViewById(R.id.bodyText);
        TextView userNameText = findViewById(R.id.userNameText);
        TextView timestampText = findViewById(R.id.timestampText);

        TextView fashion = findViewById(R.id.fashionText);
        TextView waste = findViewById(R.id.wasteText);
        TextView oceans = findViewById(R.id.oceansText);
        TextView rainforests = findViewById(R.id.rainforestsText);
        TextView carbon = findViewById(R.id.carbonText);
        TextView diet = findViewById(R.id.dietText);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = db.getReference("/users");
        final DatabaseReference postRef = db.getReference("/posts/" + postId);

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

        commentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowPostActivity.this, CommentPostActivity.class);
//                intent.putExtra(POST_COMMENT, comment);
                intent.putExtra(POST_BODY, body);
                intent.putExtra(POST_ID, postId);
                ShowPostActivity.this.startActivity(intent);
            }
        });

        //        likeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                postRef.child(postId).child("numberOfLikes").setValue(numberOfLikes + 1);
//            }
//        });

//        likesDisplay.setText(numberOfLikes + " people like this");
    }

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference postRef = database.getReference("/posts");
    private void deletePost (String id){
        postRef.child(id).removeValue();
    }


}
