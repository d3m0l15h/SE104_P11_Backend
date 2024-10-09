package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;

import java.util.List;

public interface ICauThuService {
    List<CauThuDto> getAllCauThu();
    CauThuDto getCauThuById(Long id);
    CauThuDto createCauThu(CreateCauThuDto createCauThuDto);
    CauThuDto updateCauThu(Long id, UpdateCauThuDto updateCauThuDto);
    void deleteCauThu(Long id);
}
