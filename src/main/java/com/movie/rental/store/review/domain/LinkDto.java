package com.movie.rental.store.review.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LinkDto {
    @JsonProperty("type")
    private String reviewType;

    @JsonProperty("url")
    private String reviewUrl;
}
