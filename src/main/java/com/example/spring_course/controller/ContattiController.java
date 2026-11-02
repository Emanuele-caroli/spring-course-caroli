package com.example.spring_course.controller;

import com.example.spring_course.generated.api.ContattiApi;
import com.example.spring_course.generated.model.Contatto;
import com.example.spring_course.generated.model.CreateContattoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ContattiController implements ContattiApi {

    @Override
    public ResponseEntity<Contatto> creaContatto(CreateContattoRequest createContattoRequest) {
        return ContattiApi.super.creaContatto(createContattoRequest);
    }

    @Override
    public ResponseEntity<Contatto> trovaPerId(Long id) {
        return ContattiApi.super.trovaPerId(id);
    }

    @Override
    public ResponseEntity<List<Contatto>> trovaTutti() {
        return ContattiApi.super.trovaTutti();
    }
}
