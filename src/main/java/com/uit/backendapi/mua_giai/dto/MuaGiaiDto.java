package com.uit.backendapi.mua_giai.dto;

import com.uit.backendapi.bxh.dto.BangXepHangMuaGiaiDto;
import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Data
public class MuaGiaiDto {
    private Integer id;
    private String nam;
    private LocalDate ngayBatDau;
    private LocalDate ngayKetThuc;
    private DoiBongSimpleDto doiVoDich;
    private Set<BangXepHangMuaGiaiDto> bangXepHangs;
}
