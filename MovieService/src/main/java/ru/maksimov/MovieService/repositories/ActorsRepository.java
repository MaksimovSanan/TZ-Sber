package ru.maksimov.MovieService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maksimov.MovieService.models.Actor;

@Repository
public interface ActorsRepository extends JpaRepository<Actor, Integer> {
}
