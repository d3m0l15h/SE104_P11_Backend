package com.uit.backendapi.ban_thang.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauSimpleDto;
import lombok.Data;

@Data
public class BanThangKetQuaDto {
    private Long id;
    private CauThuSimpleDto maCauThu;
    private String loaiBanThang;
    private Integer thoiDiem;
}
