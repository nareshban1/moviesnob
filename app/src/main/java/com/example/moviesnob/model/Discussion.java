package com.example.moviesnob.model;

public class Discussion {

    private String comment;
    private String date;
    private  String id;
    private String user;

    public Discussion(){}

    public Discussion(String comment, String date, String id, String user){

        this.comment=comment;
        this.date=date;
        this.id=id;
        this.user=user;

    }
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}


