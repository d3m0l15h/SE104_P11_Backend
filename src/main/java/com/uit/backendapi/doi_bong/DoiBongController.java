package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import com.uit.backendapi.doi_bong.dto.FilterDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Tag(name = "Doi bong")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doi-bong")
public class DoiBongController {
    private final DoiBongService doiBongService;
    private final ModelMapper modelMapper;

    private DoiBongDto toDto(DoiBong doiBong) {
        return modelMapper.map(doiBong, DoiBongDto.class);
    }

    @PostMapping(value = "/filter")
    @Operation(summary = "Filter doi bong")
    public ResponseEntity<Page<DoiBongDto>> filter(
            @ModelAttribute FilterDoiBongDto filterDoiBongDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<DoiBong> doiBongPage = doiBongService.filter(filterDoiBongDto, pageable);
        return ResponseEntity.ok(doiBongPage.map(this::toDto));
    }

    @GetMapping
    @Operation(summary = "Get all doi bong")
    public ResponseEntity<Page<DoiBongDto>> getAllDoiBong(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<DoiBong> doiBongPage = doiBongService.getAllDoiBong(pageable);
        return ResponseEntity.ok(doiBongPage.map(this::toDto));
    }

    @GetMapping("/mua-giai/{id}")
    @Operation(summary = "Get doi bong by mua giai id")
    public ResponseEntity<List<DoiBongDto>> getDoiBongByMuaGiaiId(
            @PathVariable("id") Long id
    ) {

        return ResponseEntity.ok(doiBongService.getDoiBongByMuaGiaiId(id).stream()
                .map(this::toDto)
                .toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get doi bong by id")
    public ResponseEntity<DoiBongDto> getDoiBongById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(toDto(doiBongService.getDoiBongById(id)));
    }

    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Create doi bong")
    @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = CreateDoiBongDto.class)))
    public ResponseEntity<DoiBongDto> createDoiBong(@ModelAttribute CreateDoiBongDto createDoiBongDto) throws IOException {
        return ResponseEntity.ok(toDto(doiBongService.createDoiBong(createDoiBongDto)));
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    @Operation(summary = "Update doi bong")
    @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = UpdateDoiBongDto.class)))
    public ResponseEntity<DoiBongDto> updateDoiBong(@PathVariable("id") Long id, @ModelAttribute UpdateDoiBongDto updateDoiBongDto) throws IOException {
        return ResponseEntity.ok(toDto(doiBongService.updateDoiBong(id, updateDoiBongDto)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete doi bong")
    public ResponseEntity<Void> deleteDoiBong(@PathVariable Long id) {
        doiBongService.deleteDoiBong(id);
        return ResponseEntity.noContent().build();
    }
}
