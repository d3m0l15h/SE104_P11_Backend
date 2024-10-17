package com.uit.backendapi.doi_bong.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateDoiBongDto {
    private String tenDoi;
    private String tenSanNha;
    private String diaChiSanNha;
    private String dienThoai;
    private String email;
    private String toChucQuanLy;
    private String thanhPhoTrucThuoc;
    private MultipartFile logo;
    private MultipartFile aoChinhThuc;
    private MultipartFile aoDuBi;
}
