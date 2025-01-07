package com.uit.backendapi.doi_bong.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateDoiBongDto extends CreateDoiBongDto {
    @Override
    public String toString() {
        return "UpdateDoiBongDto{" +
                "tenDoi='" + getTenDoi() + '\'' +
                ", tenSanNha='" + getTenSanNha() + '\'' +
                ", diaChiSanNha='" + getDiaChiSanNha() + '\'' +
                ", dienThoai='" + getDienThoai() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", toChucQuanLy='" + getToChucQuanLy() + '\'' +
                ", thanhPhoTrucThuoc='" + getThanhPhoTrucThuoc() + '\'' +
                ", logo=" + getLogo() +
                ", aoChinhThuc=" + getAoChinhThuc() +
                ", aoDuBi=" + getAoDuBi() +
                '}';
    }
}
