package com.uit.backendapi.lich;

import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.FilterLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LichThiDauService implements ILichThiDauService {
    private final LichThiDauRepository lichThiDauRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;

    @Override
    public Page<LichThiDau> getAllLichThiDau(Pageable pageable) {
        return lichThiDauRepository.findAll(pageable);
    }

    @Override
    public LichThiDau getLichThiDauById(Long id) {
        return lichThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found with id: " + id)
        );
    }

    @Override
    public Page<LichThiDau> filter(FilterLichThiDauDto filter, Pageable pageable) {
        List<LichThiDau> lichThiDaus = lichThiDauRepository.findAll(pageable).stream()
                .filter(dto -> filter.getVongThiDau() == null || dto.getVongThiDau().equals(filter.getVongThiDau()))
                .filter(dto -> filter.getNgayThiDau() == null || dto.getNgayThiDau().equals(filter.getNgayThiDau()))
                .filter(dto -> filter.getSanThiDau() == null || dto.getSanThiDau().equals(filter.getSanThiDau()))
                .filter(dto -> filter.getMaDoiNha() == null
                        || (dto.getMaDoiNha() != null && dto.getMaDoiNha().getId().equals(filter.getMaDoiNha())))
                .filter(dto -> filter.getMaDoiKhach() == null
                        || dto.getMaDoiKhach().getId().equals(filter.getMaDoiKhach()))
                .filter(dto -> filter.getMaMuaGiai() == null
                        || dto.getMaMuaGiai().getId().equals(filter.getMaMuaGiai()))
                .toList();

        return new PageImpl<>(lichThiDaus, pageable, lichThiDaus.size());
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
        if (existingLichThiDau.getKetQuaThiDau() != null) {
            throw new RuntimeException("LichThiDau already have KetQuaThiDau");
        }

        Utils.copyNonNullProperties(updateLichThiDauDto, existingLichThiDau, "id", "maDoiNha", "maDoiKhach", "maMuaGiai");

        if (updateLichThiDauDto.getMaDoiNha() != null) {
            // Check if doi bong exists then update
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

    public ResponseEntity<Void> deleteLichThiDau(Long id) {
        LichThiDau lichThiDau = lichThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found with id: " + id)
        );

        if (lichThiDau.getKetQuaThiDau() != null) {
            throw new RuntimeException("Cannot delete LichThiDau if it already have KetQua");
        }

        lichThiDauRepository.findById(id).ifPresentOrElse(
                lichThiDauRepository::delete,
                () -> {
                    throw new RuntimeException("Lich thi dau not found with id: " + id);
                }
        );

        return null;
    }
}
