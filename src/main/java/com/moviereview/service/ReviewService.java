package com.moviereview.service;

import com.moviereview.model.Movie;
import com.moviereview.model.Review;
import com.moviereview.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ReviewService {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    private static Map<String, List<Review>> movieReviews;
    private static Map<String, List<Review>> userReviews;

    public void addReview(String userName, String movieName, Float rating) throws Exception {
        User user = userService.getUser(userName);
        Movie movie = movieService.getMovie(movieName);
        this.hasReviewed(userName, movieName);
        this.validateRating(rating);
        Review review = new Review(user.getName(),
                movie.getName(),
                rating,
                user.getProfile());
        this.addReviewToMap(movieReviews, movieName, review);
        this.addReviewToMap(userReviews, userName, review);
        userService.incrementReviews(user.getName());
    }

    private void addReviewToMap(Map<String, List<Review>> map, String key, Review review) {
        List<Review> reviews = new ArrayList<>();
        if (map.containsKey(key)) {
            reviews = map.get(key);
        }
        reviews.add(review);
        map.put(key, reviews);
    }

    private void validateRating(Float rating) throws Exception {
        if ((rating < 0) || (rating > 10))
            throw new Exception("invalid rating");
    }

    public void hasReviewed(String userName, String movieName) throws Exception {
        Boolean reviewPresent = userReviews.get(userName).stream()
                .anyMatch(review -> movieName.equals(review.getMovieName()));
        if (reviewPresent) {
            throw new Exception("user already reviewed movie"); // TODO: make exception class
        }
    }

    public List<String> getMoviesReviewedByUser(String userName) {
        List<String> movies = userReviews.get(userName)
                .stream()
                .map(Review::getMovieName)
                .collect(Collectors.toList());
        return movies;
    }

}

