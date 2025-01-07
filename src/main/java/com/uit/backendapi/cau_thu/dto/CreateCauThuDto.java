package com.uit.backendapi.cau_thu.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class CreateCauThuDto {
    private String tenCauThu;
    private LocalDate ngaySinh;
    private String loaiCauThu;
    private Long maDoi;
    private Integer soAo;
    private String viTri;
    private String noiSinh;
    private String quocTich;
    private String tieuSu;
    private Double chieuCao;
    private Double canNang;
    private MultipartFile avatar;
}

