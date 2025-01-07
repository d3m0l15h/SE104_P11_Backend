package com.uit.backendapi.cau_thu;

import com.uit.backendapi.cau_thu.dto.CauThuDto;
import com.uit.backendapi.cau_thu.dto.CreateCauThuDto;
import com.uit.backendapi.cau_thu.dto.FilterCauThuDto;
import com.uit.backendapi.cau_thu.dto.UpdateCauThuDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface ICauThuService {
    Page<CauThu> getAllCauThu(Pageable pageable);
    Page<CauThu> filter(FilterCauThuDto filterCauThuDto, Pageable pageable);
    CauThu getCauThuById(Long id);
    CauThu createCauThu(CreateCauThuDto createCauThuDto) throws IOException;
    CauThu updateCauThu(Long id, UpdateCauThuDto updateCauThuDto);
    void deleteCauThu(Long id);
}
