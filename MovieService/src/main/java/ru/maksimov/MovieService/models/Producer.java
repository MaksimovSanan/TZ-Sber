package ru.maksimov.MovieService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий модель продюсера фильма.
 */
@Entity
@Table(name = "producers")
@Data
@NoArgsConstructor
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "producer_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "producer")
    private List<Movie> movies;

    /**
     * Конструктор для создания продюсера фильма с указанным идентификатором.
     *
     * @param id Идентификатор продюсера фильма.
     */
    public Producer(Integer id) {
        this.id = id;
    }
}
