package ru.maksimov.MovieService.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.maksimov.MovieService.models.Producer;
import ru.maksimov.MovieService.repositories.ProducersRepository;
import ru.maksimov.MovieService.util.PatchHelper;
import ru.maksimov.MovieService.util.exceptions.MovieNotFoundException;
import ru.maksimov.MovieService.util.exceptions.ProducerNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProducersServiceImpl implements ProducersService{

    private final ProducersRepository producersRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ProducersServiceImpl(ProducersRepository producersRepository) {
        this.producersRepository = producersRepository;
    }

    @Override
    public List<Producer> findAll() {
        return producersRepository.findAll();
    }

    @Override
    public Producer findById(Integer id) {
        return producersRepository.findById(id).orElseThrow(
                () -> new ProducerNotFoundException("Producer with id " + id + " not found!"));
    }

    @Override
    @Transactional
    public void save(Producer producer) {
        producersRepository.save(producer);
    }

    @Override
    @Transactional
    public Producer update(int id, Producer producerUpdated) {
        Producer existingProducer = entityManager.find(Producer.class, id);
        if (existingProducer == null) {
            throw new MovieNotFoundException("Producer with id " + id + " not found!");
        }


        PatchHelper.copyNonNullProperties(producerUpdated, existingProducer);


        return entityManager.merge(existingProducer);
    }

    @Override
    @Transactional
    public void delete(Producer producer) {
        producersRepository.delete(producer);
    }
}
