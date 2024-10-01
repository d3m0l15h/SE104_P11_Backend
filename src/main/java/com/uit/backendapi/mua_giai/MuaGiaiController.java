package com.uit.backendapi.mua_giai;


import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mua-giai")
public class MuaGiaiController {
    private final MuaGiaiService muaGiaiService;

    @GetMapping
    public List<MuaGiai> getAllMuaGiai() {
        return muaGiaiService.getAllMuaGiai();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuaGiai> getMuaGiaiById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(muaGiaiService.getMuaGiaiById(id));
    }

    @PostMapping
    public MuaGiai createMuaGiai(@RequestBody CreateMuaGiaiDto createMuaGiaiDto) {
        return muaGiaiService.createMuaGiai(createMuaGiaiDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MuaGiai> updateMuaGiai(@PathVariable("id") Long id, @RequestBody UpdateMuaGiaiDto updateMuaGiaiDto) {
        return ResponseEntity.ok().body(muaGiaiService.updateMuaGiai(id, updateMuaGiaiDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MuaGiai> deleteMuaGiai(@PathVariable("id") Long id) {
        muaGiaiService.deleteMuaGiai(id);
        return ResponseEntity.ok().build();
    }
}
