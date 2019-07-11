package com.fotg.keepingcool.models;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {


    private String body;
    private Date created;
    private Tags tags;
    private String uid;
    private int numberOfLikes;
    private String postId;
    private String title;



    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    public void setNumberOfLikes(int numberOfLikes) {
        this.numberOfLikes = numberOfLikes;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public String getUid() { return uid;}

    public void setUid(String id) { uid = id; }

    public String getBody() {
        return body;
    }

    public void setBody(String b) {
        body = b;
    }


    public Date getTime() {
        return created;
    }

    public void setTime(Date time) {
        created = time;
    }


    public Tags getTags() { return tags; }

    public void setTags(Tags filterChips) { tags = filterChips; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public Post() {}

    public Post(String postBody, Date time, Tags postTags, String id, String postTitle) {

        body = postBody;
        created = time;
        tags = postTags;
        uid = id;
        title = postTitle;

    }

}

