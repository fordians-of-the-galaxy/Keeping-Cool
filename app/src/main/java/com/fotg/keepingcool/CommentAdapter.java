package com.fotg.keepingcool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fotg.keepingcool.models.Comment;
import com.fotg.keepingcool.models.Post;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter  {


    public static final String POST_COMMENTID = "com.fotg.keepingcool.CID";

    Context context;
    ArrayList<Comment> comments;

    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    final DatabaseReference postRef = db.getReference("/posts");
    final DatabaseReference userRef = db.getReference("/users");

    LayoutInflater mInflator;

    public CommentAdapter(Context c, ArrayList<Comment> cl) {
        context = c;
        comments = cl;
        mInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent) {
        View c = mInflator.inflate(R.layout.content_show_post, null);
        TextView commentPost = c.findViewById(R.id.commentView);

        String comment = comments.get(position).getComment();
        String commentId = comments.get(position).getCommentId();

        commentPost.setText(comment);

        return c;
    }






}
