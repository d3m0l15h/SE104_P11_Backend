package com.uit.backendapi.bxh.dto;

import com.uit.backendapi.doi_bong.dto.DoiBongSimpleDto;
import lombok.Data;

@Data
public class BangXepHangMuaGiaiDto {
    private Integer id;
    private DoiBongSimpleDto maDoi;
    private Integer soTranThang;
    private Integer soTranHoa;
    private Integer soTranThua;
    private Integer soBanThang;
    private Integer soBanThua;
    private Integer hieuSo;
    private Integer diem;
}
