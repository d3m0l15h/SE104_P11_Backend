package com.uit.backendapi.ban_thang.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateBanThangDto extends CreateBanThangDto{
    private Long id;
}
