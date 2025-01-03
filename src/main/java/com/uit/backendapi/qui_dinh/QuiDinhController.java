package com.uit.backendapi.qui_dinh;

import com.uit.backendapi.qui_dinh.dto.UpsertQuiDinhDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Qui dinh")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/qui-dinh")
public class QuiDinhController {
    private final QuiDinhService quiDinhService;

    @GetMapping
    @Operation(summary = "Get all qui dinh")
    public List<QuiDinh> getQuiDinh() {
        return quiDinhService.getQuiDinh();
    }

    @PostMapping("/upsert")
    @Operation(summary = "Upsert qui dinh")
    public List<QuiDinh> upsertQuiDinh(@RequestBody UpsertQuiDinhDto upsertQuiDinhDto) {
        return quiDinhService.upsertQuiDinh(upsertQuiDinhDto);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete qui dinh")
    public void deleteQuiDinh(@PathVariable Long id) {
        quiDinhService.deleteQuiDinh(id);
    }
}
