package com.uit.backendapi.thay_nguoi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class UpdateThayNguoiDto extends CreateThayNguoiDto{
    private Long id;
}
