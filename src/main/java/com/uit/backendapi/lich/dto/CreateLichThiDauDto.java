package com.uit.backendapi.lich.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateLichThiDauDto {
    String vongThiDau;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate ngayThiDau;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    LocalTime gioThiDau;
    String sanThiDau;
    Long maDoiNha;
    Long maDoiKhach;
    Long maMuaGiai;
}
