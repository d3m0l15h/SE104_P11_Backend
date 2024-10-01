package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;

import java.io.IOException;
import java.util.List;

public interface IDoiBongService {
    List<DoiBong> getAllDoiBong();

    DoiBong getDoiBongById(Long id);

    DoiBong createDoiBong(CreateDoiBongDto createDoiBongDto) throws IOException;

    DoiBong updateDoiBong(Long id, UpdateDoiBongDto updateDoiBongDto) throws IOException;

    void deleteDoiBong(Long id);
}
