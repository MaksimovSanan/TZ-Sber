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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ActorsService {
    private final RestTemplate restTemplate;
    private final String url = "http://MOVIESSERVICE/api/actors";

    @Autowired
    public ActorsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ActorSimpleDto> getActors(){
        List<ActorSimpleDto> actorSimpleDtos = new ArrayList<>();
        try {
            actorSimpleDtos =  Arrays.stream(restTemplate.getForObject(
                    url,
                    ActorSimpleDto[].class
            )).toList();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return actorSimpleDtos;
    }

    public ActorDto getActorInfo(int id){
        ActorDto actorDto = null;
        try {
            actorDto = restTemplate.getForObject(
                    url + "/" + id,
                    ActorDto.class);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return actorDto;
    }

    public void updateActor(int id, NewActorDto actorDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NewActorDto> requestEntity = new HttpEntity<>(actorDto, headers);

        try {
            restTemplate.patchForObject(url + "/" + id, requestEntity, Void.class);
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteActor(int id) {
        try {
            restTemplate.delete(url + "/" + id);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createActor(NewActorDto newActor) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<NewActorDto> requestEntity = new HttpEntity<>(newActor, headers);
        try {
            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(url, requestEntity, Void.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
