package com.uit.backendapi.mua_giai;


import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<MuaGiaiDto> getAllMuaGiai() {
        return muaGiaiService.getAllMuaGiai().stream().map(this::toDto).toList();
    }

    @GetMapping("/{namOrId}")
    public ResponseEntity<MuaGiaiDto> getMuaGiaiByNamOrId(@PathVariable("namOrId") String namOrId) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.getMuaGiaiByNamOrId(namOrId)));
    }

    @PostMapping
    public ResponseEntity<MuaGiaiDto> createMuaGiai(@RequestBody CreateMuaGiaiDto createMuaGiaiDto) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.createMuaGiai(createMuaGiaiDto)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MuaGiaiDto> updateMuaGiai(@PathVariable("id") Long id, @RequestBody UpdateMuaGiaiDto updateMuaGiaiDto) {
        return ResponseEntity.ok().body(toDto(muaGiaiService.updateMuaGiai(id, updateMuaGiaiDto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MuaGiai> deleteMuaGiai(@PathVariable("id") Long id) {
        muaGiaiService.deleteMuaGiai(id);
        return ResponseEntity.ok().build();
    }
}
