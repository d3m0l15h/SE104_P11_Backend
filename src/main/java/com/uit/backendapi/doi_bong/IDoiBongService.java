package com.uit.backendapi.doi_bong;

import com.uit.backendapi.doi_bong.dto.CreateDoiBongDto;
import com.uit.backendapi.doi_bong.dto.FilterDoiBongDto;
import com.uit.backendapi.doi_bong.dto.UpdateDoiBongDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface IDoiBongService {
    Page<DoiBong> getAllDoiBong(Pageable pageable);

    Page<DoiBong> filter(FilterDoiBongDto filterDoiBongDto, Pageable pageable);

    DoiBong getDoiBongById(Long id);

    DoiBong createDoiBong(CreateDoiBongDto createDoiBongDto) throws IOException;

    DoiBong updateDoiBong(Long id, UpdateDoiBongDto updateDoiBongDto) throws IOException;

    void deleteDoiBong(Long id);
}
