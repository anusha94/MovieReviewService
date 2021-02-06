package com.moviereview;

import com.moviereview.model.Movie;
import com.moviereview.model.User;
import com.moviereview.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

public class Client {
    @Autowired
    static ReviewService reviewService;

    public static void main(String args[]) throws Exception {
        Movie m1 = new Movie("Don", "2006", Arrays.asList("action", "comedy"));
        Movie m2 = new Movie("Tiger", "2008", Arrays.asList("drama"));
        Movie m3 = new Movie("Padmaavat", "2006", Arrays.asList("comedy"));
        Movie m4 = new Movie("Lunchbox", "2021", Arrays.asList("drama"));
        Movie m5 = new Movie("Guru", "2006", Arrays.asList("drama"));
        Movie m6 = new Movie("Metro", "2006", Arrays.asList("romance"));

        User u1 = new User("SRK");
        User u2 = new User("Salman");
        User u3 = new User("Deepika");

        Client.reviewService.addReview(u1.getName(), m1.getName(), 3.5f);
        Client.reviewService.addReview(u1.getName(), m3.getName(), 5.0f);
        Client.reviewService.addReview(u2.getName(), m1.getName(), 5.0f);
        Client.reviewService.addReview(u3.getName(), m1.getName(), 9.0f);
        Client.reviewService.addReview(u3.getName(), m5.getName(), 6.0f);
        //Client.reviewService.addReview(u1.getName(), m1.getName(), 10f);
        Client.reviewService.addReview(u3.getName(), m4.getName(), 5.0f);
        Client.reviewService.addReview(u1.getName(), m1.getName(), 3.5f);




    }
}
