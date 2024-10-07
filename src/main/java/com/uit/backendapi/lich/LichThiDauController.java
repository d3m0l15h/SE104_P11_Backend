package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
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
    public ResponseEntity<List<LichThiDau>> getAllLichThiDau() {
        return ResponseEntity.ok(lichThiDauService.getAllLichThiDau());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LichThiDau> getLichThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauById(id));
    }

    @GetMapping("/doi-bong/{maDoiBong}")
    public ResponseEntity<List<LichThiDau>> getLichThiDauByDoiBong(@PathVariable("maDoiBong") Long maDoiBong,
                                                   @RequestParam(required = false) Long maMuaGiai) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByDoiBongOrMuaGiai(maDoiBong, maMuaGiai));
    }

    @GetMapping("/vong-thi-dau/{vongThiDau}/mua-giai/{maMuaGiai}")
    public ResponseEntity<List<LichThiDau>> getLichThiDauByVongThiDau(@PathVariable("vongThiDau") String vongThiDau,
                                                      @PathVariable("maMuaGiai") Long maMuaGiai) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByVongThiDauAndMaMuaGiai(vongThiDau, maMuaGiai));
    }

    @GetMapping("/mua-giai/{muaGiai}")
    public ResponseEntity<List<LichThiDau>> getLichThiDauByMaMuaGiai(@PathVariable String muaGiai) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByMaMuaGiai(muaGiai));
    }

    @GetMapping("/ngay-thi-dau")
    public ResponseEntity<List<LichThiDau>> getLichThiDauByNgayThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauStart,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauEnd) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByNgayThiDau(ngayThiDauStart, ngayThiDauEnd));
    }

    @GetMapping("/ngay-gio-thi-dau")
    public ResponseEntity
            <List<LichThiDau>> getLichThiDauByNgayThiDauAndGioThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDau,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime gioThiDau) {
        return ResponseEntity.ok(lichThiDauService.getLichThiDauByNgayThiDauAndGioThiDau(ngayThiDau, gioThiDau));
    }

    @PostMapping
    public ResponseEntity<LichThiDau> createLichThiDau(@RequestBody CreateLichThiDauDto createLichThiDauDto) {
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
    public ResponseEntity<LichThiDau> updateLichThiDau(@PathVariable("id") Long id, @RequestBody UpdateLichThiDauDto updateLichThiDauDto) {
        if(updateLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if(updateLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && updateLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if(updateLichThiDauDto.getMaDoiNha().equals(updateLichThiDauDto.getMaDoiKhach())) {
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
