package com.moviereview.model;

import lombok.*;

import java.util.List;

@ToString
@Getter
@Setter
public class Movie {

    private String name;
    private String year;
    private List<String> genre;

    public Movie(String name, String year, List<String> genre) {
        this.name = name;
        this.year = year;
        this.genre = genre;
    }
}
