package com.uit.backendapi.mua_giai;

import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;

import java.util.List;

public interface IMuaGiaiService {
    List<MuaGiai> getAllMuaGiai();

    MuaGiai getMuaGiaiById(Long id);

    MuaGiai createMuaGiai(CreateMuaGiaiDto createMuaGiaiDto);

    MuaGiai updateMuaGiai(Long id, UpdateMuaGiaiDto updateMuaGiaiDto);

    void deleteMuaGiai(Long id);
}
