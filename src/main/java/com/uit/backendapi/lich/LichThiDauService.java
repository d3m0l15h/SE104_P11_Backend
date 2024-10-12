package com.uit.backendapi.lich;

import com.uit.backendapi.Utils;
import com.uit.backendapi.bxh.BangXepHangService;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LichThiDauService implements ILichThiDauService {
    private final LichThiDauRepository lichThiDauRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;
    private final BangXepHangService bangXepHangService;

    @Override
    public List<LichThiDau> getAllLichThiDau() {
        return lichThiDauRepository.findAll();
    }

    @Override
    public LichThiDau getLichThiDauById(Long id) {
        return lichThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found with id: " + id)
        );
    }

    public List<LichThiDau> getLichThiDauByMuaGiai(String nam, String vongThiDau, Long maDoiBong) {
        MuaGiai muaGiai = muaGiaiRepository.findByNamOrId(nam, Integer.valueOf(nam)).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found: " + nam)
        );

        DoiBong doiBong = null;
        if (maDoiBong != null) {
            doiBong = doiBongRepository.findById(maDoiBong).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found with id: " + maDoiBong)
            );
        }

        return lichThiDauRepository.findByMaDoiNhaOrMaDoiKhachOrVongThiDauOrMaMuaGiai(
                doiBong, doiBong, vongThiDau, muaGiai);
    }

    public List<LichThiDau> getLichThiDauByNgayThiDau(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd) {
        return lichThiDauRepository.findByNgayThiDauBetween(ngayThiDauStart, ngayThiDauEnd);
    }

    @Override
    public LichThiDau createLichThiDau(CreateLichThiDauDto lichThiDauDto) {
        DoiBong doiNha = doiBongRepository.findById(lichThiDauDto.getMaDoiNha()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found with id: " + lichThiDauDto.getMaDoiNha())
        );

        DoiBong doiKhach = doiBongRepository.findById(lichThiDauDto.getMaDoiKhach()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found with id: " + lichThiDauDto.getMaDoiKhach())
        );

        MuaGiai muaGiai = muaGiaiRepository.findById(lichThiDauDto.getMaMuaGiai()).orElseThrow(
                () -> new ResourceNotFoundException("Mua giai not found with id: " + lichThiDauDto.getMaMuaGiai())
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

        if (bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(doiNha, muaGiai) == null) {
            bangXepHangService.createBangXepHang(doiNha, muaGiai);
        }

        if (bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(doiKhach, muaGiai) == null) {
            bangXepHangService.createBangXepHang(doiKhach, muaGiai);
        }

        return lichThiDauRepository.save(lichThiDau);
    }

    @Override
    public LichThiDau updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto) {
        return lichThiDauRepository.findById(id)
                .map(existingLichThiDau -> updateExistingLichThiDau(existingLichThiDau, updateLichThiDauDto))
                .map(lichThiDauRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Lich thi dau not found with id: " + id)
                );
    }

    private LichThiDau updateExistingLichThiDau(LichThiDau existingLichThiDau, UpdateLichThiDauDto updateLichThiDauDto) {
        if(existingLichThiDau.getKetQuaThiDau() != null) {
            throw new RuntimeException("Lich thi dau da co ket qua thi dau");
        }

        Utils.copyNonNullProperties(updateLichThiDauDto, existingLichThiDau, "id", "maDoiNha", "maDoiKhach", "maMuaGiai");

        if (updateLichThiDauDto.getMaDoiNha() != null) {
            existingLichThiDau.setMaDoiNha(doiBongRepository.findById(updateLichThiDauDto.getMaDoiNha()).orElseThrow(
                    () -> new RuntimeException("Doi bong not found with id: " + updateLichThiDauDto.getMaDoiNha())
            ));

            if (bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(existingLichThiDau.getMaDoiNha(), existingLichThiDau.getMaMuaGiai()) == null) {
                bangXepHangService.createBangXepHang(existingLichThiDau.getMaDoiNha(), existingLichThiDau.getMaMuaGiai());
            }
        }

        if (updateLichThiDauDto.getMaDoiKhach() != null) {
            existingLichThiDau.setMaDoiKhach(doiBongRepository.findById(updateLichThiDauDto.getMaDoiKhach()).orElseThrow(
                    () -> new RuntimeException("Doi bong not found with id: " + updateLichThiDauDto.getMaDoiKhach())
            ));

            if (bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(existingLichThiDau.getMaDoiKhach(), existingLichThiDau.getMaMuaGiai()) == null) {
                bangXepHangService.createBangXepHang(existingLichThiDau.getMaDoiKhach(), existingLichThiDau.getMaMuaGiai());
            }
        }

        if (updateLichThiDauDto.getMaMuaGiai() != null) {
            existingLichThiDau.setMaMuaGiai(muaGiaiRepository.findById(updateLichThiDauDto.getMaMuaGiai()).orElseThrow(
                    () -> new RuntimeException("Mua giai not found with id: " + updateLichThiDauDto.getMaMuaGiai())
            ));

            if(bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(existingLichThiDau.getMaDoiNha(), existingLichThiDau.getMaMuaGiai()) == null) {
                bangXepHangService.createBangXepHang(existingLichThiDau.getMaDoiNha(), existingLichThiDau.getMaMuaGiai());
            }

            if(bangXepHangService.getBangXepHangByMaDoiAndMuaGiai(existingLichThiDau.getMaDoiKhach(), existingLichThiDau.getMaMuaGiai()) == null) {
                bangXepHangService.createBangXepHang(existingLichThiDau.getMaDoiKhach(), existingLichThiDau.getMaMuaGiai());
            }
        }

        return existingLichThiDau;
    }

    public void deleteLichThiDau(Long id) {
        lichThiDauRepository.findById(id).ifPresentOrElse(
                lichThiDauRepository::delete,
                () -> {
                    throw new RuntimeException("Lich thi dau not found with id: " + id);
                }
        );
    }
}
