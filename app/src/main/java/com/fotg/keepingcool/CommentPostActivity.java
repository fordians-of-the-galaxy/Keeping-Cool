package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Comment;
import com.google.firebase.auth.FirebaseAuth;
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
        TextView commentPost = findViewById(R.id.commentBox);

        Intent intent = getIntent();
        String textBody = intent.getStringExtra(PostAdapter.POST_BODY);
        String postId = intent.getStringExtra(PostAdapter.POST_ID);

        body.setText(textBody);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference myRef = db.getReference("/posts");

                String uid = FirebaseAuth.getInstance().getUid();
                Comment commentObject = new Comment(uid, comment.getText().toString());

                myRef.child(postId).child("comments").push().setValue(commentObject);

//                myRef.child(postId).child("comments").push().setValue(comment.getText().toString());

//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        commentPost.setText((String) dataSnapshot.child("comments").child("comment").getValue());
//
//                    }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

                    Intent showListPostsActivity = new Intent(getApplicationContext(), ShowPostActivity.class);
                    startActivity(showListPostsActivity);


            }
        });
    }
}
