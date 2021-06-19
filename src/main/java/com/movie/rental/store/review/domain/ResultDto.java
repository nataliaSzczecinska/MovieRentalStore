package com.movie.rental.store.review.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResultDto {
    @JsonProperty("display_title")
    private String displayTitle;

    @JsonProperty("mpaa_rating")
    private String mpaaRating;

    @JsonProperty("byline")
    private String authorOfReview;

    @JsonProperty("headline")
    private String headline;

    @JsonProperty("summary_short")
    private String summary;

    @JsonProperty("link")
    private LinkDto link;
}
