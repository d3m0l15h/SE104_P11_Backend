package com.uit.backendapi.bxh.dto;

import com.uit.backendapi.doi_bong.DoiBong;
import com.uit.backendapi.mua_giai.MuaGiai;
import lombok.Data;

@Data
public class FilterBangXepHangDto {
    private Integer maMuaGiai;
    private Integer maDoi;
    private Integer soTranThang;
    private Integer soTranHoa;
    private Integer soTranThua;
    private Integer soBanThang;
    private Integer soBanThua;
    private Integer hieuSo;
    private Integer diem;

    public FilterBangXepHangDto(
            Integer maMuaGiai,
            Integer maDoi
    ) {
        this.maMuaGiai = maMuaGiai;
        this.maDoi = maDoi;
    }
}
