package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cau-thu")
public class CauThuController {
    private final CauThuService cauThuService;
    private final ModelMapper modelMapper;

    private CauThuDto toDto(CauThu cauThu) {
        return modelMapper.map(cauThu, CauThuDto.class);
    }

    @GetMapping
    public ResponseEntity<List<CauThuDto>> getAllCauThu() {
        return ResponseEntity.ok(cauThuService.getAllCauThu().stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauThuDto> getCauThuById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(cauThuService.getCauThuById(id)));
    }

    @PostMapping
    public ResponseEntity<CauThuDto> createCauThu(@RequestBody CreateCauThuDto createCauThuDto) {
        return ResponseEntity.ok(toDto(cauThuService.createCauThu(createCauThuDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauThuDto> updateCauThu(@PathVariable("id") Long id, @RequestBody UpdateCauThuDto updateCauThuDto) {
        return ResponseEntity.ok(toDto(cauThuService.updateCauThu(id, updateCauThuDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCauThu(@PathVariable("id") Long id) {
        cauThuService.deleteCauThu(id);
        return ResponseEntity.noContent().build();
    }
}
