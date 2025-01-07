package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.FilterLichThiDauDto;
import com.uit.backendapi.lich.dto.LichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Tag(name = "Lich thi dau")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/lich-thi-dau")
public class LichThiDauController {
    private final LichThiDauService lichThiDauService;
    private final ModelMapper modelMapper;

    private LichThiDauDto toDto(LichThiDau lichThiDau) {
        return modelMapper.map(lichThiDau, LichThiDauDto.class);
    }

    @GetMapping
    @Operation(summary = "Get all lich thi dau")
    public ResponseEntity<Page<LichThiDauDto>> getAllLichThiDau(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<LichThiDau> lichThiDauPage = lichThiDauService.getAllLichThiDau(pageable);
        return ResponseEntity.ok(lichThiDauPage.map(this::toDto));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get lich thi dau by id")
    public ResponseEntity<LichThiDauDto> getLichThiDauById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(lichThiDauService.getLichThiDauById(id)));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter lich thi dau")
    public ResponseEntity<Page<LichThiDauDto>> filter(
            @RequestBody FilterLichThiDauDto filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        if (filter.getMaMuaGiai() == null) {
            throw new RuntimeException("Mua giai khong duoc de trong");
        }
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<LichThiDau> lichThiDauPage = lichThiDauService.filter(filter, pageable);
        return ResponseEntity.ok(lichThiDauPage.map(this::toDto));
    }

    @PostMapping("{muaGiaiId}")
    @Operation(summary = "Create lich thi dau", description = "Create LichThiDau with MuaGiai")
    public ResponseEntity<LichThiDauDto> createLichThiDau(@RequestBody CreateLichThiDauDto createLichThiDauDto, @PathVariable String muaGiaiId) throws BadRequestException {
        if (createLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new BadRequestException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if (createLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && createLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new BadRequestException("Gio thi dau phai lon hon gio hien tai");
        }

        if (createLichThiDauDto.getMaDoiNha().equals(createLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return ResponseEntity.ok(toDto(lichThiDauService.createLichThiDau(createLichThiDauDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update lich thi dau", description = "Cannot update LichThiDau if it already have KetQua")
    public ResponseEntity<LichThiDauDto> updateLichThiDau(@PathVariable("id") Long id, @RequestBody UpdateLichThiDauDto updateLichThiDauDto) {
        if (updateLichThiDauDto.getNgayThiDau() != null && updateLichThiDauDto.getNgayThiDau().isBefore(LocalDate.now())) {
            throw new RuntimeException("Ngay thi dau phai lon hon ngay hien tai");
        }

        if (updateLichThiDauDto.getNgayThiDau() != null && updateLichThiDauDto.getNgayThiDau().isEqual(LocalDate.now()) && updateLichThiDauDto.getGioThiDau().isBefore(LocalTime.now())) {
            throw new RuntimeException("Gio thi dau phai lon hon gio hien tai");
        }

        if (updateLichThiDauDto.getMaDoiNha() != null && updateLichThiDauDto.getMaDoiNha().equals(updateLichThiDauDto.getMaDoiKhach())) {
            throw new RuntimeException("Doi nha va doi khach phai khac nhau");
        }

        return ResponseEntity.ok(toDto(lichThiDauService.updateLichThiDau(id, updateLichThiDauDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete lich thi dau")
    public ResponseEntity<Void> deleteLichThiDau(@PathVariable("id") Long id) throws RuntimeException {
        return lichThiDauService.deleteLichThiDau(id);

    }
}
