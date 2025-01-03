package com.uit.backendapi.thong_ke.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.Data;

@Data
public class VuaPhaLuoiDto {
    MuaGiaiSimpleDto muaGiai;
    CauThuSimpleDto cauThu;
    DoiBongSimpleDto doiBong;
    Integer soBanThang;

    public VuaPhaLuoiDto(MuaGiaiSimpleDto muaGiai, CauThuSimpleDto cauThu, int soBanThang, DoiBongSimpleDto doiBong) {
        this.muaGiai = muaGiai;
        this.cauThu = cauThu;
        this.soBanThang = soBanThang;
        this.doiBong = doiBong;
    }
}
