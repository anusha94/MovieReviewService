package com.moviereview.exception;

public class InvalidRatingException extends Exception{

    public InvalidRatingException(String message) {
        super(message);
    }
}