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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Tag(name = "Ket qua")
    @Operation(summary = "Get all ket qua thi dau")
    public ResponseEntity<Page<KetQuaThiDauDto>> getAllKetQuaThiDau(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of( page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<KetQuaThiDau> ketQuaThiDauPage = ketQuaThiDauService.getAllKetQuaThiDau(pageable);
        return ResponseEntity.ok(ketQuaThiDauPage.map(this::toKetQuaThiDauDto));
    }

    @GetMapping("/{id}")
    @Tag(name = "Ket qua")
    @Operation(summary = "Get ket qua thi dau by id")
    public ResponseEntity<KetQuaThiDauDto> getKetQuaThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.getKetQuaThiDauById(id)));
    }

    @PostMapping()
    @Tag(name = "Ket qua")
    @Operation(summary = "Create ket qua thi dau")
    public ResponseEntity<KetQuaThiDauDto> createKetQuaThiDau(@RequestBody CreateKetQuaThiDauDto createKetQuaThiDauDto) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.createKetQuaThiDau(createKetQuaThiDauDto)));
    }

    @PutMapping("/{id}")
    @Tag(name = "Ket qua")
    @Operation(summary = "Update ket qua thi dau")
    public ResponseEntity<KetQuaThiDauDto> updateKetQuaThiDau(@PathVariable("id") Long id, @RequestBody UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        return ResponseEntity.ok(toKetQuaThiDauDto(ketQuaThiDauService.updateKetQuaThiDau(id, updateKetQuaThiDauDto)));
    }

    @DeleteMapping("/{id}")
    @Tag(name = "Ket qua")
    @Operation(summary = "Delete ket qua thi dau")
    public ResponseEntity<Void> deleteKetQuaThiDau(@PathVariable("id") Long id) {
        ketQuaThiDauService.deleteKetQuaThiDau(id);
        return ResponseEntity.noContent().build();
    }

    //---------------------------------Ban Thang----------------------------------------
    @GetMapping("/{id}/ban-thang")
    @Tag(name = "Ban thang")
    @Operation(summary = "Get all ban thang by ket qua")
    public ResponseEntity<List<BanThangDto>> getBanThangByKetQua(@PathVariable("id") Long id) {
        return ResponseEntity.ok(banThangService.getBanThangByKetQua(id).stream().map(this::toBanThangDto).toList());
    }

    @PostMapping("/{id}/ban-thang")
    @Tag(name = "Ban thang")
    @Operation(summary = "Create ban thang")
    public ResponseEntity<BanThangDto> createBanThang(@PathVariable("id") Long id, @RequestBody CreateBanThangDto createBanThangDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toBanThangDto(banThangService.createBanThangByKetQua(ketQuaThiDau, createBanThangDto)));
    }

//    @PutMapping("/{id}/ban-thang/{maBanThang}")
//    @Tag(name = "Ban thang")
//    @Operation(summary = "Update ban thang")
//    public ResponseEntity<BanThangDto> updateBanThang(@PathVariable("id") Long id,
//                                                   @PathVariable("maBanThang") Long maBanThang,
//                                                   @RequestBody UpdateBanThangDto updateBanThangDto) {
//        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
//        return ResponseEntity.ok(toBanThangDto(banThangService.updateBanThangByKetQuaAndId(ketQuaThiDau, maBanThang, updateBanThangDto)));
//    }

    @DeleteMapping("/{id}/ban-thang/{maBanThang}")
    @Tag(name = "Ban thang")
    @Operation(summary = "Delete ban thang")
    public ResponseEntity<Void> deleteBanThang(@PathVariable("id") Long id, @PathVariable("maBanThang") Long maBanThang) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        banThangService.deleteBanThangByKetQuaAndId(ketQuaThiDau, maBanThang);
        return ResponseEntity.noContent().build();
    }

    //-------------------------------The phat--------------------------------
    @GetMapping("/{id}/the-phat")
    @Tag(name = "The phat")
    @Operation(summary = "Get all the phat by ket qua")
    public ResponseEntity<List<ThePhatDto>> getThePhatByKetQua(@PathVariable("id") Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(thePhatService.getThePhatByMaKetQua(ketQuaThiDau).stream().map(this::toThePhatDto).toList());
    }

    @PostMapping("/{id}/the-phat")
    @Tag(name = "The phat")
    @Operation(summary = "Create the phat")
    public ResponseEntity<ThePhatDto> createThePhat(@PathVariable("id") Long id, @RequestBody CreateThePhatDto createThePhatDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThePhatDto(thePhatService.createThePhatByMaKetQua(ketQuaThiDau, createThePhatDto)));
    }

//    @PutMapping("/{id}/the-phat/{maThePhat}")
//    @Tag(name = "The phat")
//    @Operation(summary = "Update the phat")
//    public ResponseEntity<ThePhatDto> updateThePhat(@PathVariable("id") Long id,
//                                                @PathVariable("maThePhat") Long maThePhat,
//                                                @RequestBody UpdateThePhatDto updateThePhatDto)
//    {
//        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
//        return ResponseEntity.ok(toThePhatDto(thePhatService.updateThePhatByMaKetQuaAndId(ketQuaThiDau, maThePhat, updateThePhatDto)));
//    }

    @DeleteMapping("/{id}/the-phat/{maThePhat}")
    @Tag(name = "The phat")
    @Operation(summary = "Delete the phat")
    public ResponseEntity<Void> deleteThePhat(@PathVariable("id") Long id, @PathVariable("maThePhat") Long maThePhat) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        thePhatService.deleteThePhatByMaKetQuaAndId(ketQuaThiDau, maThePhat);
        return ResponseEntity.noContent().build();
    }

    //-------------------------------Thay nguoi--------------------------------
    @GetMapping("/{id}/thay-nguoi")
    @Tag(name = "Thay nguoi")
    @Operation(summary = "Get all thay nguoi by ket qua")
    public ResponseEntity<List<ThayNguoiDto>> getThayNguoiByKetQua(@PathVariable("id") Long id) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(thayNguoiService.getThayNguoiByKetQua(ketQuaThiDau).stream().map(this::toThayNguoiDto).toList());
    }

    @PostMapping("/{id}/thay-nguoi")
    @Tag(name = "Thay nguoi")
    @Operation(summary = "Create thay nguoi")
    public ResponseEntity<ThayNguoiDto> createThayNguoi(@PathVariable("id") Long id, @RequestBody CreateThayNguoiDto createThayNguoiDto) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        return ResponseEntity.ok(toThayNguoiDto(thayNguoiService.createThayNguoiByKetQua(ketQuaThiDau, createThayNguoiDto)));
    }

//    @PutMapping("/{id}/thay-nguoi/{maThayNguoi}")
//    @Tag(name = "Thay nguoi")
//    @Operation(summary = "Update thay nguoi")
//    public ResponseEntity<ThayNguoiDto> updateThayNguoi(@PathVariable("id") Long id,
//                                                     @PathVariable("maThayNguoi") Long maThayNguoi,
//                                                     @RequestBody UpdateThayNguoiDto updateThayNguoiDto) {
//        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
//        return ResponseEntity.ok(toThayNguoiDto(thayNguoiService.updateThayNguoiByKetQuaAndId(ketQuaThiDau, maThayNguoi, updateThayNguoiDto)));
//    }

    @DeleteMapping("/{id}/thay-nguoi/{maThayNguoi}")
    @Tag(name = "Thay nguoi")
    @Operation(summary = "Delete thay nguoi")
    public ResponseEntity<Void> deleteThayNguoi(@PathVariable("id") Long id, @PathVariable("maThayNguoi") Long maThayNguoi) {
        KetQuaThiDau ketQuaThiDau = ketQuaThiDauService.getKetQuaThiDauById(id);
        thayNguoiService.deleteThayNguoiByKetQuaAndId(ketQuaThiDau, maThayNguoi);
        return ResponseEntity.noContent().build();
    }
}
