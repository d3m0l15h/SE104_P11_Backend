package com.uit.backendapi.lich.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongLichThiDauDto;
import com.uit.backendapi.ket_qua.KetQuaThiDau;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauDto;
import com.uit.backendapi.ket_qua.dto.KetQuaThiDauLichDto;
import com.uit.backendapi.models.DoiHinhRaSan;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
public class LichThiDauDto {
    private Integer id;
    private String vongThiDau;
    private LocalDate ngayThiDau;
    private LocalTime gioThiDau;
    private String sanThiDau;
    private DoiBongLichThiDauDto maDoiNha;
    private DoiBongLichThiDauDto maDoiKhach;
//    private Set<DoiHinhRaSan> doiHinhRaSans;
    private KetQuaThiDauLichDto ketQuaThiDau;
    private MuaGiaiSimpleDto maMuaGiai;
}
