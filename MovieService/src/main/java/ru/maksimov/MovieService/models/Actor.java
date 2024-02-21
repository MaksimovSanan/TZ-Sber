package ru.maksimov.MovieService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Movie> movies;

    public Actor(Integer id) {
        this.id = id;
    }
}
