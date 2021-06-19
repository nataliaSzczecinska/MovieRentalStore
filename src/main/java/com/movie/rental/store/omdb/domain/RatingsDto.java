package com.movie.rental.store.omdb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class RatingsDto {
    @JsonProperty("Source")
    private String source;

    @JsonProperty("Value")
    private String value;
}
