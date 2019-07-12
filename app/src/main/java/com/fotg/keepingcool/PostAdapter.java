package com.fotg.keepingcool;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;

import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.User;
import com.google.android.material.chip.Chip;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;
import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends BaseAdapter {

    ArrayList<Post> posts;
    Context context;

    User user;
    String name;

    public static final String POST_BODY = "com.fotg.keepingcool.BODY";
    public static final String POST_ID = "com.fotg.keepingcool.ID";
    public static final String POST_COMMENT = "com.fotg.keepingcool.COMMENT";


    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference postRef = db.getReference("/posts");
    final DatabaseReference userRef = db.getReference("/users");


    LayoutInflater mInflator;

    @Override
    public int getCount() {
        return posts.size();
    }

    public PostAdapter(Context c, ArrayList<Post> ps) {
        context = c;
        posts = ps;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView ( int position, View convertView, ViewGroup parent){
        View v = mInflator.inflate(R.layout.content_list_post_view, null);

        TextView timeTextView = v.findViewById(R.id.timeView);
        TextView bodyTextView = v.findViewById(R.id.bodyView);
        TextView usernameTextView = v.findViewById(R.id.usernameView);
        TextView likesDisplay = v.findViewById(R.id.upvotesText);
        TextView titleTextView = v.findViewById(R.id.titleTextView);

//        Chip filterChip_fashion = v.findViewById(R.id.chip_fashion);
//        Chip filterChip_waste = v.findViewById(R.id.chip_waste);
//        Chip filterChip_oceans = v.findViewById(R.id.chip_oceans);
//        Chip filterChip_rainforest = v.findViewById(R.id.chip_rainforest);
//        Chip filterChip_carbon = v.findViewById(R.id.chip_carbon);
//        Chip filterChip_diet = v.findViewById(R.id.chip_diet);

        Date time = posts.get(position).getTime();
        String body = posts.get(position).getBody();
        String uid = posts.get(position).getUid();
        String title = posts.get(position).getTitle();
        String postId = posts.get(position).getPostId();
        int numberOfLikes = posts.get(position).getNumberOfLikes();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postsRef = db.getReference("/posts");
        final DatabaseReference postRef = db.getReference("/posts/" + postId + "/tags");

//        postRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                filterChip_fashion.setChecked((boolean) dataSnapshot.child("Fashion").getValue());
//                filterChip_carbon.setChecked((boolean) dataSnapshot.child("Carbon").getValue());
//                filterChip_diet.setChecked((boolean) dataSnapshot.child("Diet").getValue());
//                filterChip_oceans.setChecked((boolean) dataSnapshot.child("Oceans").getValue());
//                filterChip_rainforest.setChecked((boolean) dataSnapshot.child("Rainforest").getValue());
//                filterChip_waste.setChecked((boolean) dataSnapshot.child("Waste").getValue());
//            }
//// use dataSnapshot to get boolean. Then if true, display label, if false do not display label.
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        PrettyTime time_display = new PrettyTime();

        timeTextView.setText(time_display.format(time));
        bodyTextView.setText(body);
        titleTextView.setText(title);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(uid).getValue(User.class);
                name = user.getName();
                usernameTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return v;
    }


    @Override
    public long getItemId ( int position){
        return position;
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);


    }
}
