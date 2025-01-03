package com.uit.backendapi.bxh.dto;

import lombok.Data;

@Data
public class FilterBangXepHangDto {
    private Integer maMuaGiai;
    private Integer maDoi;
    private Integer soTranThang;
    private Integer soTranHoa;
    private Integer soTranThua;
    private Integer soBanThang;
    private Integer soBanThua;
    private Integer hieuSo;
    private Integer diem;
}
