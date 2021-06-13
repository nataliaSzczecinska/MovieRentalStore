package com.movie.rental.store.domain;

import com.movie.rental.store.domain.archive.DeleteCopy;
import com.movie.rental.store.domain.enums.Type;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "Movie.retrieveMovieByTitleFragment",
                query = "FROM Movie WHERE movieTitle LIKE: titleFragment"
        ),
        @NamedQuery(
                name = "Movie.retrieveMovieByDirectorFragment",
                query = "FROM Movie WHERE movieDirector LIKE : directorFragment"
        ),
        @NamedQuery(
                name = "Movie.retrieveMovieByDescriptionFragment",
                query = "FROM Movie WHERE movieDescription LIKE : descriptionFragment"
        )
})
@Entity
@Table(name ="MOVIES")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue
    @NotNull
    @Column(name = "MOVIE_ID", unique = true)
    private Long movieId;

    @NotNull
    @Column(name = "MOVIE_TITLE")
    private String movieTitle;

    @NotNull
    @Column(name = "MOVIE_DIRECTOR")
    private String movieDirector;

    @NotNull
    @Lob
    @Column(name = "MOVIE_DESCRIPTION")
    private String movieDescription;

    @NotNull
    @Column(name = "MOVIE_TYPE")
    private Type movieType;

    @NotNull
    @Column(name = "MOVIE_YEAR")
    private int movieYear;

    @OneToMany (
            targetEntity = Copy.class,
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Copy> copies;

    @OneToMany(
            targetEntity = DeleteCopy.class,
            mappedBy = "movie",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<DeleteCopy> deleteCopies;

    public Movie(@NotNull Long movieId, @NotNull String movieTitle, @NotNull String movieDirector, @NotNull String movieDescription, @NotNull Type movieType, @NotNull int movieYear) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieDirector = movieDirector;
        this.movieDescription = movieDescription;
        this.movieType = movieType;
        this.movieYear = movieYear;
    }

    public Movie(@NotNull String movieTitle, @NotNull String movieDirector, @NotNull String movieDescription, @NotNull Type movieType, @NotNull int movieYear) {
        this.movieTitle = movieTitle;
        this.movieDirector = movieDirector;
        this.movieDescription = movieDescription;
        this.movieType = movieType;
        this.movieYear = movieYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return movieYear == movie.movieYear &&
                Objects.equals(movieTitle, movie.movieTitle) &&
                Objects.equals(movieDirector, movie.movieDirector) &&
                Objects.equals(movieDescription, movie.movieDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movieTitle, movieDirector, movieDescription, movieYear);
    }
}
