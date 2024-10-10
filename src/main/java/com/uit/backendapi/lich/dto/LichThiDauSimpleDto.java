package com.uit.backendapi.lich.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import lombok.Data;

@Data
public class LichThiDauSimpleDto {
    private Integer id;
    private String ngayThiDau;
    private String gioThiDau;
    private DoiBongSimpleDto maDoiNha;
    private DoiBongSimpleDto maDoiKhach;
}
