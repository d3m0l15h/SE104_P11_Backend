package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.ban_thang.BanThangService;
import com.uit.backendapi.ban_thang.dto.BanThangDto;
import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.ban_thang.dto.UpdateBanThangDto;
import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;
import com.uit.backendapi.thay_nguoi.ThayNguoi;
import com.uit.backendapi.thay_nguoi.ThayNguoiService;
import com.uit.backendapi.thay_nguoi.dto.CreateThayNguoiDto;
import com.uit.backendapi.thay_nguoi.dto.ThayNguoiDto;
import com.uit.backendapi.thay_nguoi.dto.UpdateThayNguoiDto;
import com.uit.backendapi.the_phat.ThePhat;
import com.uit.backendapi.the_phat.ThePhatService;
import com.uit.backendapi.the_phat.dto.CreateThePhatDto;
import com.uit.backendapi.the_phat.dto.ThePhatDto;
import com.uit.backendapi.the_phat.dto.UpdateThePhatDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ket-qua")
public class KetQuaThiDauController {
    private final KetQuaThiDauService ketQuaThiDauService;
    private final BanThangService banThangService;
    private final ThePhatService thePhatService;
    private final ThayNguoiService thayNguoiService;
    private final ModelMapper modelMapper;

    private KetQuaThiDauDto toKetQuaThiDauDto(KetQuaThiDau ketQuaThiDau) {
        return modelMapper.map(ketQuaThiDau, KetQuaThiDauDto.class);
    }

    private BanThangDto toBanThangDto(BanThang banThang) {
        return modelMapper.map(banThang, BanThangDto.class);
    }

    private ThePhatDto toThePhatDto(ThePhat thePhat) {
        return modelMapper.map(thePhat, ThePhatDto.class);
    }

    private ThayNguoiDto toThayNguoiDto(ThayNguoi thayNguoi) {
        return modelMapper.map(thayNguoi, ThayNguoiDto.class);
    }

    //-----------------------------Ket Qua----------------------------
    @GetMapping()
    public ResponseEntity<List<KetQuaThiDauDto>> getAllKetQuaThiDau() {
        return ResponseEntity.ok(ketQuaThiDauService.getAllKetQuaThiDau().stream().map(this::toKetQuaThiDauDto).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KetQuaThiDauDto> getKetQuaThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.getKetQuaThiDauById(id)));
    }

    @PostMapping()
    public ResponseEntity<KetQuaThiDauDto> createKetQuaThiDau(@RequestBody CreateKetQuaThiDauDto createKetQuaThiDauDto) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.createKetQuaThiDau(createKetQuaThiDauDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<KetQuaThiDauDto> updateKetQuaThiDau(@PathVariable("id") Long id, @RequestBody UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.updateKetQuaThiDau(id, updateKetQuaThiDauDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKetQuaThiDau(@PathVariable("id") Long id) {
        ketQuaThiDauService.deleteKetQuaThiDau(id);
        return ResponseEntity.noContent().build();
    }

    //---------------------------------Ban Thang----------------------------------------
    @GetMapping("/{id}/ban-thang")
    public ResponseEntity<List<BanThangDto>> getBanThangByKetQua(@PathVariable("id") Long id) {
        return ResponseEntity.ok(banThangService.getBanThangByKetQua(id).stream().map(this::toBanThangDto).toList());
    }

    @PostMapping("/{id}/ban-thang")
    public ResponseEntity<BanThangDto> createBanThang(@PathVariable("id") Long id, @RequestBody CreateBanThangDto createBanThangDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toBanThangDto(banThangService.createBanThangByKetQua(ketQuaThiDau, createBanThangDto)));
    }

    @PutMapping("/{id}/ban-thang/{maBanThang}")
    public ResponseEntity<BanThangDto> updateBanThang(@PathVariable("id") Long id,
                                                   @PathVariable("maBanThang") Long maBanThang,
                                                   @RequestBody UpdateBanThangDto updateBanThangDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toBanThangDto(banThangService.updateBanThangByKetQuaAndId(ketQuaThiDau, maBanThang, updateBanThangDto)));
    }

    @DeleteMapping("/{id}/ban-thang/{maBanThang}")
    public ResponseEntity<Void> deleteBanThang(@PathVariable("id") Long id, @PathVariable("maBanThang") Long maBanThang) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        banThangService.deleteBanThangByKetQuaAndId(ketQuaThiDau, maBanThang);
        return ResponseEntity.noContent().build();
    }

    //-------------------------------The phat--------------------------------
    @GetMapping("/{id}/the-phat")
    public ResponseEntity<List<ThePhatDto>> getThePhatByKetQua(@PathVariable("id") Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(thePhatService.getThePhatByMaKetQua(ketQuaThiDau).stream().map(this::toThePhatDto).toList());
    }

    @PostMapping("/{id}/the-phat")
    public ResponseEntity<ThePhatDto> createThePhat(@PathVariable("id") Long id, @RequestBody CreateThePhatDto createThePhatDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThePhatDto(thePhatService.createThePhatByMaKetQua(ketQuaThiDau, createThePhatDto)));
    }

    @PutMapping("/{id}/the-phat/{maThePhat}")
    public ResponseEntity<ThePhatDto> updateThePhat(@PathVariable("id") Long id,
                                                @PathVariable("maThePhat") Long maThePhat,
                                                @RequestBody UpdateThePhatDto updateThePhatDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThePhatDto(thePhatService.updateThePhatByMaKetQuaAndId(ketQuaThiDau, maThePhat, updateThePhatDto)));
    }

    @DeleteMapping("/{id}/the-phat/{maThePhat}")
    public ResponseEntity<Void> deleteThePhat(@PathVariable("id") Long id, @PathVariable("maThePhat") Long maThePhat) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        thePhatService.deleteThePhatByMaKetQuaAndId(ketQuaThiDau, maThePhat);
        return ResponseEntity.noContent().build();
    }

    //-------------------------------Thay nguoi--------------------------------
    @GetMapping("/{id}/thay-nguoi")
    public ResponseEntity<List<ThayNguoiDto>> getThayNguoiByKetQua(@PathVariable("id") Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(thayNguoiService.getThayNguoiByKetQua(ketQuaThiDau).stream().map(this::toThayNguoiDto).toList());
    }

    @PostMapping("/{id}/thay-nguoi")
    public ResponseEntity<ThayNguoiDto> createThayNguoi(@PathVariable("id") Long id, @RequestBody CreateThayNguoiDto createThayNguoiDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThayNguoiDto(thayNguoiService.createThayNguoiByKetQua(ketQuaThiDau, createThayNguoiDto)));
    }

    @PutMapping("/{id}/thay-nguoi/{maThayNguoi}")
    public ResponseEntity<ThayNguoiDto> updateThayNguoi(@PathVariable("id") Long id,
                                                     @PathVariable("maThayNguoi") Long maThayNguoi,
                                                     @RequestBody UpdateThayNguoiDto updateThayNguoiDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThayNguoiDto(thayNguoiService.updateThayNguoiByKetQuaAndId(ketQuaThiDau, maThayNguoi, updateThayNguoiDto)));
    }

    @DeleteMapping("/{id}/thay-nguoi/{maThayNguoi}")
    public ResponseEntity<Void> deleteThayNguoi(@PathVariable("id") Long id, @PathVariable("maThayNguoi") Long maThayNguoi) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        thayNguoiService.deleteThayNguoiByKetQuaAndId(ketQuaThiDau, maThayNguoi);
        return ResponseEntity.noContent().build();
    }
}
