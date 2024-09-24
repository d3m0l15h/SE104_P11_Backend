package com.uit.backendapi.doi_bong;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoiBongService {
    private final DoiBongRepository doiBongRepository;

    public DoiBongService(DoiBongRepository doiBongRepository) {
        this.doiBongRepository = doiBongRepository;
    }

    public List<DoiBong> getAllDoiBong() {
        return doiBongRepository.findAll();
    }

    public Optional<DoiBong> getDoiBongById(Long id) {
        return doiBongRepository.findById(id);
    }

    public DoiBong createDoiBong(DoiBong doiBong) {
        return doiBongRepository.save(doiBong);
    }
}
