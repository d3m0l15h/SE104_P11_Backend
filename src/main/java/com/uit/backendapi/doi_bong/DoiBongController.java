package com.uit.backendapi.doi_bong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doi-bong")
public class DoiBongController {

        private final DoiBongService doiBongService;

        @Autowired
        public DoiBongController(DoiBongService doiBongService) {
            this.doiBongService = doiBongService;
        }

        @GetMapping
        public List<DoiBong> getAllDoiBong() {
            return doiBongService.getAllDoiBong();
        }

        @GetMapping("/{id}")
        public ResponseEntity<DoiBong> getDoiBongById(@PathVariable Long id) {
            Optional<DoiBong> doiBong = doiBongService.getDoiBongById(id);
            return doiBong.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @PostMapping
        public ResponseEntity<DoiBong> createDoiBong(@RequestBody DoiBong doiBong) {
            return ResponseEntity.ok(doiBongService.createDoiBong(doiBong));
        }
}
