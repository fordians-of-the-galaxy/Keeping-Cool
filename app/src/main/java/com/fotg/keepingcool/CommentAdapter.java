package com.fotg.keepingcool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fotg.keepingcool.models.Comment;
import com.fotg.keepingcool.models.Post;
import com.fotg.keepingcool.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference postRef = db.getReference("/posts");
    final DatabaseReference userRef = db.getReference("/users");

    LayoutInflater mInflater;
    ArrayList<Comment> items;
    Context context;
    User user;
    String name;


    public CommentAdapter(Context c, ArrayList<Comment> ps) {
        context = c;
        items = ps;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mInflater.inflate(R.layout.activity_comments_list_view, null);

        TextView commentTextView = v.findViewById(R.id.commentID);
        TextView userTextView = v.findViewById(R.id.userComment);

        String comments = items.get(i).getComment();
        String userComments = items.get(i).getuID();


        commentTextView.setText(comments);
//        userTextView.setText(userComments);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.child(userComments).getValue(User.class);
                name = user.getName();
                userTextView.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        return v;
    }
}
