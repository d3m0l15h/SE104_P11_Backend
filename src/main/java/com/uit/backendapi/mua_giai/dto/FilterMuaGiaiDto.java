package com.uit.backendapi.mua_giai.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterMuaGiaiDto {
    private String nam;
    private Long doiVoDich;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
}