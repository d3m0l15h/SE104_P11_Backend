package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
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
        return lichThiDauService.getAllLichThiDau();
    }

    @GetMapping("/{id}")
    public LichThiDau getLichThiDauById(@PathVariable("id") Long id) {
        return lichThiDauService.getLichThiDauById(id);
    }

    @GetMapping("/doi-bong/{maDoiBong}")
    public List<LichThiDau> getLichThiDauByDoiBong(@PathVariable("maDoiBong") Long maDoiBong,
                                                   @RequestParam(required = false) Long maMuaGiai) {
        return lichThiDauService.getLichThiDauByDoiBongOrMuaGiai(maDoiBong, maMuaGiai);
    }

    @GetMapping("/vong-thi-dau/{vongThiDau}/mua-giai/{maMuaGiai}")
    public List<LichThiDau> getLichThiDauByVongThiDau(@PathVariable("vongThiDau") String vongThiDau,
                                                      @PathVariable("maMuaGiai") Long maMuaGiai) {
        return lichThiDauService.getLichThiDauByVongThiDauAndMaMuaGiai(vongThiDau, maMuaGiai);
    }

    @GetMapping("/mua-giai/{muaGiai}")
    public List<LichThiDau> getLichThiDauByMaMuaGiai(@PathVariable String muaGiai) {
        return lichThiDauService.getLichThiDauByMaMuaGiai(muaGiai);
    }

    @GetMapping("/ngay-thi-dau")
    public List<LichThiDau> getLichThiDauByNgayThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauStart,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDauEnd) {
        return lichThiDauService.getLichThiDauByNgayThiDau(ngayThiDauStart, ngayThiDauEnd);
    }

    @GetMapping("/ngay-gio-thi-dau")
    public List<LichThiDau> getLichThiDauByNgayThiDauAndGioThiDau(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate ngayThiDau,
                                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime gioThiDau) {
        return lichThiDauService.getLichThiDauByNgayThiDauAndGioThiDau(ngayThiDau, gioThiDau);
    }

    @PostMapping
    public LichThiDau createLichThiDau(@RequestBody CreateLichThiDauDto createLichThiDauDto) {
        if(createLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if(createLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && createLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if(createLichThiDauDto.getMaDoiNha().equals(createLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return lichThiDauService.createLichThiDau(createLichThiDauDto);
    }

    @PutMapping("/{id}")
    public LichThiDau updateLichThiDau(@PathVariable("id") Long id, @RequestBody UpdateLichThiDauDto updateLichThiDauDto) {
        if(updateLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if(updateLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && updateLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if(updateLichThiDauDto.getMaDoiNha().equals(updateLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return lichThiDauService.updateLichThiDau(id, updateLichThiDauDto);
    }

    @DeleteMapping("/{id}")
    public void deleteLichThiDau(@PathVariable("id") Long id) {
        lichThiDauService.deleteLichThiDau(id);
    }
}
