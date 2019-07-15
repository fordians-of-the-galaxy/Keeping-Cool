package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;

import com.fotg.keepingcool.models.Comment;
import com.fotg.keepingcool.models.Post;
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
import android.widget.ListView;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

public class ShowPostActivity extends AppCompatActivity {

    ListView commentsView;
    ArrayList<Comment> commentList;
    ArrayList<Post> postList;
    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_BODY = "com.fotg.keepingcool.BODY";


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

//        String commentId = getIntent().getStringExtra(CommentPostActivity.POST_COMMENTID);


        Button commentButton = findViewById(R.id.commentButton);
        TextView titleText = findViewById(R.id.titleText);
        TextView bodyText = findViewById(R.id.bodyText);
        TextView userNameText = findViewById(R.id.userNameText);
        TextView timestampText = findViewById(R.id.timestampText);

        commentsView = findViewById(R.id.comments);

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
        final DatabaseReference commentRef = db.getReference("/posts/" + postId + "/comments/");


        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                titleText.setText((String) dataSnapshot.child("title").getValue());
                bodyText.setText((String) dataSnapshot.child("body").getValue());

//                 commentPost.setText((String) dataSnapshot.child("comments").child("-LjgbLY09DygJrTi0Uex").child("comment").getValue());
//                commentPost.setText((String) dataSnapshot.child("comments").child("-Ljgdc5zueKhv7_VdcQW").child("comment").getValue());


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
//                Collections.reverse(commentList);

                        commentsView.setAdapter(commentAdapter);


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

            private void deletePost(String id) {
                postRef.child(id).removeValue();
            }


        }