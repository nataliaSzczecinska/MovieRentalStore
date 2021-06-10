package com.movie.rental.store.domain;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name ="COPIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Copy {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "COPY_ID", unique = true)
    private Long copyId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @NotNull
    @Column(name = "COPY_STATUS")
    private Status copyStatus;

    @NotNull
    @Column(name = "MEDIA_TYPE")
    private MediaType mediaType;
}
