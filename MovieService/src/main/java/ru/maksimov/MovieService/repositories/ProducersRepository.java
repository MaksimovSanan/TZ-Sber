package ru.maksimov.MovieService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.maksimov.MovieService.models.Producer;

@Repository
public interface ProducersRepository extends JpaRepository<Producer, Integer> {
}
