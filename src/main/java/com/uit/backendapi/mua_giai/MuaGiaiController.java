package com.uit.backendapi.mua_giai;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mua-giai")
public class MuaGiaiController {

    private final MuaGiaiService muaGiaiService;

    @Autowired
    public MuaGiaiController(MuaGiaiService muaGiaiService) {
        this.muaGiaiService = muaGiaiService;
    }

    @GetMapping
    public List<MuaGiai> getAllMuaGiai() {
        return muaGiaiService.getAllMuaGiai();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MuaGiai> getMuaGiaiById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(muaGiaiService.getMuaGiaiById(id));
    }

    @PostMapping
    public MuaGiai createMuaGiai(@RequestBody MuaGiai muaGiai) {
        return muaGiaiService.createMuaGiai(muaGiai);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MuaGiai> updateMuaGiai(@PathVariable(value = "id") Long id, @RequestBody MuaGiai muaGiai) {
        return ResponseEntity.ok().body(muaGiaiService.updateMuaGiai(id, muaGiai));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MuaGiai> deleteMuaGiai(@PathVariable(value = "id") Long id) {
        muaGiaiService.deleteMuaGiai(id);
        return ResponseEntity.ok().build();
    }
}
