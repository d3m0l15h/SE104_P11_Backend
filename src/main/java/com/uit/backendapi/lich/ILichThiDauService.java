package com.uit.backendapi.lich;

import com.uit.backendapi.lich.dto.CreateLichThiDauDto;
import com.uit.backendapi.lich.dto.UpdateLichThiDauDto;

import java.util.List;

public interface ILichThiDauService {
    LichThiDau createLichThiDau(CreateLichThiDauDto createLichThiDauDto);
    LichThiDau updateLichThiDau(Long id, UpdateLichThiDauDto updateLichThiDauDto);
    LichThiDau getLichThiDauById(Long id);
    List<LichThiDau> getAllLichThiDau();
    void deleteLichThiDau(Long id);
}
