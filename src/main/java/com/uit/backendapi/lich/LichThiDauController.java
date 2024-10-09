package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.LichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lich-thi-dau")
public class LichThiDauController {
    private final LichThiDauService lichThiDauService;

    @GetMapping
    public ResponseEntity<List<LichThiDauDto>> getAllLichThiDau() {
        return ResponseEntity.ok(lichThiDauService.getAllLichThiDau());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichThiDauDto> getLichThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauById(id));
    }

    @GetMapping("/mua-giai/{nam}")
    public ResponseEntity<List<LichThiDauDto>> getLichThiDauByMuaGiai(
            @PathVariable("nam") String nam,
            @RequestParam(name = "vong-thi-dau", required = false) String vongThiDau,
            @RequestParam(name = "ma-doi-bong", required = false) Long maDoiBong) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByMuaGiai(nam, vongThiDau, maDoiBong));
    }

    @GetMapping("/ngay-thi-dau")
    public ResponseEntity<List<LichThiDauDto>> getLichThiDauByNgayThiDau(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayThiDauStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayThiDauEnd) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByNgayThiDau(ngayThiDauStart, ngayThiDauEnd));
    }

    @PostMapping
    public ResponseEntity<LichThiDauDto> createLichThiDau(@RequestBody CreateLichThiDauDto createLichThiDauDto) {
        if(createLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if(createLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && createLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if(createLichThiDauDto.getMaDoiNha().equals(createLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return ResponseEntity.ok(lichThiDauService.createLichThiDau(createLichThiDauDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LichThiDauDto> updateLichThiDau(@PathVariable("id") Long id, @RequestBody UpdateLichThiDauDto updateLichThiDauDto) {
        if(updateLichThiDauDto.getNgayThiDau() !=null && updateLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if(updateLichThiDauDto.getNgayThiDau() !=null && updateLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && updateLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if(updateLichThiDauDto.getMaDoiNha() !=null &&  updateLichThiDauDto.getMaDoiNha().equals(updateLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return ResponseEntity.ok(lichThiDauService.updateLichThiDau(id, updateLichThiDauDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLichThiDau(@PathVariable("id") Long id) {
        lichThiDauService.deleteLichThiDau(id);
        return ResponseEntity.noContent().build();
    }
}
