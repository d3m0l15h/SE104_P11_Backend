package com.uit.backendapi.mua_giai;

import com.uit.backendapi.mua_giai.dto.CreateMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.FilterMuaGiaiDto;
import com.uit.backendapi.mua_giai.dto.UpdateMuaGiaiDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface IMuaGiaiService {

    Page<MuaGiai> getAllMuaGiai(Pageable pageable); // Add this method

    Page<MuaGiai> filter(FilterMuaGiaiDto filterMuaGiaiDto, Pageable pageable);

    MuaGiai createMuaGiai(CreateMuaGiaiDto createMuaGiaiDto);

    MuaGiai updateMuaGiai(Long id, UpdateMuaGiaiDto updateMuaGiaiDto);

    MuaGiai getMuaGiaiById(Long id);

    void deleteMuaGiai(Long id);
}
