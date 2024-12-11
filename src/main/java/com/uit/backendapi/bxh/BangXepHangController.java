package com.uit.backendapi.bxh;

import com.uit.backendapi.bxh.dto.BangXepHangDto;
import com.uit.backendapi.bxh.dto.BangXepHangMuaGiaiDto;
import com.uit.backendapi.bxh.dto.FilterBangXepHangDto;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.dto.CauThuDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Bang Xep Hang")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bang-xep-hang")
public class BangXepHangController {
    private final BangXepHangService bangXepHangService;
    private final ModelMapper modelMapper;

    private BangXepHangDto toDto(BangXepHang bangXepHang) {
        return modelMapper.map(bangXepHang, BangXepHangDto.class);
    }

    @PostMapping("/filter")
    @Operation(summary = "Filter bang xep hang")
    public Page<BangXepHangDto> filter(
            @RequestBody FilterBangXepHangDto filterBangXepHangDto,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id,asc") String[] sort
    ) {
        Pageable pageable = PageRequest.of(page,
                size,
                Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<BangXepHang> bangXepHangPage = bangXepHangService.filter(filterBangXepHangDto, pageable);
        return bangXepHangPage.map(this::toDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete bang xep hang by id")
    public void deleteBangXepHang(@PathVariable("id") Long id) {
        bangXepHangService.deleteBangXepHang(id);
    }

}
