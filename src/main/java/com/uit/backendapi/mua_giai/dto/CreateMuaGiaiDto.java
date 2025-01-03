package com.uit.backendapi.mua_giai.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Optional;

@Data
public class CreateMuaGiaiDto {
    private String nam;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
}
