package com.uit.backendapi.ket_qua.dto;

import com.uit.backendapi.ban_thang.dto.CreateBanThangDto;
import com.uit.backendapi.thay_nguoi.dto.CreateThayNguoiDto;
import com.uit.backendapi.the_phat.dto.CreateThePhatDto;
import lombok.Data;

@Data
public class CreateKetQuaThiDauDto {
    private Long maLichThiDau;
    private Long cauThuXuatSac;
    private String ghiChu;
    private CreateThePhatDto[] thePhats;
    private CreateBanThangDto[] banThangs;
    private CreateThayNguoiDto[] thayNguois;
}
