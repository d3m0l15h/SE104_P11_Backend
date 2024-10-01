package com.uit.backendapi.mua_giai.dto;

import lombok.Data;

import java.util.Optional;

@Data
public class CreateMuaGiaiDto {
    private String nam;
    private Optional<Long> doiVoDich = Optional.empty();
}
