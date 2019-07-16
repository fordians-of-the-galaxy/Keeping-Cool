package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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
    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_BODY = "com.fotg.keepingcool.BODY";
    //Button tipsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_posts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        postsView = findViewById(R.id.postsView);
        //tipsButton = findViewById(R.id.tipsButton);

        postList = new ArrayList<Post>();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("/posts");

        postsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent showPostActivity = new Intent(getApplicationContext(), ShowPostActivity.class);
                showPostActivity.putExtra("com.fotg.ITEM_INDEX", i);
                String postID = postList.get(i).getPostId();
                String body = postList.get(i).getBody();
                showPostActivity.putExtra(POST_ID, postID);
                showPostActivity.putExtra(POST_BODY, body);
                startActivity(showPostActivity);
            }
        });

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

//        tipsButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                openDavidsTips();
//            }
//        });
    }

//    public void openDavidsTips() {
//        Intent intent = new Intent(this, DavidsTipsActivity.class);
//        startActivity(intent);
//    }
}
