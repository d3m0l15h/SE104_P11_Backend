package com.uit.backendapi.cau_thu.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CauThuDto {
    private Long id;
    private String tenCauThu;
    private String ngaySinh;
    private String loaiCauThu;
    private DoiBongDto maDoi;
    private Integer soAo;
    private String viTri;
    private String noiSinh;
    private String quocTich;
    private String tieuSu;
    private Double chieuCao;
    private Double canNang;
}
