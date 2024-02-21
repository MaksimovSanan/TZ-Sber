package ru.maksimov.MovieService.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Movie> movies;

    public Producer(Integer id) {
        this.id = id;
    }
}
