package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class ListPostsActivity extends ToolbarActivity {

    ListView postsView;
    ArrayList<Post> postList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_posts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postsView = findViewById(R.id.postsView);

        postList = new ArrayList<Post>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("/posts");

        postRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                postList.clear();

                for(DataSnapshot post: dataSnapshot.getChildren()){
                    Post newPost = post.getValue(Post.class);
                    newPost.setPostId(post.getKey());
                    postList.add(newPost);
                }

                PostAdapter postAdapter = new PostAdapter(ListPostsActivity.this, postList);
                Collections.reverse(postList);

                postsView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        FloatingActionButton createPostFAB = findViewById(R.id.fab);
        createPostFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
                startActivity(intent);
            }
        });
    }
}
