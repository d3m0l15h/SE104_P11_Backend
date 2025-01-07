package com.uit.backendapi.thong_ke;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.ban_thang.BanThangRepository;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.ket_qua.KetQuaThiDauRepository;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import com.uit.backendapi.thong_ke.dto.CauThuXuatSacDto;
import com.uit.backendapi.thong_ke.dto.VuaPhaLuoiDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThongKeService {
    private final MuaGiaiRepository muaGiaiRepository;
    private final CauThuRepository cauThuRepository;
    private final BanThangRepository banThangRepository;
    private final KetQuaThiDauRepository ketQuaThiDauRepository;
    private final ModelMapper modelMapper;

    public List<VuaPhaLuoiDto> getVuaPhaLuoi(Long muaGiaiId) {
        MuaGiai muaGiai = muaGiaiRepository.findById(muaGiaiId).orElseThrow(
                () -> new RuntimeException("Mua giai not found with id: " + muaGiaiId)
        );
        List<BanThang> banThangs = ketQuaThiDauRepository.findAll().stream()
                .filter(ketQuaThiDau -> ketQuaThiDau.getLichThiDau().getMaMuaGiai().getId() == muaGiaiId.intValue())
                .flatMap(ketQuaThiDau -> banThangRepository.findBanThangByMaKetQua(ketQuaThiDau).stream())
                .filter(banThang -> !"C".equals(banThang.getLoaiBanThang())) // Filter out loaiBanThang = "C"
                .toList();

        Map<CauThu, Integer> cauThuBanThangCount = new HashMap<>();
        for (BanThang banThang : banThangs) {
            CauThu cauThu = banThang.getMaCauThu();
            cauThuBanThangCount.put(cauThu, cauThuBanThangCount.getOrDefault(cauThu, 0) + 1);
        }

        return cauThuBanThangCount.entrySet().stream()
                .map(entry -> new VuaPhaLuoiDto(
                        modelMapper.map(muaGiai, MuaGiaiSimpleDto.class),
                        modelMapper.map(entry.getKey(), CauThuSimpleDto.class),
                        entry.getValue(),
                        modelMapper.map(entry.getKey().getMaDoi(), DoiBongSimpleDto.class)
                ))
                .sorted((a, b) -> b.getSoBanThang().compareTo(a.getSoBanThang()))
                .collect(Collectors.toList());
    }

    public List<CauThuXuatSacDto> getCauThuXuatSac(Long muaGiaiId) {
        MuaGiai muaGiai = muaGiaiRepository.findById(muaGiaiId).orElseThrow(
                () -> new RuntimeException("Mua giai not found with id: " + muaGiaiId)
        );
        List<KetQuaThiDau> ketQuaThiDaus = ketQuaThiDauRepository.findAll().stream()
                .filter(ketQuaThiDau -> ketQuaThiDau.getLichThiDau().getMaMuaGiai().getId() == muaGiaiId.intValue())
                .toList();

        Map<CauThu, Integer> cauThuBinhChonCount = new HashMap<>();
        for (KetQuaThiDau ketQuaThiDau : ketQuaThiDaus) {
            CauThu cauThuXuatSac = ketQuaThiDau.getCauThuXuatSac();
            if (cauThuXuatSac != null) {
                cauThuBinhChonCount.put(cauThuXuatSac, cauThuBinhChonCount.getOrDefault(cauThuXuatSac, 0) + 1);
            }
        }

        return cauThuBinhChonCount.entrySet().stream()
                .map(entry -> {
                    CauThu cauThu = entry.getKey();
                    return new CauThuXuatSacDto(
                            modelMapper.map(cauThu, CauThuSimpleDto.class),
                            modelMapper.map(cauThu.getMaDoi(), DoiBongSimpleDto.class),
                            modelMapper.map(muaGiai, MuaGiaiSimpleDto.class),
                            entry.getValue()
                    );
                })
                .sorted((a, b) -> Integer.compare(b.getSoLanBinhChon(), a.getSoLanBinhChon()))
                .collect(Collectors.toList());
    }
}
