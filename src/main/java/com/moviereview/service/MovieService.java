package com.moviereview.service;

import com.moviereview.exception.MovieAlreadyExistsException;
import com.moviereview.exception.MovieNotFoundException;
import com.moviereview.model.Movie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MovieService {

    private static final Map<String, Movie> movies = new HashMap<>();


    public Movie addMovie(String movieName, String year, List<String> genre) throws MovieAlreadyExistsException {
        this.checkNewMovie(movieName);
        Movie movie = new Movie(movieName, year, genre);
        movies.put(movieName, movie);
        return movie;
    }

    private void checkNewMovie(String movieName) throws MovieAlreadyExistsException {
        if (movies.containsKey(movieName)) {
            throw new MovieAlreadyExistsException("movie already exists");
        }
    }

    public static void movieExists(Map<String, Movie> movies, String movieName) throws Exception {
        if (!movies.containsKey(movieName)) {
            throw new MovieNotFoundException("movie does not exist"); // TODO: make exception class
        }
    }

    public Movie getMovie(String movieName) throws Exception {
        MovieService.movieExists(movies, movieName);
        return movies.get(movieName);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> moviesByGenre = movies.values()
                .stream()
                .filter((movie) -> movie.getGenre()
                        .contains(genre))
                .collect(Collectors.toList());
        return moviesByGenre;
    }

    public List<Movie> getMoviesByReleaseYear(String year) {
        List<Movie> moviesByYear = movies.values()
                .stream()
                .filter(movie -> year.equals(movie.getYear()))
                .collect(Collectors.toList());
        return moviesByYear;
    }
}