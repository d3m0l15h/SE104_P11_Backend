package com.uit.backendapi.doi_bong.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class DoiBongDto {
    private Long id;
    private String tenDoi;
    private String tenSanNha;
    private String diaChiSanNha;
    private String dienThoai;
    private String email;
    private String toChucQuanLy;
    private String thanhPhoTrucThuoc;
    private String logo;
    private String aoChinhThuc;
    private String aoDuBi;
    private Set<CauThuSimpleDto> cauThus;
    private Set<MuaGiaiSimpleDto> muaGiais;
}
