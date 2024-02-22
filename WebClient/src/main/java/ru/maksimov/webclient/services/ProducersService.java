package ru.maksimov.webclient.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.maksimov.webclient.dto.actorsDto.ActorDto;
import ru.maksimov.webclient.dto.actorsDto.ActorSimpleDto;
import ru.maksimov.webclient.dto.actorsDto.NewActorDto;
import ru.maksimov.webclient.dto.producersDto.NewProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerDto;
import ru.maksimov.webclient.dto.producersDto.ProducerSimpleDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ProducersService {
    private final RestTemplate restTemplate;
    private final String url = "http://MOVIESSERVICE/api/producers";

    @Autowired
    public ProducersService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ProducerSimpleDto> getProducers(){
        List<ProducerSimpleDto> producerSimpleDtos = new ArrayList<>();
        try {
            producerSimpleDtos =  Arrays.stream(restTemplate.getForObject(
                    url,
                    ProducerSimpleDto[].class
            )).toList();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return producerSimpleDtos;
    }

    public ProducerDto getProducerInfo(int id){
        ProducerDto producerDto = null;
        try {
            producerDto = restTemplate.getForObject(
                    url + "/" + id,
                    ProducerDto.class);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return producerDto;
    }

    public void updateProducer(int id, NewProducerDto producerDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewProducerDto> requestEntity = new HttpEntity<>(producerDto, headers);

        try {
            restTemplate.patchForObject(url + "/" + id, requestEntity, Void.class);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createProducer(NewProducerDto newProducer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewProducerDto> requestEntity = new HttpEntity<>(newProducer, headers);
        try {
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestEntity, Void.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
