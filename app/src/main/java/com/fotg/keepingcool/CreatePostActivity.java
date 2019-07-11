package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class CreatePostActivity extends ToolbarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button submitBtn = findViewById(R.id.postSubmit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText body = findViewById(R.id.postBody);
//                TextView comment = findViewById(R.id.Comment);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference postRef = database.getReference("/posts");
                String key = postRef.child("posts").push().getKey();
                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Date currentDateTime = new Date();
                Post post = new Post(body.getText().toString(), currentDateTime, uid);

                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        System.out.println("************");
                        System.out.println("datachange");
                        System.out.println("************");
                        Post post = dataSnapshot.getValue(Post.class);
                        // ...
                        System.out.println(post);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("************");
                        System.out.println(databaseError);
                        System.out.println("************");

//                        // Getting Post failed, log a message
//                        Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };
                postRef.addValueEventListener(postListener);

                postRef.child(key).setValue(post);



                Intent showListPostsActivity = new Intent(getApplicationContext(), ListPostsActivity.class);
                startActivity(showListPostsActivity);
            }
        });
    }

}
