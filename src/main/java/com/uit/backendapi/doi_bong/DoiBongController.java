package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.DoiBongDto;
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
        public ResponseEntity<List<DoiBongDto>> getAllDoiBong() {
            return ResponseEntity.ok(doiBongService.getAllDoiBong());
        }

        @GetMapping("/{id}")
        public ResponseEntity<DoiBongDto> getDoiBongById(@PathVariable("id") Long id) {
            return ResponseEntity.ok(doiBongService.getDoiBongById(id));
        }

        @PostMapping
        public ResponseEntity<DoiBongDto> createDoiBong(@ModelAttribute CreateDoiBongDto createDoiBongDto) throws IOException {
            if(createDoiBongDto.getAoChinhThuc() == null || createDoiBongDto.getAoDuBi() == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(doiBongService.createDoiBong(createDoiBongDto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<DoiBongDto> updateDoiBong(@PathVariable("id") Long id, @ModelAttribute UpdateDoiBongDto updateDoiBongDto) throws IOException {
            return ResponseEntity.ok(doiBongService.updateDoiBong(id, updateDoiBongDto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteDoiBong(@PathVariable Long id) {
            doiBongService.deleteDoiBong(id);
            return ResponseEntity.noContent().build();
        }
}
