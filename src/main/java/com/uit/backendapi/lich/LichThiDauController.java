package com.uit.backendapi.lich;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/lich-thi-dau")
public class LichThiDauController {
    private final LichThiDauService lichThiDauService;

    @Autowired
    public LichThiDauController(LichThiDauService lichThiDauService) {
        this.lichThiDauService = lichThiDauService;
    }

    @GetMapping
    public List<LichThiDau> getAllLichThiDau() {
        return lichThiDauService.findAllLichThiDau();
    }

    @GetMapping("/{id}")
    public LichThiDau getLichThiDauById(Long id) {
        return lichThiDauService.findLichThiDauById(id);
    }

    @GetMapping("/doi-bong/{maDoiBong}")
    public List<LichThiDau> getLichThiDauByDoiBong(@PathVariable Long maDoiBong,
                                                   @RequestParam(required = false) Long maMuaGiai) {
        return lichThiDauService.findLichThiDauByDoiBongOrMuaGiai(maDoiBong, maMuaGiai);
    }

    @GetMapping("/vong-thi-dau/{vongThiDau}/mua-giai/{maMuaGiai}")
    public List<LichThiDau> getLichThiDauByVongThiDau(@PathVariable String vongThiDau,
                                                      @PathVariable Long maMuaGiai) {
        return lichThiDauService.findLichThiDauByVongThiDauAndMaMuaGiai(vongThiDau, maMuaGiai);
    }

    @GetMapping("/mua-giai/{muaGiai}")
    public List<LichThiDau> getLichThiDauByMaMuaGiai(@PathVariable String muaGiai) {
        return lichThiDauService.findLichThiDauByMaMuaGiai(muaGiai);
    }

    @GetMapping("/ngay-thi-dau")
    public List<LichThiDau> getLichThiDauByNgayThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauStart,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauEnd) {
        return lichThiDauService.findLichThiDauByNgayThiDau(ngayThiDauStart, ngayThiDauEnd);
    }

    @GetMapping("/ngay-gio-thi-dau")
    public List<LichThiDau> getLichThiDauByNgayThiDauAndGioThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDau,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime gioThiDau) {
        return lichThiDauService.findLichThiDauByNgayThiDauAndGioThiDau(ngayThiDau, gioThiDau);
    }

    @PostMapping
    public LichThiDau createLichThiDau(LichThiDau lichThiDau) {
        return lichThiDauService.createLichThiDau(lichThiDau);
    }

    @PutMapping("/{id}")
    public LichThiDau updateLichThiDau(Long id, LichThiDau lichThiDauDto) {
        return lichThiDauService.updateLichThiDau(id, lichThiDauDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLichThiDau(@PathVariable Long id) {
        lichThiDauService.deleteLichThiDau(id);
    }
}
