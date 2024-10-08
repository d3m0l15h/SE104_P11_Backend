package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ket-qua")
public class KetQuaThiDauController {
    private final KetQuaThiDauService ketQuaThiDauService;

    @GetMapping()
    public ResponseEntity<List<KetQuaThiDau>> getAllKetQuaThiDau() {
        return ResponseEntity.ok(ketQuaThiDauService.getAllKetQuaThiDau());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KetQuaThiDau> getKetQuaThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(ketQuaThiDauService.getKetQuaThiDauById(id));
    }

    @PostMapping()
    public ResponseEntity<KetQuaThiDau> createKetQuaThiDau(@RequestBody CreateKetQuaThiDauDto createKetQuaThiDauDto) {
        return ResponseEntity.ok(ketQuaThiDauService.createKetQuaThiDau(createKetQuaThiDauDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KetQuaThiDau> updateKetQuaThiDau(@PathVariable("id") Long id, @RequestBody UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        return ResponseEntity.ok(ketQuaThiDauService.updateKetQuaThiDau(id, updateKetQuaThiDauDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKetQuaThiDau(@PathVariable("id") Long id) {
        ketQuaThiDauService.deleteKetQuaThiDau(id);
        return ResponseEntity.noContent().build();
    }
}
