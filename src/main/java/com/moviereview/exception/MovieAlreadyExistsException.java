package com.moviereview.exception;

import com.moviereview.model.Movie;

public class MovieAlreadyExistsException extends Exception{

    public MovieAlreadyExistsException(String message) {
        super(message);
    }
}
