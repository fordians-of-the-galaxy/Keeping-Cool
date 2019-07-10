package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CommentPostActivity extends ToolbarActivity {

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
        String postComment = intent.getStringExtra(PostAdapter.POST_COMMENT);
        String postId = intent.getStringExtra(PostAdapter.POST_ID);
        comment.setText(postComment);
        body.setText(textBody);



        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myRef = db.getReference("/posts");


                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot post: dataSnapshot.getChildren()){

                            if(post.child(postId).hasChild("comments")) {

                                myRef.child(postId).child("comment2").setValue(comment.getText().toString());

                            } else {

                                myRef.child(postId).child("comments").setValue(comment.getText().toString());

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                    Intent showListPostsActivity = new Intent(getApplicationContext(), ListPostsActivity.class);
                    startActivity(showListPostsActivity);

            }
        });
    }
}
