package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cau-thu")
public class CauThuController {
    private final CauThuService cauThuService;

    @GetMapping
    public ResponseEntity<List<CauThu>> getAllCauThu() {
        return ResponseEntity.ok(cauThuService.getAllCauThu());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CauThu> getCauThuById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(cauThuService.getCauThuById(id));
    }

    @PostMapping
    public ResponseEntity<CauThu> createCauThu(@RequestBody CreateCauThuDto createCauThuDto) {
        return ResponseEntity.ok(cauThuService.createCauThu(createCauThuDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CauThu> updateCauThu(@PathVariable("id") Long id, @RequestBody UpdateCauThuDto updateCauThuDto) {
        return ResponseEntity.ok(cauThuService.updateCauThu(id, updateCauThuDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCauThu(@PathVariable("id") Long id) {
        cauThuService.deleteCauThu(id);
        return ResponseEntity.noContent().build();
    }
}