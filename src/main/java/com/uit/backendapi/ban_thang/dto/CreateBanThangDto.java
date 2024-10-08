package com.uit.backendapi.ban_thang.dto;

import lombok.Data;

@Data
public class CreateBanThangDto {
    private Long maCauThu;
    private String loaiBanThang;
    private Integer thoiDiem;
}
