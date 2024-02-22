package ru.maksimov.MovieService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, представляющий модель фильма.
 */
@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "year_of_production")
    private Integer yearOfProduction;

    @ManyToOne()
    @JoinColumn(name = "producer_id", referencedColumnName = "producer_id")
    private Producer producer;

    @ManyToMany
    @JoinTable(
            name = "movies_actors",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors;

    /**
     * Метод для установки продюсера фильма по его идентификатору.
     *
     * @param producerId Идентификатор продюсера фильма.
     */
    public void producerById(Integer producerId) {
        if(producerId == null) {
            producer = null;
        } else {
            producer = new Producer(producerId);
        }
    }

    /**
     * Метод для установки актеров фильма по их идентификаторам.
     *
     * @param actorsId Список идентификаторов актеров фильма.
     */
    public void actorsById(List<Integer> actorsId) {
        if(actorsId == null) {
            actors = null;
        } else {
            actors = actorsId.stream()
                    .map(Actor::new)
                    .collect(Collectors.toList());
        }
    }
}
