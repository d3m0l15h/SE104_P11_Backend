package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;

import java.util.List;

public interface IKetQuaThiDauService {
    List<KetQuaThiDau> getAllKetQuaThiDau();
    KetQuaThiDau getKetQuaThiDauById(Long id);
    KetQuaThiDau createKetQuaThiDau(CreateKetQuaThiDauDto createKetQuaThiDauDto);
    KetQuaThiDau updateKetQuaThiDau(Long id, UpdateKetQuaThiDauDto updateKetQuaThiDauDto);
    void deleteKetQuaThiDau(Long id);
}
