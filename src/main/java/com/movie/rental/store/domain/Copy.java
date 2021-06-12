package com.movie.rental.store.domain;

import com.movie.rental.store.domain.enums.MediaType;
import com.movie.rental.store.domain.enums.Status;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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

    @OneToMany (
            targetEntity = Borrow .class,
            mappedBy = "copy",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Borrow> borrow;

    public Copy(@NotNull Long copyId, @NotNull Movie movie, @NotNull Status copyStatus, @NotNull MediaType mediaType) {
        this.copyId = copyId;
        this.movie = movie;
        this.copyStatus = copyStatus;
        this.mediaType = mediaType;
    }
}
