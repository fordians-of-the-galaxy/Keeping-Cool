package com.fotg.keepingcool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.User;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;
import java.util.Date;

public class PostAdapter extends BaseAdapter {

    ArrayList<Post> posts;
    Context context;

    User user;
    String name;

    public static final String POST_BODY = "com.fotg.keepingcool.BODY";
    public static final String POST_TITLE = "com.fotg.keepingcool.TITLE";
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

        TextView titleTextView = v.findViewById(R.id.titleTextView);
        TextView votesDisplay = v.findViewById(R.id.upvotesText);
        TextView fashion = v.findViewById(R.id.fashionText);
        TextView waste = v.findViewById(R.id.wasteText);
        TextView oceans = v.findViewById(R.id.oceansText);
        TextView rainforests = v.findViewById(R.id.rainforestsText);
        TextView carbon = v.findViewById(R.id.carbonText);
        TextView diet = v.findViewById(R.id.dietText);

        Date time = posts.get(position).getTime();
        String body = posts.get(position).getBody();
        String uid = posts.get(position).getUid();
        String title = posts.get(position).getTitle();
        String postId = posts.get(position).getPostId();

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference postsRef = db.getReference("/posts");
        final DatabaseReference tagsRef = db.getReference("/posts/" + postId + "/tags");

        tagsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if ((boolean) dataSnapshot.child("Fashion").getValue()) {
                    fashion.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("Carbon").getValue()) {
                    carbon.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("Diet").getValue()) {
                    diet.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("Oceans").getValue()) {
                    oceans.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("Rainforest").getValue()) {
                    rainforests.setVisibility(View.VISIBLE);
                }
                if ((boolean) dataSnapshot.child("Waste").getValue()) {
                    waste.setVisibility(View.VISIBLE);
                }

            }
// use dataSnapshot to get boolean. Then if true, display label, if false do not display label.
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        final DatabaseReference upvotesRef = db.getReference("/posts/" + postId + "/upvotes/");
        ArrayList<String> upvotes = new ArrayList<>();
        upvotesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upvotes.clear();
                for (DataSnapshot upvote : dataSnapshot.getChildren()) {
                    String vote = upvote.getValue().toString();
                    upvotes.add(vote);
                }
                votesDisplay.setText(upvotes.size() + " votes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
