package com.uit.backendapi.cau_thu.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CauThuSimpleDto {
    private Long id;
    private String tenCauThu;
    private String loaiCauThu;
    private Integer soAo;
    private String viTri;
    private String quocTich;
    private String avatar;
}
