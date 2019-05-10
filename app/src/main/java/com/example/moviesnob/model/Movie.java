package com.example.moviesnob.model;

public class Movie {

    private String Title;
    private String image_url;
    private String back_url;
    private String description;
    private String rating;
    private int id;
    private String release;

    public Movie(){

    }
    public Movie(int id, String Title, String image_url, String description, String rating, String back_url,String release){

        this.Title =Title;
        this.image_url = image_url;
        this.description = description;
        this.rating = rating;
        this.back_url=back_url;
        this.id=id;
        this.release=release;

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

    public void setImage_url(String image_url)
    {
        this.image_url = image_url;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getBack_url() {

        return "https://image.tmdb.org/t/p/w500" + back_url;
    }

    public void setBack_url(String back_url) {

        this.back_url = back_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }
}
