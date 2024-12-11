package com.uit.backendapi.cau_thu;

import com.uit.backendapi.Utils;
import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.FilterCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CauThuService implements ICauThuService {
    private final CauThuRepository cauThuRepository;
    private final DoiBongRepository doiBongRepository;

    @Override
    public Page<CauThu> getAllCauThu(Pageable pageable) {
        List<CauThu> cauThus = cauThuRepository.findAll();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cauThus.size());
        return new PageImpl<>(cauThus.subList(start, end), pageable, cauThus.size());
    }

    @Override
    public Page<CauThu> filter(FilterCauThuDto filterCauThuDto, Pageable pageable) {
        List<CauThu> cauThus = cauThuRepository.findAll().stream()
                .filter(cauThu -> filterCauThuDto.getTenCauThu() == null
                        || cauThu.getTenCauThu().equals(filterCauThuDto.getTenCauThu()))
                .filter(cauThu -> filterCauThuDto.getNgaySinh() == null
                        || cauThu.getNgaySinh().equals(filterCauThuDto.getNgaySinh()))
                .filter(cauThu -> filterCauThuDto.getLoaiCauThu() == null
                        || cauThu.getLoaiCauThu().equals(filterCauThuDto.getLoaiCauThu()))
                .filter(cauThu -> filterCauThuDto.getMaDoi() == null
                        || cauThu.getMaDoi().getId().equals(filterCauThuDto.getMaDoi()))
                .filter(cauThu -> filterCauThuDto.getSoAo() == null
                        || cauThu.getSoAo().equals(filterCauThuDto.getSoAo()))
                .filter(cauThu -> filterCauThuDto.getViTri() == null
                        || cauThu.getViTri().equals(filterCauThuDto.getViTri()))
                .filter(cauThu -> filterCauThuDto.getNoiSinh() == null
                        || cauThu.getNoiSinh().equals(filterCauThuDto.getNoiSinh()))
                .filter(cauThu -> filterCauThuDto.getQuocTich() == null
                        || cauThu.getQuocTich().equals(filterCauThuDto.getQuocTich()))
                .filter(cauThu -> filterCauThuDto.getCanNang() == null
                        || cauThu.getCanNang().equals(filterCauThuDto.getCanNang()))
                .filter(cauThu -> filterCauThuDto.getChieuCao() == null
                        || cauThu.getChieuCao().equals(filterCauThuDto.getChieuCao()))
                .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), cauThus.size());
        return new PageImpl<>(cauThus.subList(start, end), pageable, cauThus.size());
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
        Utils.copyNonNullProperties(updateCauThuDto, existingCauThu, "id", "maDoi");

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
