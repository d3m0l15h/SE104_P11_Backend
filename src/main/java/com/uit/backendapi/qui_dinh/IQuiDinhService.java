package com.uit.backendapi.qui_dinh;

import com.uit.backendapi.qui_dinh.dto.UpsertQuiDinhDto;

import java.util.List;

public interface IQuiDinhService {
    List<QuiDinh> getQuiDinh();
    List<QuiDinh> upsertQuiDinh(UpsertQuiDinhDto upsertQuiDinhDto);
    void deleteQuiDinh(Long id);
}
