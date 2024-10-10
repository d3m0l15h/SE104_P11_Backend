package com.uit.backendapi.cau_thu;

import com.uit.backendapi.Utils;
import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.doi_bong.DoiBongRepository;
import com.uit.backendapi.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CauThuService implements ICauThuService {
    private final CauThuRepository cauThuRepository;
    private final DoiBongRepository doiBongRepository;
    private final ModelMapper modelMapper;

    private CauThuDto toDto(CauThu cauThu) {
        return modelMapper.map(cauThu, CauThuDto.class);
    }

    @Override
    public List<CauThuDto> getAllCauThu() {
        return cauThuRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public CauThuDto getCauThuById(Long id) {
        return toDto(cauThuRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Cau thu not found")
        ));
    }

    @Override
    public CauThuDto createCauThu(CreateCauThuDto createCauThuDto) {
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

        return toDto(cauThuRepository.save(cauThu));
    }

    @Override
    public CauThuDto updateCauThu(Long id, UpdateCauThuDto updateCauThuDto) {
        return toDto(
                cauThuRepository.findById(id)
                .map(existingCauThu -> updateExistingCauThu(existingCauThu, updateCauThuDto))
                .map(cauThuRepository::save
                ).orElseThrow(() -> new ResourceNotFoundException("Doi bong not found"))
        );
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
