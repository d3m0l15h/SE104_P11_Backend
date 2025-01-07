package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.FilterLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ILichThiDauService {
    LichThiDau createLichThiDau(CreateLichThiDauDto createLichThiDauDto);
    LichThiDau updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto);
    Page<LichThiDau> filter(FilterLichThiDauDto filterLichThiDauDto, Pageable pageable);
    LichThiDau getLichThiDauById(Long id);
    Page<LichThiDau> getAllLichThiDau(Pageable pageable);
    ResponseEntity<Void> deleteLichThiDau(Long id);
}
