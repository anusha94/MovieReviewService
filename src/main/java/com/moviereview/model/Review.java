package com.moviereview.model;

import lombok.*;

@ToString
@Getter
@Setter
public class Review {
    private String userName;
    private String movieName;
    private Float rating;
    private String profile;

    public Review(String userName, String movieName, Float rating, String profile) {
        this.userName = userName;
        this.movieName = movieName;
        this.rating = rating;
        this.profile = profile;
    }
}

