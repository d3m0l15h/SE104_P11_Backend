package com.uit.backendapi.thay_nguoi;

import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.thay_nguoi.dto.CreateThayNguoiDto;
import com.uit.backendapi.thay_nguoi.dto.UpdateThayNguoiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ThayNguoiService implements IThayNguoiService {
    private final ThayNguoiRepository thayNguoiRepository;
    private final CauThuRepository cauThuRepository;

    @Override
    public List<ThayNguoi> getThayNguoiByKetQua(KetQuaThiDau maKetQua) {
        return thayNguoiRepository.findByMaKetQua(maKetQua);
    }

    @Override
    public ThayNguoi createThayNguoiByKetQua(KetQuaThiDau maKetQua, CreateThayNguoiDto createThayNguoiDto) {
        CauThu maCauThuRa = cauThuRepository.findById(createThayNguoiDto.getMaCauThuRa())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cau thu ra not found with id: " + createThayNguoiDto.getMaCauThuRa())
                );
        CauThu maCauThuVao = cauThuRepository.findById(createThayNguoiDto.getMaCauThuVao())
                .orElseThrow(
                        () -> new ResourceNotFoundException("Cau thu vao not found with id: " + createThayNguoiDto.getMaCauThuVao())
                );


        ThayNguoi thayNguoi = new ThayNguoi(
                maKetQua,
                maCauThuVao,
                maCauThuRa,
                createThayNguoiDto.getThoiDiem()
        );

        return thayNguoiRepository.save(thayNguoi);
    }

    @Override
    public ThayNguoi updateThayNguoiByKetQuaAndId(KetQuaThiDau maKetQua, Long id, UpdateThayNguoiDto updateThayNguoiDto) {
        return thayNguoiRepository.findByMaKetQuaAndId(maKetQua, Math.toIntExact(id))
                .map(existingThayNguoi -> updateExistingThayNguoi(existingThayNguoi, updateThayNguoiDto))
                .map(thayNguoiRepository::save)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Thay nguoi not found with id: " + id)
                );
    }

    private ThayNguoi updateExistingThayNguoi(ThayNguoi existingThayNguoi, UpdateThayNguoiDto updateThayNguoiDto) {
        BeanUtils.copyProperties(updateThayNguoiDto, existingThayNguoi);

        if (updateThayNguoiDto.getMaCauThuRa() != null) {
            CauThu maCauThuRa = cauThuRepository.findById(updateThayNguoiDto.getMaCauThuRa())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Cau thu ra not found with id: " + updateThayNguoiDto.getMaCauThuRa())
                    );
            existingThayNguoi.setMaCauThuRa(maCauThuRa);
        }

        if (updateThayNguoiDto.getMaCauThuVao() != null) {
            CauThu maCauThuVao = cauThuRepository.findById(updateThayNguoiDto.getMaCauThuVao())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Cau thu vao not found with id: " + updateThayNguoiDto.getMaCauThuVao())
                    );
            existingThayNguoi.setMaCauThuVao(maCauThuVao);
        }

        return existingThayNguoi;
    }

    @Override
    public void deleteThayNguoiByKetQuaAndId(KetQuaThiDau maKetQua, Long id) {
        thayNguoiRepository.findByMaKetQuaAndId(maKetQua, Math.toIntExact(id))
                .ifPresentOrElse(
                        thayNguoiRepository::delete,
                        () -> {
                            throw new ResourceNotFoundException("Thay nguoi not found with id: " + id);
                        }
                );
    }
}
