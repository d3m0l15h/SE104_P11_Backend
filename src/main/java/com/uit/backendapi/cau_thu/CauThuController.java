package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.FilterCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Cau thu")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cau-thu")
public class CauThuController {
    private final CauThuService cauThuService;
    private final ModelMapper modelMapper;

    private CauThuDto toDto(CauThu cauThu) {
        return modelMapper.map(cauThu, CauThuDto.class);
    }

    @GetMapping
    @Operation(summary = "Get all cau thu")
    public ResponseEntity<Page<CauThuDto>> getAllCauThu(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of( page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<CauThu> cauThuPage = cauThuService.getAllCauThu(pageable);
        return ResponseEntity.ok(cauThuPage.map(this::toDto));
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter cau thu")
    public ResponseEntity<Page<CauThuDto>> filter(
            @ModelAttribute FilterCauThuDto filterCauThuDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<CauThu> cauThuPage = cauThuService.filter(filterCauThuDto, pageable);
        return ResponseEntity.ok(cauThuPage.map(this::toDto));
    }

    @GetMapping("/lich-thi-dau/{id}")
    @Operation(summary = "Get lich thi dau by id cau thu")
    public ResponseEntity<List<CauThuDto>> getLichThiDauByIdCauThu(
            @PathVariable("id") Long id
    ) {
        List<CauThu> cauThus = cauThuService.getCauThuByLichThiDauId(id);
        return ResponseEntity.ok(cauThus.stream().map(this::toDto).toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get cau thu by id")
    public ResponseEntity<CauThuDto> getCauThuById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(cauThuService.getCauThuById(id)));
    }

    @PostMapping(consumes = "multipart/form-data")
    @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = CreateCauThuDto.class)))
    @Operation(summary = "Create cau thu")
    public ResponseEntity<CauThuDto> createCauThu(@ModelAttribute CreateCauThuDto createCauThuDto) throws IOException {
        return ResponseEntity.ok(toDto(cauThuService.createCauThu(createCauThuDto)));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = UpdateCauThuDto.class)))
    @Operation(summary = "Update cau thu")
    public ResponseEntity<CauThuDto> updateCauThu(@PathVariable("id") Long id, @ModelAttribute UpdateCauThuDto updateCauThuDto) {
        return ResponseEntity.ok(toDto(cauThuService.updateCauThu(id, updateCauThuDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cau thu")
    public ResponseEntity<Void> deleteCauThu(@PathVariable("id") Long id) {
        cauThuService.deleteCauThu(id);
        return ResponseEntity.noContent().build();
    }
}
