package com.example.moviesnob.model;

public class Data {


    private String comment;
    private String date;
    private  String id;
    private String mid;
    private String user;

    public Data(){}

    public Data(String comment, String date, String id,String mid,String user){

        this.comment=comment;
        this.mid=mid;
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

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
