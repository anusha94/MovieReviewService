package com.moviereview.service;

import com.moviereview.model.Movie;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MovieService {

    private Map<String, Movie> movies;

    public MovieService() {
        movies = new HashMap<>();
    }

    public Movie addMovie(String movieName, String year, List<String> genre) {
        Movie movie = new Movie(movieName, year, genre);
        movies.put(movieName, movie);
        return movie;
    }

    public static void movieExists(Map<String, Movie> movies, String movieName) throws Exception {
        if (!movies.containsKey(movieName)) {
            throw new Exception("movie does not exist"); // TODO: make exception class
        }
    }

    public Movie getMovie(String movieName) throws Exception {
        MovieService.movieExists(this.movies, movieName);
        return this.movies.get(movieName);
    }

    public List<Movie> getMoviesByGenre(String genre) {
        List<Movie> moviesByGenre = this.movies.values()
                .stream()
                .filter((movie) -> movie.getGenre()
                        .contains(genre))
                .collect(Collectors.toList());
        return moviesByGenre;
    }

    public List<Movie> getMoviesByReleaseYear(String year) {
        List<Movie> moviesByYear = this.movies.values()
                .stream()
                .filter(movie -> year.equals(movie.getYear()))
                .collect(Collectors.toList());
        return moviesByYear;
    }
}