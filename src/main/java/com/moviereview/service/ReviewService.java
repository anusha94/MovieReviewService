package com.moviereview.service;

import com.moviereview.Client;
import com.moviereview.exception.InvalidRatingException;
import com.moviereview.exception.MultipleReviewException;
import com.moviereview.model.Movie;
import com.moviereview.model.Review;
import com.moviereview.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Component
public class ReviewService {

    private static Logger logger = Logger.getLogger(ReviewService.class.getName());

    private final UserService userService;

    private final MovieService movieService;

    private final ProfileService profileService;


    private final static Map<String, List<Review>> movieReviews = new HashMap<>();
    private final static Map<String, List<Review>> userReviews = new HashMap<>();

    @Autowired
    ReviewService(UserService userService, MovieService movieService, ProfileService profileService) {
        this.userService = userService;
        this.movieService = movieService;
        this.profileService = profileService;
    }

    public void addReview(String userName, String movieName, Float rating) throws Exception {
        User user = userService.getUser(userName);
        Movie movie = movieService.getMovie(movieName);
        this.hasReviewed(userName, movieName);
        this.validateRating(rating);
        Integer weight = profileService.getWeightage(user.getProfile());
        Review review = new Review(user.getName(),
                movie.getName(),
                rating * weight,
                user.getProfile());
        this.addReviewToMap(movieReviews, movieName, review);
        this.addReviewToMap(userReviews, userName, review);
        logger.log(Level.INFO, "user "
                + user.getName()
                + " having profile "
                + user.getProfile()
                + " original score of "
                + rating + " and calculated score of "
                + review.getRating());
        this.profileService.updateUserProfile(userName);
    }

    private void addReviewToMap(Map<String, List<Review>> map, String key, Review review) {
        List<Review> reviews = new ArrayList<>();
        if (null != map && map.containsKey(key)) {
            reviews = map.get(key);
        }
        reviews.add(review);
        map.put(key, reviews);
    }

    private void validateRating(Float rating) throws Exception {
        if ((rating < 0) || (rating > 10))
            throw new InvalidRatingException("invalid rating");
    }

    public void hasReviewed(String userName, String movieName) throws Exception {
        if (userReviews.containsKey(userName)) {
            boolean reviewPresent = userReviews.get(userName).stream()
                    .anyMatch(review -> movieName.equals(review.getMovieName()));
            if (reviewPresent) {
                throw new MultipleReviewException("user already reviewed movie");
            }
        }

    }

    public List<String> getMoviesReviewedByUser(String userName) {
        List<String> movies = userReviews.get(userName)
                .stream()
                .map(Review::getMovieName)
                .collect(Collectors.toList());
        return movies;
    }

    public Double getAverageReviewScoreInYear(String year) {
        List<Movie> movies = movieService.getMoviesByReleaseYear(year);
        List<Review> reviews = this.getReviewByMovieNames(movies);
        return this.getAverage(reviews);
    }

    private List<Review> getReviewByMovieNames(List<Movie> movies) {
        return movies.stream()
                .map(movie -> movieReviews.get(movie.getName()))
                .filter(Objects::nonNull)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private Double getAverage(List<Review> reviews) {
        OptionalDouble average = reviews.stream().mapToDouble(Review::getRating).average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    public Double getAverageReviewScoreForMovie(String movieName) {
        List<Review> reviews = movieReviews.get(movieName);
        return this.getAverage(reviews);
    }

    public List<String> getTopMoviesByProfileAndGenre(Integer top, String profile, String genre) {
        List<Movie> moviesByGenre = this.movieService.getMoviesByGenre(genre);
        if (moviesByGenre.size() == 0) {
            logger.log(Level.SEVERE, "no movies in the given genre");
            return new ArrayList<String>();
        }
        List<Review> movies = this.getReviewByMovieNames(moviesByGenre);
        List<Review> topReviews = movies.stream()
                .filter(review -> profile.equals(review.getProfile()))
                .sorted((a, b) -> b.getRating().compareTo(a.getRating()))
                .limit(top)
                .collect(Collectors.toList());
        return topReviews.stream()
                .map(review -> review.getMovieName())
                .collect(Collectors.toList());
    }


}


