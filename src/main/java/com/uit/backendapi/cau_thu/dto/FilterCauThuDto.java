package com.uit.backendapi.cau_thu.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterCauThuDto {
    private String tenCauThu;
    private LocalDate ngaySinh;
    private String loaiCauThu;
    private Integer maDoi;
    private Integer soAo;
    private String viTri;
    private String noiSinh;
    private String quocTich;
    private Double chieuCao;
    private Double canNang;
}
