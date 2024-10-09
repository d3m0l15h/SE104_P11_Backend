package com.uit.backendapi.lich.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateLichThiDauDto {
    private String vongThiDau;
    private LocalDate ngayThiDau;
    private LocalTime gioThiDau;
    private String sanThiDau;
    private Long maDoiNha;
    private Long maDoiKhach;
    private Long maMuaGiai;
}
