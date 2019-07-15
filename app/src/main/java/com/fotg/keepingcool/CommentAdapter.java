package com.fotg.keepingcool;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fotg.keepingcool.models.Comment;
import com.fotg.keepingcool.models.Post;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {

    LayoutInflater mInflater;
    ArrayList<Comment> items;
    Context context;


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

        String comments = items.get(i).getComment();

        commentTextView.setText(comments);

        return v;
    }
}
