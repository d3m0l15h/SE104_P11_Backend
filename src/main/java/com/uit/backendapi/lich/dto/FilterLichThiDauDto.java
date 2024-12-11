package com.uit.backendapi.lich.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FilterLichThiDauDto {
    private String vongThiDau;
    private LocalDate ngayThiDau;
    private LocalTime gioThiDau;
    private String sanThiDau;
    private Integer maDoiNha;
    private Integer maDoiKhach;
    private Integer maMuaGiai;

}
