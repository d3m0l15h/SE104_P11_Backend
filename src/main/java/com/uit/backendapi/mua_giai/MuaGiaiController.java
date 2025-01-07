package com.uit.backendapi.mua_giai;


import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.FilterMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
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

import java.util.Comparator;
import java.util.List;

@Tag(name = "Mua giai")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mua-giai")
public class MuaGiaiController {
    private final MuaGiaiService muaGiaiService;
    private final ModelMapper modelMapper;

    private MuaGiaiDto toDto(MuaGiai muaGiai) {
        return modelMapper.map(muaGiai, MuaGiaiDto.class);
    }

    @GetMapping
    @Operation(summary = "Get all mua giai")
    public Page<MuaGiaiDto> getAllMuaGiai(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        return  muaGiaiService.getAllMuaGiai(pageable).map(this::toDto);
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter mua giai with pagination")
    public ResponseEntity<Page<MuaGiaiDto>> filter(
            @RequestBody FilterMuaGiaiDto filterMuaGiaiDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<MuaGiai> muaGiaiPage = muaGiaiService.filter(filterMuaGiaiDto, pageable);
        return ResponseEntity.ok(muaGiaiPage.map(this::toDto));
    }

    @PostMapping
    @Operation(summary = "Create mua giai")
    public ResponseEntity<MuaGiaiDto> createMuaGiai(@RequestBody CreateMuaGiaiDto createMuaGiaiDto) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.createMuaGiai(createMuaGiaiDto)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update mua giai")
    public ResponseEntity<MuaGiaiDto> updateMuaGiai(@PathVariable("id") Long id, @RequestBody UpdateMuaGiaiDto updateMuaGiaiDto) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.updateMuaGiai(id, updateMuaGiaiDto)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get mua giai by id")
    public ResponseEntity<MuaGiaiDto> getMuaGiaiById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.getMuaGiaiById(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete mua giai")
    public ResponseEntity<MuaGiaiDto> deleteMuaGiai(@PathVariable("id") Long id) {
        muaGiaiService.deleteMuaGiai(id);
        return ResponseEntity.ok().build();
    }
}
