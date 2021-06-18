package com.movie.rental.store.review.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MultimediaDto {
    private String type;
    private String src;
    private int height;
    private int width;
}
