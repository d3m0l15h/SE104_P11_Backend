package com.uit.backendapi.lich.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
public class LichThiDauDto {
    private Integer id;
    private String vongThiDau;
    private LocalDate ngayThiDau;
    private LocalTime gioThiDau;
    private String sanThiDau;
    private DoiBongDto maDoiNha;
    private DoiBongDto maDoiKhach;
    private MuaGiaiDto maMuaGiai;
}
