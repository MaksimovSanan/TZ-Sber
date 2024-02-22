package ru.maksimov.MovieService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс, представляющий модель актера.
 */
@Entity
@Table(name = "actors")
@Data
@NoArgsConstructor
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "actors")
    private List<Movie> movies;

    /**
     * Конструктор для создания актера с указанным идентификатором.
     *
     * @param id Идентификатор актера.
     */
    public Actor(Integer id) {
        this.id = id;
    }
}
