package com.uit.backendapi.lich.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CreateLichThiDauDto {
    private String vongThiDau;
    @Schema(example = "yyyy-MM-dd")
    private LocalDate ngayThiDau;
    @Schema(example = "HH:mm")
    private LocalTime gioThiDau;
    private String sanThiDau;
    private Long maDoiNha;
    private Long maDoiKhach;
    private Long maMuaGiai;
}
