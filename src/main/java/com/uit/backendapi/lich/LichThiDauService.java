package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class LichThiDauService {
    private final LichThiDauRepository lichThiDauRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;

    @Autowired
    public LichThiDauService(LichThiDauRepository lichThiDauRepository, DoiBongRepository doiBongRepository, MuaGiaiRepository muaGiaiRepository) {
        this.lichThiDauRepository = lichThiDauRepository;
        this.doiBongRepository = doiBongRepository;
        this.muaGiaiRepository = muaGiaiRepository;
    }

    public List<LichThiDau> findAllLichThiDau() {
        return lichThiDauRepository.findAll();
    }

    public LichThiDau findLichThiDauById(Long id) {
        return lichThiDauRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Lich thi dau not found with id: " + id)
        );
    }

    public List<LichThiDau> findLichThiDauByDoiBongOrMuaGiai(Long maDoiBong, Long maMuaGiai) {
        DoiBong doiBong = doiBongRepository.findById(maDoiBong).orElseThrow(
                () -> new RuntimeException("Doi bong not found with id: " + maDoiBong)
        );

        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElse(null);

        return lichThiDauRepository.findByMaDoiNhaOrMaDoiKhachOrMaMuaGiai(doiBong, doiBong, muaGiai);
    }

    public List<LichThiDau> findLichThiDauByVongThiDauAndMaMuaGiai(String vongThiDau, Long maMuaGiai) {
        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElseThrow(
                () -> new RuntimeException("Mua giai not found with id: " + maMuaGiai)
        );

        return lichThiDauRepository.findByVongThiDauAndMaMuaGiai(vongThiDau, muaGiai);
    }

    public List<LichThiDau> findLichThiDauByMaMuaGiai(String nam) {
        MuaGiai muaGiai = muaGiaiRepository.findByNamContaining(nam);
        return lichThiDauRepository.findByMaMuaGiai(muaGiai);
    }

    public List<LichThiDau> findLichThiDauByNgayThiDau(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd) {
        return lichThiDauRepository.findByNgayThiDauBetween(ngayThiDauStart, ngayThiDauEnd);
    }

    public List<LichThiDau> findLichThiDauByNgayThiDauAndGioThiDau(LocalDate ngayThiDau, LocalTime gioThiDau) {
        return lichThiDauRepository.findByNgayThiDauAndGioThiDau(ngayThiDau, gioThiDau);
    }

    public LichThiDau createLichThiDau(LichThiDau lichThiDau) {
        return lichThiDauRepository.save(lichThiDau);
    }

    public LichThiDau updateLichThiDau(Long id, LichThiDau lichThiDauDto) {
        return lichThiDauRepository.findById(id)
                .map(lichThiDau -> {
                    lichThiDau.setVongThiDau(lichThiDauDto.getVongThiDau());
                    lichThiDau.setNgayThiDau(lichThiDauDto.getNgayThiDau());
                    lichThiDau.setGioThiDau(lichThiDauDto.getGioThiDau());
                    lichThiDau.setSanThiDau(lichThiDauDto.getSanThiDau());
                    lichThiDau.setMaDoiNha(lichThiDauDto.getMaDoiNha());
                    lichThiDau.setMaDoiKhach(lichThiDauDto.getMaDoiKhach());
                    return lichThiDauRepository.save(lichThiDau);
                })
                .orElseGet(() -> lichThiDauRepository.save(lichThiDauDto));
    }

    public void deleteLichThiDau(Long id) {
        lichThiDauRepository.deleteById(id);
    }
}
