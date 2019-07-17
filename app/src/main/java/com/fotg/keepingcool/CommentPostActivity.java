package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Comment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentPostActivity extends ToolbarActivity {

    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_UID= "com.fotg.keepingcool.UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_comment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button commentBtn = findViewById(R.id.postComment);
        TextView body = findViewById(R.id.originalPost);
        EditText comment = findViewById(R.id.Comment);

        Intent intent = getIntent();
        String textBody = intent.getStringExtra(PostAdapter.POST_BODY);
        String postId = intent.getStringExtra(PostAdapter.POST_ID);
        String postUid = intent.getStringExtra(ListPostsActivity.POST_UID);

        body.setText(textBody);

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

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myRef = db.getReference("/posts");

                String uid = FirebaseAuth.getInstance().getUid();
                Comment commentObject = new Comment(uid, comment.getText().toString());

                myRef.child(postId).child("comments").push().setValue(commentObject);

//                myRef.child(postId).child("comments").push().setValue(comment.getText().toString());


                    Intent showListPostsActivity = new Intent(getApplicationContext(), ShowPostActivity.class);
                    showListPostsActivity.putExtra(POST_ID, postId);
                showListPostsActivity.putExtra(POST_UID, postUid);
                    startActivity(showListPostsActivity);
            }
        });
    }
}
