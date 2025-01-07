package com.uit.backendapi.bxh.dto;

import com.uit.backendapi.mua_giai.MuaGiai;
import lombok.Data;

@Data
public class BangXepHangSimpleDto {
    private Integer id;
    private MuaGiai maMuaGiai;
}
