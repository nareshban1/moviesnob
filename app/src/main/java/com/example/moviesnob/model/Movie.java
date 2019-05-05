package com.example.moviesnob.model;

public class Movie {

    private String Title;
    private String image_url;

    public Movie(){

    }
    public Movie(String Title, String image_url){

        this.Title =Title;
        this.image_url = image_url;

    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {

        Title = title;
    }

    public String getImage_url() {
        return "https://image.tmdb.org/t/p/w500" + image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
