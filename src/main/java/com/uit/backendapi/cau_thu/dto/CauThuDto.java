package com.uit.backendapi.cau_thu.dto;

import com.uit.backendapi.ban_thang.BanThang;
import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import com.uit.backendapi.the_phat.ThePhat;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class CauThuDto {
    private Long id;
    private String tenCauThu;
    private LocalDate ngaySinh;
    private String loaiCauThu;
    private DoiBongSimpleDto maDoi;
    private Integer soAo;
    private String viTri;
    private String noiSinh;
    private String quocTich;
    private String tieuSu;
    private Double chieuCao;
    private Double canNang;
}
