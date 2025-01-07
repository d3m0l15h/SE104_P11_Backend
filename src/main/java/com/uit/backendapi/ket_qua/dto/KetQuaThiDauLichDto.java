package com.uit.backendapi.ket_qua.dto;

import com.uit.backendapi.ban_thang.dto.BanThangKetQuaDto;
import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.lich.dto.LichThiDauSimpleDto;
import com.uit.backendapi.thay_nguoi.dto.ThayNguoiKetQuaDto;
import com.uit.backendapi.the_phat.dto.ThePhatKetQuaDto;
import lombok.Data;

import java.util.Set;

@Data
public class KetQuaThiDauLichDto {
    private Integer id;
    private Integer soBanDoiNha;
    private Integer soBanDoiKhach;
    private CauThuSimpleDto cauThuXuatSac;
    private String ghiChu;
    private Set<BanThangKetQuaDto> banThangs;
    private Set<ThePhatKetQuaDto> thePhats;
    private Set<ThayNguoiKetQuaDto> thayNguois;
}
