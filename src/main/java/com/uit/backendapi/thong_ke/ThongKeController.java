package com.uit.backendapi.thong_ke;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.thong_ke.dto.CauThuXuatSacDto;
import com.uit.backendapi.thong_ke.dto.VuaPhaLuoiDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Thong ke")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/thong-ke")
public class ThongKeController {
    private final ThongKeService thongKeService;

    @GetMapping("/vua-pha-luoi/{muaGiaiId}")
    public List<VuaPhaLuoiDto> getVuaPhaLuoi(@PathVariable Long muaGiaiId) {
        return thongKeService.getVuaPhaLuoi(muaGiaiId);
    }

    @GetMapping("/cau-thu-xuat-sac/{muaGiaiId}")
    public List<CauThuXuatSacDto> getCauThuXuatSac(@PathVariable Long muaGiaiId) {
        return thongKeService.getCauThuXuatSac(muaGiaiId);
    }
}
