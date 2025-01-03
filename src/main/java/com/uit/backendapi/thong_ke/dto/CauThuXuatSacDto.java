package com.uit.backendapi.thong_ke.dto;

import com.uit.backendapi.cau_thu.dto.CauThuSimpleDto;
import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import com.uit.backendapi.mua_giai.dto.MuaGiaiSimpleDto;
import lombok.Data;

@Data
public class CauThuXuatSacDto {
    CauThuSimpleDto cauThu;
    DoiBongSimpleDto doiBong;
    MuaGiaiSimpleDto muaGiai;
    int soLanBinhChon;

    public CauThuXuatSacDto(CauThuSimpleDto cauThu, DoiBongSimpleDto doiBong, MuaGiaiSimpleDto muaGiai, int soLanBinhChon) {
        this.cauThu = cauThu;
        this.doiBong = doiBong;
        this.muaGiai = muaGiai;
        this.soLanBinhChon = soLanBinhChon;
    }
}
