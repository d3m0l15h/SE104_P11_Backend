package com.uit.backendapi.bxh;

import com.uit.backendapi.bxh.dto.BangXepHangMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bang-xep-hang")
public class BangXepHangController {
    private final BangXepHangService bangXepHangService;
    private final ModelMapper modelMapper;

    @GetMapping("/mua-giai/{namOrId}")
    public List<BangXepHangMuaGiaiDto> getBangXepHangByMuaGiai(@PathVariable("namOrId") String namOrId) {
        return bangXepHangService.getBangXepHangByMuaGiai(namOrId).stream()
                .map(bangXepHang -> modelMapper.map(bangXepHang, BangXepHangMuaGiaiDto.class))
                .toList();
    }

    @DeleteMapping("/{id}")
    public void deleteBangXepHang(@PathVariable("id") Long id) {
        bangXepHangService.deleteBangXepHang(id);
    }

}
