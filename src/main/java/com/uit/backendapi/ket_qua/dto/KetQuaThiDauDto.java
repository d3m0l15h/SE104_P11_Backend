package com.uit.backendapi.ket_qua.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.lich.dto.LichThiDauSimpleDto;
import lombok.Data;

@Data
public class KetQuaThiDauDto {
    private Integer id;
    private LichThiDauSimpleDto maLichThiDau;
    private Integer soBanDoiNha;
    private Integer soBanDoiKhach;
    private CauThuSimpleDto cauThuXuatSac;
    private String ghiChu;
}
