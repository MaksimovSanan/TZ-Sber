package ru.maksimov.MovieService;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import ru.maksimov.MovieService.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.MovieService.dto.moviesDto.MovieDto;
import ru.maksimov.MovieService.dto.moviesDto.NewMovieDto;
import ru.maksimov.MovieService.models.Movie;

import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieServiceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		modelMapper.typeMap(NewMovieDto.class, Movie.class)
				.addMappings(mapper -> {
					mapper.map(NewMovieDto::getProducerId, Movie::producerById);
				});

		modelMapper.typeMap(NewMovieDto.class, Movie.class)
				.addMappings(mapper -> {
					mapper.map(NewMovieDto::getActorsId, Movie::actorsById);
				});

		modelMapper.typeMap(NewMovieDto.class, Movie.class)
				.addMappings(mapper -> mapper.skip(Movie::setId));

		return modelMapper;
	}
}
