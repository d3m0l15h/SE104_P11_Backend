package com.uit.backendapi.ket_qua.dto;

import com.uit.backendapi.ban_thang.dto.BanThangDto;
import com.uit.backendapi.ban_thang.dto.BanThangKetQuaDto;
import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.lich.dto.LichThiDauSimpleDto;
import lombok.Data;

import java.util.Set;

@Data
public class KetQuaThiDauDto {
    private Integer id;
    private LichThiDauSimpleDto maLichThiDau;
    private Integer soBanDoiNha;
    private Integer soBanDoiKhach;
    private CauThuSimpleDto cauThuXuatSac;
    private String ghiChu;
    private Set<BanThangKetQuaDto> banThangs;
}
