package com.uit.backendapi.thay_nguoi.dto;

import lombok.Data;

@Data
public class CreateThayNguoiDto {
    private Long maCauThuVao;
    private Long maCauThuRa;
    private Integer thoiDiem;
}
