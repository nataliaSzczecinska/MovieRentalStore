package com.movie.rental.store.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultDto {
    private String display_title;
    private String mpaa_rating;
    private int critics_pick;
    private String byline;
    private String headline;
    private String summary_short;
    private LocalDate publication_date;
    private LocalDate opening_date;
    private LocalDateTime date_updated;
    private LinkDto link;
    private MultimediaDto multimedia;
}
