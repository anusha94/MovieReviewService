package com.moviereview.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class User {

    private String name;
    private Integer numReviews = 0;
    private String profile;

    public User(String name) {
        this.name = name;
    }

    public void addReview() {
        this.numReviews++;
    }
}

