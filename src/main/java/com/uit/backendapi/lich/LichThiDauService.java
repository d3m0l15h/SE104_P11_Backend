package com.uit.backendapi.lich;

import com.uit.backendapi.Utils;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.doi_bong.dto.DoiBongLichThiDauDto;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.LichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import com.uit.backendapi.mua_giai.MuaGiai;
import com.uit.backendapi.mua_giai.MuaGiaiRepository;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LichThiDauService implements ILichThiDauService {
    private final LichThiDauRepository lichThiDauRepository;
    private final DoiBongRepository doiBongRepository;
    private final MuaGiaiRepository muaGiaiRepository;
    private final ModelMapper modelMapper;

    private LichThiDauDto toDto(LichThiDau lichThiDau) {
        LichThiDauDto lichThiDauDto = modelMapper.map(lichThiDau, LichThiDauDto.class);
        lichThiDauDto.setMaDoiNha(modelMapper.map(lichThiDau.getMaDoiNha(), DoiBongLichThiDauDto.class));
        lichThiDauDto.setMaDoiKhach(modelMapper.map(lichThiDau.getMaDoiKhach(), DoiBongLichThiDauDto.class));
        lichThiDauDto.setMaMuaGiai(modelMapper.map(lichThiDau.getMaMuaGiai(), MuaGiaiSimpleDto.class));
        return lichThiDauDto;
    }

    @Override
    public List<LichThiDauDto> getAllLichThiDau() {
        return lichThiDauRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LichThiDauDto getLichThiDauById(Long id) {
        return toDto(lichThiDauRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Lich thi dau not found with id: " + id)
        ));
    }

    public List<LichThiDauDto> getLichThiDauByMuaGiai(String nam, String vongThiDau, Long maDoiBong) {
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
                        doiBong, doiBong, vongThiDau, muaGiai).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<LichThiDauDto> getLichThiDauByNgayThiDau(LocalDate ngayThiDauStart, LocalDate ngayThiDauEnd) {
        return lichThiDauRepository.findByNgayThiDauBetween(ngayThiDauStart, ngayThiDauEnd).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public LichThiDauDto createLichThiDau(CreateLichThiDauDto lichThiDauDto) {
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

        return toDto(lichThiDauRepository.save(lichThiDau));
    }

    @Override
    public LichThiDauDto updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto) {
        return toDto(lichThiDauRepository.findById(id)
                .map(existingLichThiDau -> updateExistingLichThiDau(existingLichThiDau, updateLichThiDauDto))
                .map(lichThiDauRepository::save)
                .orElseThrow(() -> new RuntimeException("Lich thi dau not found with id: " + id)
                ));
    }

    private LichThiDau updateExistingLichThiDau(LichThiDau existingLichThiDau, UpdateLichThiDauDto updateLichThiDauDto) {
        Utils.copyNonNullProperties(updateLichThiDauDto, existingLichThiDau, "id", "maDoiNha", "maDoiKhach", "maMuaGiai");

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
        lichThiDauRepository.findById(id).ifPresentOrElse(
                lichThiDauRepository::delete,
                () -> {
                    throw new RuntimeException("Lich thi dau not found with id: " + id);
                }
        );
    }
}
