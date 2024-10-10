package com.uit.backendapi.thay_nguoi.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauSimpleDto;
import lombok.Data;

@Data
public class ThayNguoiKetQuaDto {
    private Long id;
    private CauThuSimpleDto maCauThuVao;
    private CauThuSimpleDto maCauThuRa;
    private Integer thoiDiem;
}
