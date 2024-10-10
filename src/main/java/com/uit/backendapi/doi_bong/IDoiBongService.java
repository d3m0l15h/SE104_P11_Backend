package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.DoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;

import java.io.IOException;
import java.util.List;

public interface IDoiBongService {
    List<DoiBongDto> getAllDoiBong();

    DoiBongDto getDoiBongById(Long id);

    DoiBongDto createDoiBong(CreateDoiBongDto createDoiBongDto) throws IOException;

    DoiBongDto updateDoiBong(Long id, UpdateDoiBongDto updateDoiBongDto) throws IOException;

    void deleteDoiBong(Long id);
}
