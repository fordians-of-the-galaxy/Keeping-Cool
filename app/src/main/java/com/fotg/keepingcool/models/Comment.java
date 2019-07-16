package com.fotg.keepingcool.models;

public class Comment {
    private String uID;
    private String comment;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    private String commentId;

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Comment() {}

    public Comment(String user, String commentBody) {
        uID = user;
        comment = commentBody;
    }
}
