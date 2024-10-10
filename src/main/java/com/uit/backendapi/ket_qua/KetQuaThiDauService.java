package com.uit.backendapi.ket_qua;

import com.uit.backendapi.Utils;
import com.uit.backendapi.cau_thu.CauThu;
import com.uit.backendapi.cau_thu.CauThuRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;
import com.uit.backendapi.lich.LichThiDau;
import com.uit.backendapi.lich.LichThiDauRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KetQuaThiDauService implements IKetQuaThiDauService {
    private final KetQuaThiDauRepository ketQuaThiDauRepository;
    private final LichThiDauRepository lichThiDauRepository;
    private final CauThuRepository cauThuRepository;

    @Override
    public List<KetQuaThiDau> getAllKetQuaThiDau() {
        return ketQuaThiDauRepository.findAll();
    }

    @Override
    public KetQuaThiDau getKetQuaThiDauById(Long id) {
        return ketQuaThiDauRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Ket qua thi dau not found")
        );
    }

    @Override
    public KetQuaThiDau createKetQuaThiDau(CreateKetQuaThiDauDto createKetQuaThiDauDto) {
        LichThiDau lichThiDau = lichThiDauRepository.findById(createKetQuaThiDauDto.getMaLichThiDau()).orElseThrow(
                () -> new ResourceNotFoundException("Lich thi dau not found")
        );

        CauThu cauThuXuatSac = cauThuRepository.findById(createKetQuaThiDauDto.getCauThuXuatSac()).orElseThrow(
                () -> new ResourceNotFoundException("Cau thu not found")
        );

        KetQuaThiDau ketQuaThiDau = new KetQuaThiDau(
                lichThiDau,
                createKetQuaThiDauDto.getSoBanDoiNha(),
                cauThuXuatSac,
                createKetQuaThiDauDto.getGhiChu(),
                createKetQuaThiDauDto.getSoBanDoiKhach()
        );

        return ketQuaThiDauRepository.save(ketQuaThiDau);
    }

    @Override
    public KetQuaThiDau updateKetQuaThiDau(Long id, UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        return ketQuaThiDauRepository.findById(id)
                .map(existingKetQuaThiDau -> updateExistingKetQuaThiDau(existingKetQuaThiDau, updateKetQuaThiDauDto))
                .map(ketQuaThiDauRepository::save)
                .orElseThrow(() -> new ResourceNotFoundException("Ket qua thi dau not found")
        );
    }

    private KetQuaThiDau updateExistingKetQuaThiDau(KetQuaThiDau existingKetQuaThiDau, UpdateKetQuaThiDauDto updateKetQuaThiDauDto) {
        Utils.copyNonNullProperties(updateKetQuaThiDauDto, existingKetQuaThiDau, "id", "maLichThiDau", "cauThuXuatSac");

        if (updateKetQuaThiDauDto.getMaLichThiDau() != null) {
            LichThiDau lichThiDau = lichThiDauRepository.findById(updateKetQuaThiDauDto.getMaLichThiDau()).orElseThrow(
                    () -> new ResourceNotFoundException("Lich thi dau not found")
            );
            existingKetQuaThiDau.setMaLichThiDau(lichThiDau);
        }

        if (updateKetQuaThiDauDto.getCauThuXuatSac() != null) {
            CauThu cauThuXuatSac = cauThuRepository.findById(updateKetQuaThiDauDto.getCauThuXuatSac()).orElseThrow(
                    () -> new ResourceNotFoundException("Cau thu not found")
            );
            existingKetQuaThiDau.setCauThuXuatSac(cauThuXuatSac);
        }

        return existingKetQuaThiDau;
    }

    @Override
    public void deleteKetQuaThiDau(Long id) {
        ketQuaThiDauRepository.findById(id).ifPresentOrElse(
                ketQuaThiDauRepository::delete,
                () -> {
                    throw new ResourceNotFoundException("Ket qua thi dau not found");
                }
        );
    }
}
