package com.uit.backendapi.the_phat.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauSimpleDto;
import lombok.Data;

@Data
public class ThePhatDto {
    private Integer id;
    private KetQuaThiDauSimpleDto maKetQua;
    private CauThuSimpleDto maCauThu;
    private String loaiThe;
    private Integer thoiDiem;
}
