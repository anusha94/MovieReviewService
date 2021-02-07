package com.moviereview;

import com.moviereview.model.Movie;
import com.moviereview.model.User;
import com.moviereview.service.MovieService;
import com.moviereview.service.ReviewService;
import com.moviereview.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@ComponentScan(basePackages = {"com.moviereview.service"},
            basePackageClasses = ReviewService.class)
public class Client {

    public static void main(String args[]) throws Exception {
        ApplicationContext context = SpringApplication.run(Client.class, args);
        ReviewService reviewService = context.getBean(ReviewService.class);
        UserService userService = context.getBean(UserService.class);
        MovieService movieService = context.getBean(MovieService.class);

        Movie m1 = movieService.addMovie("Don", "2006", Arrays.asList("action", "comedy"));
        Movie m2 = movieService.addMovie("Tiger", "2008", Arrays.asList("drama"));
        Movie m3 = movieService.addMovie("Padmaavat", "2006", Arrays.asList("comedy"));
        Movie m4 = movieService.addMovie("Lunchbox", "2021", Arrays.asList("drama"));
        Movie m5 = movieService.addMovie("Guru", "2006", Arrays.asList("drama"));
        Movie m6 = movieService.addMovie("Metro", "2006", Arrays.asList("romance"));

        User u1 = userService.addUser("SRK");
        User u2 = userService.addUser("Salman");
        User u3 = userService.addUser("Deepika");

        reviewService.addReview(u1.getName(), m1.getName(), 3.5f);
        reviewService.addReview(u1.getName(), m3.getName(), 5.0f);
        reviewService.addReview(u2.getName(), m1.getName(), 5.0f);
        reviewService.addReview(u3.getName(), m1.getName(), 9.0f);
        reviewService.addReview(u3.getName(), m5.getName(), 6.0f);
        //reviewService.addReview(u1.getName(), m1.getName(), 10f);
        reviewService.addReview(u3.getName(), m4.getName(), 5.0f);
        //reviewService.addReview(u1.getName(), m1.getName(), 3.5f);




    }
}
