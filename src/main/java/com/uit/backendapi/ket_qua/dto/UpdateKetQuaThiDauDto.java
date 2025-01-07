package com.uit.backendapi.ket_qua.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateKetQuaThiDauDto {
    private Long cauThuXuatSac;
    private String ghiChu;
}
