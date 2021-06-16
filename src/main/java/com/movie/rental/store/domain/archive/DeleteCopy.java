package com.movie.rental.store.domain.archive;

import com.movie.rental.store.domain.Movie;
import com.movie.rental.store.domain.enums.MediaType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "DELETED_COPIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DeleteCopy {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "DELETE_COPY_ID", unique = true)
    private Long deleteCopyId;

    @NotNull
    @Column(name = "PREVIOUS_COPY_ID", unique = true)
    private Long previousCopyId;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "MOVIE_ID")
    private Movie movie;

    @NotNull
    @Column(name = "MEDIA_TYPE")
    private MediaType mediaType;

    @NotNull
    @Column(name = "DELETE_DATE")
    private LocalDate deleteDate;

    public DeleteCopy(Long previousCopyId, Movie movie, MediaType mediaType, LocalDate deleteDate) {
        this.previousCopyId = previousCopyId;
        this.movie = movie;
        this.mediaType = mediaType;
        this.deleteDate = deleteDate;
    }
}
