package com.uit.backendapi.thay_nguoi;

import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.thay_nguoi.dto.CreateThayNguoiDto;
import com.uit.backendapi.thay_nguoi.dto.UpdateThayNguoiDto;

import java.util.List;

public interface IThayNguoiService {
    List<ThayNguoi> getThayNguoiByKetQua(KetQuaThiDau maKetQua);

    ThayNguoi createThayNguoiByKetQua(KetQuaThiDau maKetQua, CreateThayNguoiDto createThayNguoiDto);

    ThayNguoi updateThayNguoiByKetQuaAndId(KetQuaThiDau maKetQua, Long id, UpdateThayNguoiDto updateThayNguoiDto);

    void deleteThayNguoiByKetQuaAndId(KetQuaThiDau maKetQua, Long id);
}
