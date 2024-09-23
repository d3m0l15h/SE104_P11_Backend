package com.uit.backendapi.api.controller;

import com.uit.backendapi.api.model.DoiBong;
import com.uit.backendapi.repositories.DoiBongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doi-bong")
public class DoiBongController {

    @Autowired
    private DoiBongRepository doiBongRepository;

    @GetMapping()
    public List<DoiBong> getAllDoiBong() {
        return doiBongRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoiBong> getDoiBongById(@PathVariable Long id) {
        Optional<DoiBong> team = doiBongRepository.findById(id);
        return team.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
