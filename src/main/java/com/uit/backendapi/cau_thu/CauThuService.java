package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CauThuService implements ICauThuService {
    private final CauThuRepository cauThuRepository;
    private final DoiBongRepository doiBongRepository;

    @Override
    public List<CauThu> getAllCauThu() {
        return cauThuRepository.findAll();
    }

    @Override
    public CauThu getCauThuById(Long id) {
        return cauThuRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cau thu not found")
        );
    }

    @Override
    public CauThu createCauThu(CreateCauThuDto createCauThuDto) {
        DoiBong doiBong = doiBongRepository.findById(createCauThuDto.getMaDoi()).orElseThrow(
                () -> new ResourceNotFoundException("Doi bong not found")
        );

        CauThu cauThu = new CauThu(
                createCauThuDto.getTenCauThu(),
                createCauThuDto.getNgaySinh(),
                createCauThuDto.getLoaiCauThu(),
                doiBong,
                createCauThuDto.getSoAo(),
                createCauThuDto.getViTri(),
                createCauThuDto.getNoiSinh(),
                createCauThuDto.getQuocTich(),
                createCauThuDto.getTieuSu().orElse(null),
                createCauThuDto.getChieuCao(),
                createCauThuDto.getCanNang()
        );

        return cauThuRepository.save(cauThu);
    }

    @Override
    public CauThu updateCauThu(Long id, UpdateCauThuDto updateCauThuDto) {
        return cauThuRepository.findById(id)
                .map(existingCauThu -> updateExistingCauThu(existingCauThu, updateCauThuDto))
                .map(cauThuRepository::save
                ).orElseThrow(() -> new ResourceNotFoundException("Doi bong not found"));
    }

    private CauThu updateExistingCauThu(CauThu existingCauThu, UpdateCauThuDto updateCauThuDto) {
        BeanUtils.copyProperties(updateCauThuDto, existingCauThu, "id", "maDoi");

        if (updateCauThuDto.getMaDoi() != null) {
            DoiBong doiBong = doiBongRepository.findById(updateCauThuDto.getMaDoi()).orElseThrow(
                    () -> new ResourceNotFoundException("Doi bong not found")
            );
            existingCauThu.setMaDoi(doiBong);
        }

        return existingCauThu;
    }

    @Override
    public void deleteCauThu(Long id) {
        cauThuRepository.deleteById(id);
    }
}
