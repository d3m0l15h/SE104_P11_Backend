package com.uit.backendapi.doi_bong.dto;

import lombok.Data;

@Data
public class FilterDoiBongDto {
    private String tenDoi;
    private String tenSanNha;
    private String diaChiSanNha;
    private String dienThoai;
    private String email;
    private String toChucQuanLy;
    private String thanhPhoTrucThuoc;
}
