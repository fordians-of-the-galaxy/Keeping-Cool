package com.fotg.keepingcool;

import android.content.Intent;
import android.os.Bundle;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ShowPostActivity extends AppCompatActivity {

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
        Button commentButton = findViewById(R.id.commentButton);
        TextView titleText = findViewById(R.id.titleText);
        TextView bodyText = findViewById(R.id.bodyText);
        //TextView userNameText = findViewById(R.id.userNameText);
        //TextView timestampText = findViewById(R.id.timestampText);


        Chip filterChip_fashion = findViewById(R.id.chip_fashion);
        Chip filterChip_waste = findViewById(R.id.chip_waste);
        Chip filterChip_oceans = findViewById(R.id.chip_oceans);
        Chip filterChip_rainforest = findViewById(R.id.chip_rainforest);
        Chip filterChip_carbon = findViewById(R.id.chip_carbon);
        Chip filterChip_diet = findViewById(R.id.chip_diet);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postRef = db.getReference("/posts/" + postId);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                filterChip_fashion.setChecked((boolean) dataSnapshot.child("tags").child("Fashion").getValue());
                filterChip_carbon.setChecked((boolean) dataSnapshot.child("tags").child("Carbon").getValue());
                filterChip_diet.setChecked((boolean) dataSnapshot.child("tags").child("Diet").getValue());
                filterChip_oceans.setChecked((boolean) dataSnapshot.child("tags").child("Oceans").getValue());
                filterChip_rainforest.setChecked((boolean) dataSnapshot.child("tags").child("Rainforest").getValue());
                filterChip_waste.setChecked((boolean) dataSnapshot.child("tags").child("Waste").getValue());

                titleText.setText((String) dataSnapshot.child("title").getValue());
                bodyText.setText((String) dataSnapshot.child("body").getValue());

//                Date time_stamp = null;
//                for (DataSnapshot postSnapshot: dataSnapshot.child("time").getChildren()) {
//                    time_stamp = postSnapshot.getValue(Date.class);
//                }
//                System.out.println("*************");
//                System.out.println(time_stamp);
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
    private void deletePost (String id){
        postRef.child(id).removeValue();
    }


}
