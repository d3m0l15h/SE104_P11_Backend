package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.LichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;

import java.util.List;

public interface ILichThiDauService {
    LichThiDauDto createLichThiDau(CreateLichThiDauDto createLichThiDauDto);
    LichThiDauDto updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto);
    LichThiDauDto getLichThiDauById(Long id);
    List<LichThiDauDto> getAllLichThiDau();
    void deleteLichThiDau(Long id);
}
