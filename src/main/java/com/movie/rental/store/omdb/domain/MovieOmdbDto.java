package com.movie.rental.store.omdb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieOmdbDto {
    private String title;
    private int year;
    private String rated;
    private String released;
    private String runtime;
    private String genre;
    private String director;
    private String writer;
    private String actors;
    private String plot;
    private String language;
    private String country;
    private String awards;
    private String poster;
    private List<RatingsDto> ratings;
    private int metascore;
    private double imdbRating;
    private String imdbVotes;
    private String imdbId;
    private String type;
    private String DVD;
    private String boxOffice;
    private String production;
    private String website;
    private boolean response;
}
