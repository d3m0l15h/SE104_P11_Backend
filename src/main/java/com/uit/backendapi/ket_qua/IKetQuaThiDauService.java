package com.uit.backendapi.ket_qua;

import com.uit.backendapi.ket_qua.dto.CreateKetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.UpdateKetQuaThiDauDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IKetQuaThiDauService {
    Page<KetQuaThiDau> getAllKetQuaThiDau(Pageable pageable);
    KetQuaThiDau getKetQuaThiDauById(Long id);
    KetQuaThiDau createKetQuaThiDau(CreateKetQuaThiDauDto createKetQuaThiDauDto);
    KetQuaThiDau updateKetQuaThiDau(Long id, UpdateKetQuaThiDauDto updateKetQuaThiDauDto);
    void deleteKetQuaThiDau(Long id);
}
