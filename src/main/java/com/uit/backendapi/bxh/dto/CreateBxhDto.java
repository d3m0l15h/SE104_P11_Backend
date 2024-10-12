package com.uit.backendapi.bxh.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateBxhDto {
    private Long maDoi;
    private Integer soTranThang;
    private Integer soTranHoa;
    private Integer soTranThua;
    private Integer hieuSo;
    private Long maMuaGiai;
    private Integer diem;
    private Integer soBanThang;
    private Integer soBanThua;
}
