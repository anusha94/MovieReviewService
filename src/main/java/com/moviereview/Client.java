package com.moviereview;

import com.moviereview.exception.InvalidRatingException;
import com.moviereview.exception.MultipleReviewException;
import com.moviereview.model.Movie;
import com.moviereview.model.User;
import com.moviereview.service.MovieService;
import com.moviereview.service.ReviewService;
import com.moviereview.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = {"com.moviereview.service"},
            basePackageClasses = {ReviewService.class, UserService.class})
public class Client {

    private static Logger logger = Logger.getLogger(Client.class.getName());
    private static DecimalFormat df2 = new DecimalFormat("#.##");

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
        Movie m7 = movieService.addMovie("Raees", "2019", Arrays.asList("drama"));

        User u1 = userService.addUser("SRK");
        User u2 = userService.addUser("Salman");
        User u3 = userService.addUser("Deepika");
        User u4 = userService.addUser("Javed");

        logger.log(Level.INFO, "u1 profile: " + u1.getProfile());
        reviewService.addReview(u1.getName(), m1.getName(), 3.5f);
        reviewService.addReview(u1.getName(), m3.getName(), 5.0f);
        reviewService.addReview(u1.getName(), m2.getName(), 5.8f);
        logger.log(Level.INFO, "u1 number of reviews: " + u1.getNumReviews());
        logger.log(Level.INFO, "u1 updated profile: " + u1.getProfile());

        reviewService.addReview(u2.getName(), m1.getName(), 5.0f);
        reviewService.addReview(u2.getName(), m4.getName(), 9.0f);
        reviewService.addReview(u2.getName(), m6.getName(), 6.0f);
        reviewService.addReview(u3.getName(), m1.getName(), 9.0f);
        reviewService.addReview(u3.getName(), m5.getName(), 6.0f);
        reviewService.addReview(u3.getName(), m4.getName(), 5.0f);
        reviewService.addReview(u3.getName(), m2.getName(), 7.0f);
        reviewService.addReview(u3.getName(), m7.getName(), 10.0f);
        reviewService.addReview(u1.getName(), m7.getName(), 3.0f);
        reviewService.addReview(u2.getName(), m7.getName(), 6.5f);
        reviewService.addReview(u1.getName(), m4.getName(), 8.5f);

        try {
            reviewService.addReview(u1.getName(), m1.getName(), 10f);
        } catch (MultipleReviewException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }

        try {
            reviewService.addReview(u2.getName(), m5.getName(), 11f);
        } catch (InvalidRatingException ex) {
            logger.log(Level.SEVERE, ex.getMessage());
        }

        df2.setRoundingMode(RoundingMode.DOWN);
        logger.log(Level.INFO, "average review score for movie "
                + m1.getName()
                + " is: "
                + df2.format(reviewService.getAverageReviewScoreForMovie(m1.getName())) );

        logger.log(Level.INFO, "average review score for the year 2006 is: "
                + df2.format(reviewService.getAverageReviewScoreInYear("2006")));

        logger.log(Level.INFO, "getting top 3 movies reviewed by critics for the genre drama: "
                + reviewService.getTopMoviesByProfileAndGenre(3, "critic", "drama").toString());

        logger.log(Level.INFO, "Users who are viewers: " + userService.getUsersByProfile("viewer"));
        logger.log(Level.INFO, "Users who are critics: " + userService.getUsersByProfile("critic"));
    }
}
