package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.fotg.keepingcool.models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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
    public static final String POST_UID = "com.fotg.keepingcool.UID";
    public static final String POST_TITLE = "com.fotg.keepingcool.TITLE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_posts);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.news_feed);
        postsView = findViewById(R.id.postsView);

        postList = new ArrayList<Post>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference postRef = database.getReference("/posts");

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
//                    case R.id.calendar:
//                        Intent events_intent = new Intent(getApplicationContext(), EventsActivity.class);
//                        startActivity(events_intent);
//                        break;
//                    case R.id.bindr:
//                        Intent bindr_intent = new Intent(getApplicationContext(), BindrActivity.class);
//                        startActivity(bindr_intent);
//                        break;
                }
                return true;
            }
        });

        postsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent showPostActivity = new Intent(getApplicationContext(), ShowPostActivity.class);
                showPostActivity.putExtra("com.fotg.ITEM_INDEX", i);
                String postID = postList.get(i).getPostId();
                String body = postList.get(i).getBody();

                String uid = postList.get(i).getUid();
                String title = postList.get(i).getTitle();
                showPostActivity.putExtra(POST_UID, uid);
                showPostActivity.putExtra(POST_ID, postID);
                showPostActivity.putExtra(POST_BODY, body);
                showPostActivity.putExtra(POST_TITLE, title);

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
    }

}
