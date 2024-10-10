package com.uit.backendapi.doi_bong.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoiBongLichThiDauDto {
    private Long id;
    private String tenDoi;
    private String aoChinhThuc;
    private String aoDuBi;
}
