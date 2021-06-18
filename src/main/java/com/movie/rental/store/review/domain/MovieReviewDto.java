package com.movie.rental.store.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovieReviewDto {
    private String status;
    private String copyright;
    private boolean has_more;
    private int num_results;
    private List<ResultDto> results;
}
