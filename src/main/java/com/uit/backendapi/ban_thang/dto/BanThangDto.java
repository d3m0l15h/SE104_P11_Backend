package com.uit.backendapi.ban_thang.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauSimpleDto;
import lombok.Data;

@Data
public class BanThangDto {
    private Long id;
    private KetQuaThiDauSimpleDto maKetQua;
    private CauThuSimpleDto maCauThu;
    private String loaiBanThang;
    private Integer thoiDiem;
}
