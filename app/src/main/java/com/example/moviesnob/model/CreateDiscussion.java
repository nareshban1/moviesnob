package com.example.moviesnob.model;

public class CreateDiscussion {

        private String title;
        private String date;
        private  String id;
        private String info;
        private String user;

        public CreateDiscussion(){}

        public CreateDiscussion(String id, String date,String title ,String info,String user){
            this.id=id;
            this.date=date;
            this.title=title;
            this.info=info;
            this.user=user;

        }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}


