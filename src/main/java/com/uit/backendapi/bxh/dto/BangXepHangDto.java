package com.uit.backendapi.bxh.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.Data;

@Data
public class BangXepHangDto {
    private Integer id;
    private DoiBongSimpleDto maDoi;
    private Integer soTranThang;
    private Integer soTranHoa;
    private Integer soTranThua;
    private Integer hieuSo;
    private MuaGiaiSimpleDto maMuaGiai;
    private Integer diem;
    private Integer soBanThang;
    private Integer soBanThua;
}
