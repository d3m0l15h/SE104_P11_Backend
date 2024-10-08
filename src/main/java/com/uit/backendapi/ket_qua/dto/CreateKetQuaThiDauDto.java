package com.uit.backendapi.ket_qua.dto;

import lombok.Data;

@Data
public class CreateKetQuaThiDauDto {
    private Long maLichThiDau;
    private Integer soBanDoiNha;
    private Long cauThuXuatSac;
    private String ghiChu;
    private Integer soBanDoiKhach;
}
