package com.uit.backendapi.lich;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LichThiDauService implements ILichThiDauService {
    private final LichThiDauRepository lichThiDauRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;

    @Override
    public List<LichThiDau> getAllLichThiDau() {
        return lichThiDauRepository.findAll();
    }

    @Override
    public LichThiDau getLichThiDauById(Long id) {
        return lichThiDauRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Lich thi dau not found with id: " + id)
        );
    }

    public List<LichThiDau> getLichThiDauByDoiBongOrMuaGiai(Long maDoiBong, Long maMuaGiai) {
        DoiBong doiBong = doiBongRepository.findById(maDoiBong).orElseThrow(
                () -> new RuntimeException("Doi bong not found with id: " + maDoiBong)
        );

        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElse(null);

        return lichThiDauRepository.findByMaDoiNhaOrMaDoiKhachOrMaMuaGiai(doiBong, doiBong, muaGiai);
    }

    public List<LichThiDau> getLichThiDauByVongThiDauAndMaMuaGiai(String vongThiDau, Long maMuaGiai) {
        MuaGiai muaGiai = muaGiaiRepository.findById(maMuaGiai).orElseThrow(
                () -> new RuntimeException("Mua giai not found with id: " + maMuaGiai)
        );

        return lichThiDauRepository.findByVongThiDauAndMaMuaGiai(vongThiDau, muaGiai);
    }

    public List<LichThiDau> getLichThiDauByMaMuaGiai(String nam) {
        MuaGiai muaGiai = muaGiaiRepository.findByNamContaining(nam);
        return lichThiDauRepository.findByMaMuaGiai(muaGiai);
    }

    public List<LichThiDau> getLichThiDauByNgayThiDau(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd) {
        return lichThiDauRepository.findByNgayThiDauBetween(ngayThiDauStart, ngayThiDauEnd);
    }

    public List<LichThiDau> getLichThiDauByNgayThiDauAndGioThiDau(LocalDate ngayThiDau, LocalTime gioThiDau) {
        return lichThiDauRepository.findByNgayThiDauAndGioThiDau(ngayThiDau, gioThiDau);
    }

    @Override
    public LichThiDau createLichThiDau(CreateLichThiDauDto lichThiDauDto) {
        DoiBong doiNha = doiBongRepository.findById(lichThiDauDto.getMaDoiNha()).orElseThrow(
                () -> new RuntimeException("Doi bong not found with id: " + lichThiDauDto.getMaDoiNha())
        );

        DoiBong doiKhach = doiBongRepository.findById(lichThiDauDto.getMaDoiKhach()).orElseThrow(
                () -> new RuntimeException("Doi bong not found with id: " + lichThiDauDto.getMaDoiKhach())
        );

        MuaGiai muaGiai = muaGiaiRepository.findById(lichThiDauDto.getMaMuaGiai()).orElseThrow(
                () -> new RuntimeException("Mua giai not found with id: " + lichThiDauDto.getMaMuaGiai())
        );

        LichThiDau lichThiDau = new LichThiDau(
                lichThiDauDto.getVongThiDau(),
                lichThiDauDto.getNgayThiDau(),
                lichThiDauDto.getGioThiDau(),
                lichThiDauDto.getSanThiDau(),
                doiNha,
                doiKhach,
                muaGiai
        );

        return lichThiDauRepository.save(lichThiDau);
    }

    @Override
    public LichThiDau updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto) {
        return lichThiDauRepository.findById(id)
                .map(existingLichThiDau -> updateExistingLichThiDau(existingLichThiDau, updateLichThiDauDto))
                .map(lichThiDauRepository::save)
                .orElseThrow(() -> new RuntimeException("Lich thi dau not found with id: " + id)
        );
    }

    private LichThiDau updateExistingLichThiDau(LichThiDau existingLichThiDau, UpdateLichThiDauDto updateLichThiDauDto) {
        BeanUtils.copyProperties(updateLichThiDauDto, existingLichThiDau, "id", "maDoiNha", "maDoiKhach", "maMuaGiai");

        if (updateLichThiDauDto.getMaDoiNha() != null) {
            existingLichThiDau.setMaDoiNha(doiBongRepository.findById(updateLichThiDauDto.getMaDoiNha()).orElseThrow(
                    () -> new RuntimeException("Doi bong not found with id: " + updateLichThiDauDto.getMaDoiNha())
            ));
        }

        if (updateLichThiDauDto.getMaDoiKhach() != null) {
            existingLichThiDau.setMaDoiKhach(doiBongRepository.findById(updateLichThiDauDto.getMaDoiKhach()).orElseThrow(
                    () -> new RuntimeException("Doi bong not found with id: " + updateLichThiDauDto.getMaDoiKhach())
            ));
        }

        if (updateLichThiDauDto.getMaMuaGiai() != null) {
            existingLichThiDau.setMaMuaGiai(muaGiaiRepository.findById(updateLichThiDauDto.getMaMuaGiai()).orElseThrow(
                    () -> new RuntimeException("Mua giai not found with id: " + updateLichThiDauDto.getMaMuaGiai())
            ));
        }

        return existingLichThiDau;
    }

    public void deleteLichThiDau(Long id) {
        lichThiDauRepository.deleteById(id);
    }
}
