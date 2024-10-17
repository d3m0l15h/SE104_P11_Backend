package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doi-bong")
public class DoiBongController {
    private final DoiBongService doiBongService;
    private final ModelMapper modelMapper;

    private DoiBongDto toDto(DoiBong doiBong) {
        return modelMapper.map(doiBong, DoiBongDto.class);
    }

    @GetMapping
    public ResponseEntity<List<DoiBongDto>> getAllDoiBong() {
        return ResponseEntity.ok(doiBongService.getAllDoiBong().stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoiBongDto> getDoiBongById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(doiBongService.getDoiBongById(id)));
    }

    @PostMapping
    public ResponseEntity<DoiBongDto> createDoiBong(@ModelAttribute CreateDoiBongDto createDoiBongDto) throws IOException {
        if (createDoiBongDto.getAoChinhThuc() == null || createDoiBongDto.getAoDuBi() == null || createDoiBongDto.getLogo() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(toDto(doiBongService.createDoiBong(createDoiBongDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoiBongDto> updateDoiBong(@PathVariable("id") Long id, @ModelAttribute UpdateDoiBongDto updateDoiBongDto) throws IOException {
        return ResponseEntity.ok(toDto(doiBongService.updateDoiBong(id, updateDoiBongDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoiBong(@PathVariable Long id) {
        doiBongService.deleteDoiBong(id);
        return ResponseEntity.noContent().build();
    }
}
