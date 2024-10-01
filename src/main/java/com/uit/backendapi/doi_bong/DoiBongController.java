package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/doi-bong")
public class DoiBongController {

        private final DoiBongService doiBongService;

        @Autowired
        public DoiBongController(DoiBongService doiBongService) {
            this.doiBongService = doiBongService;
        }

        @GetMapping
        public ResponseEntity<List<DoiBong>> getAllDoiBong() {
            return ResponseEntity.ok(doiBongService.getAllDoiBong());
        }

        @GetMapping("/{id}")
        public ResponseEntity<DoiBong> getDoiBongById(@PathVariable("id") Long id) {
            DoiBong doiBong = doiBongService.getDoiBongById(id);
            return ResponseEntity.ok(doiBong);
        }

        @PostMapping
        public ResponseEntity<DoiBong> createDoiBong(@ModelAttribute CreateDoiBongDto createDoiBongDto) throws IOException {
            return ResponseEntity.ok(doiBongService.createDoiBong(createDoiBongDto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<DoiBong> updateDoiBong(@PathVariable("id") Long id, @ModelAttribute UpdateDoiBongDto updateDoiBongDto) throws IOException {
            return ResponseEntity.ok(doiBongService.updateDoiBong(id, updateDoiBongDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDoiBong(@PathVariable Long id) {
            doiBongService.deleteDoiBong(id);
            return ResponseEntity.noContent().build();
        }
}
